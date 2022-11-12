package umcteam01.catchcar.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umcteam01.catchcar.domain.UniversityRespDto;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("")
    public List<UniversityRespDto> getUniversityList(@RequestParam String uni_name){

        return null;
    }
}
