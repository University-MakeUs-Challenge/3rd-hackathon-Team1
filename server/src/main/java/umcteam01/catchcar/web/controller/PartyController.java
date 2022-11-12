package umcteam01.catchcar.web.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;
import umcteam01.catchcar.service.PartyService;

@RestController
public class PartyController {
    final PartyService partyService


    @PatchMapping("/party/{id}")
    public PartyCancleRespDto partyCancle(@PathVariable("id") Long id, PartyCancleReqDto partyCancleReqDto) {

    }
}
