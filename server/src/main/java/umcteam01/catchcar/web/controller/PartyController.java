package umcteam01.catchcar.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponse;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;
import umcteam01.catchcar.service.PartyProvider;
import umcteam01.catchcar.service.PartyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    private final PartyProvider partyProvider;


    @PatchMapping("/party/{id}")
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
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
