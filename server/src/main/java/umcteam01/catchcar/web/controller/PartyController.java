package umcteam01.catchcar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponse;
import umcteam01.catchcar.domain.PartyJoinReq;
import umcteam01.catchcar.domain.PartyJoinRes;
import umcteam01.catchcar.service.PartyService;

import java.util.List;

@RestController
public class PartyController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @ResponseBody
    @PatchMapping("/party")
    public BaseResponse<List<PartyJoinRes>> participateParty(@RequestBody PartyJoinReq partyJoinReq){
        try {
            List<PartyJoinRes> partyJoinRes = partyService.participateParty(partyJoinReq);
            return new BaseResponse<>(partyJoinRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
