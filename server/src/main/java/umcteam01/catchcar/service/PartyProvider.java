package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;
import umcteam01.catchcar.web.PartyDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyProvider {
    private final PartyDao partyDao;

    // User들의 정보를 조회
    public List<PartyCancleRespDto> getParticipations(PartyCancleReqDto partyCancleReqDto) throws BaseException {
        try {
            List<PartyCancleRespDto> partyCancleRespDtos = partyDao.getParticipations(partyCancleReqDto);
            return partyCancleRespDtos;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
