package umcteam01.catchcar.web.controller;

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

import java.util.List;

import static umcteam01.catchcar.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/party")
public class PartyController {

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



    @PatchMapping("/{id}")
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
     * 필터링 API (pin_id 기준)
     * [GET] /party/search?pin_id=1
     */
    @GetMapping("/search")
    public BaseResponse<List<PartyReadResDto>> getPartyListByPin(@RequestParam("pin_id") Long pin_id) {
        if (pin_id == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        try {
            List<PartyReadResDto> partyReadResDto = partyProvider.getPartyListByPin(pin_id);
            return new BaseResponse<>(partyReadResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @PatchMapping("/expire/{id}")
    public BaseResponse<String> expireParty(@PathVariable("id") Long partyId) throws BaseException {
        try {
            PartyReadResDto party = partyProvider.getParty(partyId);
            PartyExpireReqDto partyExpireReq = new PartyExpireReqDto(partyId, party.getExpiredAt());
            partyService.updatePartyStatus(partyExpireReq);

            String result = "파티가 만료되었습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}


