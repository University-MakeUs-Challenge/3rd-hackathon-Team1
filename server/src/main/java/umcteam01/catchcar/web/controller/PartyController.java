package umcteam01.catchcar.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponse;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCreateReqDto;
import umcteam01.catchcar.domain.PartyCreateResDto;
import umcteam01.catchcar.service.PartyProvider;
import umcteam01.catchcar.service.PartyService;

import static umcteam01.catchcar.config.BaseResponseStatus.POST_PARTY_EXISTS_LEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/party")
public class PartyController {

    @Autowired private final PartyProvider partyProvider;
    @Autowired private final PartyService partyService;

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

}
