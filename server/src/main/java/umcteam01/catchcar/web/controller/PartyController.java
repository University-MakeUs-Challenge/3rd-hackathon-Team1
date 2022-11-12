package umcteam01.catchcar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponse;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.*;
import umcteam01.catchcar.service.PartyProvider;
import umcteam01.catchcar.service.PartyService;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static umcteam01.catchcar.config.BaseResponseStatus.REQUEST_ERROR;
import umcteam01.catchcar.domain.PartyJoinReq;
import umcteam01.catchcar.domain.PartyJoinRes;
import umcteam01.catchcar.service.PartyService;

import java.util.ArrayList;
import java.util.List;

import static umcteam01.catchcar.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/party")
public class PartyController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PartyProvider partyProvider;
    private final PartyService partyService;

    /**
     * 파티 생성
     * @param partyCreateReq
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PartyCreateResDto> createParty(@RequestBody PartyCreateReqDto partyCreateReq) throws BaseException {
        if (partyProvider.checkPartyLeader(partyCreateReq.getLeader()) != 0) {
            System.out.println("party leader check = " + partyProvider.checkPartyLeader(partyCreateReq.getLeader()));

            return new BaseResponse<>(POST_PARTY_EXISTS_LEADER);   // 유저가 이미 하나의 활성상태인 파티그룹을 생성한 경우에서의 예외처리
        }

        if (partyCreateReq.getDestination() == null) {
            return new BaseResponse<>(POST_PARTY_EMPTY_VALUE);
        }

        try {
            PartyCreateResDto partyCreateRes = partyService.createParty(partyCreateReq);

            System.out.println("PartyController: createPary 실행");
            return new BaseResponse<>(partyCreateRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // TODO 파티 만료 시 status -> INACTIVE (partyService.updatePartyStatus)
    // TODO 파티 상태 변경 active -> partyService.updatePartyActive


    @PatchMapping("/status")
    public BaseResponse<List<PartyCancelRespDto>> partyCancel(@RequestBody PartyCancelReqDto partyCancelReqDto) {
        System.out.println(partyCancelReqDto);
        try {
            partyService.modifyParticipateActive(partyCancelReqDto);

        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
        // party id 값이 없을때
        if (partyCancelReqDto.getPartyId() == null) {
            return new BaseResponse<>(BaseResponseStatus.PATCH_PARTY_EMPTY_PARTY_ID);
        }
        // user id 값이 없을때
        if (partyCancelReqDto.getUserId() == null) {
            return new BaseResponse<>(BaseResponseStatus.PATCH_PARTY_EMPTY_USER_ID);
        }
        try {
            List<PartyCancelRespDto> partyCancelRespDtos = partyProvider.getParticipations(partyCancelReqDto);
            return new BaseResponse<>(partyCancelRespDtos);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }
    
     /**
     * 그룹 참여 API
     * [PATCH] /party/catch
     */
    @ResponseBody
    @PatchMapping("/catch")
    public BaseResponse<List<PartyJoinRes>> participateParty(@RequestBody PartyJoinReq partyJoinReq){
        try {
            List<PartyJoinRes> partyJoinRes = partyService.participateParty(partyJoinReq);
            return new BaseResponse<>(partyJoinRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 그룹 전체 조회 API
     * [GET] /party
     */
    @GetMapping("")
    public BaseResponse<List<PartyReadResDto>> getPartyList() {
        try {
            List<PartyReadResDto> partyReadResDto = partyProvider.getPartyList();
            return new BaseResponse<>(partyReadResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 그룹 단일 조회 API
     * [GET] /party/:party_id
     */
    @GetMapping("/{party_id}")
    public BaseResponse<PartyReadResDto> getParty(@PathVariable("party_id") Long party_id) {
        try {
            PartyReadResDto partyReadResDto = partyProvider.getParty(party_id);
            return new BaseResponse<>(partyReadResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 필터링 API
     * [GET] /party/search?pin_id=1
     * [GET] /party/search?univ_id=1
     */
    @GetMapping("/search")
    public BaseResponse<List<PartyReadResDto>> getPartyListByPin(PartySearchKeyword keyword) throws BaseException {
        System.out.println(keyword);
        if (keyword.getPin_id() == null && keyword.getUniv_id() == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        List<PartyReadResDto> partyReadResDto = new ArrayList<PartyReadResDto>();

        if (keyword.getUniv_id() == null && keyword.getPin_id() != null) {
            partyReadResDto = partyProvider.getPartyListByPin(keyword.getPin_id());
            return new BaseResponse<>(partyReadResDto);
        }

        if (keyword.getPin_id() == null && keyword.getUniv_id() != null) {
            partyReadResDto = partyProvider.getPartyListByUniv(keyword.getUniv_id());
            return new BaseResponse<>(partyReadResDto);
        }

        return new BaseResponse<>(partyReadResDto);

    }

    @PatchMapping("/expire/{partyId}")
    public BaseResponse<String> expireParty(@PathVariable("partyId") Long partyId) throws BaseException {
        try {
            System.out.println("expireParty Controller");
            String party = partyProvider.getPartyExpire(partyId);
            System.out.println("partyExpire = " + party);
            System.out.println("getParty 실행 후");
            PartyExpireReqDto partyExpireReq = new PartyExpireReqDto(partyId, party);
            partyService.updatePartyStatus(partyExpireReq);
            System.out.println("updatePartyStatus 실행 후");

            String result = "파티가 만료되었습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}


