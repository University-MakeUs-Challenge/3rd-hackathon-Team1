package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;
import umcteam01.catchcar.web.PartyDao;

import java.util.List;

import umcteam01.catchcar.domain.PartyReadResDto;

import static umcteam01.catchcar.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class PartyProvider {
    private final PartyDao partyDao;

    // ACTIVE 상태인 참여자 List
    public List<PartyCancleRespDto> getParticipations(PartyCancleReqDto partyCancleReqDto) throws BaseException {
        try {
            List<PartyCancleRespDto> partyCancleRespDtos = partyDao.getParticipations(partyCancleReqDto);
            return partyCancleRespDtos;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PartyReadResDto getParty(Long party_id) throws BaseException{
        try {
            PartyReadResDto partyReadResDto = partyDao.getParty(party_id);
            return partyReadResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PartyReadResDto> getPartyList() throws BaseException{
        try {
            List<PartyReadResDto> partyReadResDto = partyDao.getPartyList();
            return partyReadResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PartyReadResDto> getPartyListByPin(Long pin_id) throws BaseException {
        try {
            List<PartyReadResDto> partyReadResDto = partyDao.getPartyListByPin(pin_id);
            return partyReadResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PartyReadResDto> getPartyListByUniv(Long univ_id) throws BaseException{
        System.out.println(univ_id);
        try {
            List<PartyReadResDto> partyReadResDto = partyDao.getPartyListByUniv(univ_id);
            return partyReadResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
