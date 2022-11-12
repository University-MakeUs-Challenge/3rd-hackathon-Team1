package umcteam01.catchcar.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponse;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.*;
import umcteam01.catchcar.service.PartyProvider;
import umcteam01.catchcar.service.PartyService;

import static umcteam01.catchcar.config.BaseResponseStatus.REQUEST_ERROR;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static umcteam01.catchcar.config.BaseResponseStatus.POST_PARTY_EXISTS_LEADER;

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
    public BaseResponse<List<PartyCancleRespDto>> partyCancle(@RequestBody PartyCancleReqDto partyCancleReqDto) {
        System.out.println(partyCancleReqDto);
        try {
            partyService.modifyParticipateActive(partyCancleReqDto);

        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
        // party id 값이 없을때
        if (partyCancleReqDto.getParty_id() == null) {
            return new BaseResponse<>(BaseResponseStatus.PATCH_PARTY_EMPTY_PARTY_ID);
        }
        // user id 값이 없을때
        if (partyCancleReqDto.getUser_id() == null) {
            return new BaseResponse<>(BaseResponseStatus.PATCH_PARTY_EMPTY_USER_ID);
        }
        try {
            List<PartyCancleRespDto> partyCancleRespDtos = partyProvider.getParticipations(partyCancleReqDto);
            return new BaseResponse<>(partyCancleRespDtos);
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
     * 필터링 API
     * [GET] /party/search?pin_id=1
     * [GET] /party/search?univ_id=1
     */
    @GetMapping("/search")
    public BaseResponse<List<PartyReadResDto>> getPartyListByPin(PartySearchKeyword keyword) throws BaseException {
        if (keyword.getPin_id() == null && keyword.getUniv_id() == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        List<PartyReadResDto> partyReadResDto = new ArrayList<PartyReadResDto>();

        if (keyword.getUniv_id() == null) {
            partyReadResDto = partyProvider.getPartyListByPin(keyword.getPin_id());
        }

        if (keyword.getPin_id() == null) {
            partyReadResDto = partyProvider.getPartyListByUniv(keyword.getUniv_id());
        }

        return new BaseResponse<>(partyReadResDto);

    }



}


