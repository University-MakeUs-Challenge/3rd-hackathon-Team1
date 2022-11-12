package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;

import umcteam01.catchcar.domain.*;
import umcteam01.catchcar.web.PartyDao;
import umcteam01.catchcar.web.UserDao;

import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;

import java.util.List;

import umcteam01.catchcar.domain.PartyReadResDto;

import static umcteam01.catchcar.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class PartyProvider {

    private final PartyDao partyDao;
    private final UserDao userDao;

    /**
     * 같은 생성자가 만든 파티그룹이 이미 존재하는 경우
     * - 한 사용자가 만들 수 있는 파티 수는 1개로 제한됨
     *
     * @param userId
     * @return
     */
    public Long checkPartyLeader(Long userId) throws BaseException {
        try {
            return partyDao.checkPartyLeader(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // ACTIVE 상태인 참여자 List
    public List<PartyCancleRespDto> getParticipations(PartyCancleReqDto partyCancleReqDto) throws BaseException {
        try {
            List<PartyCancleRespDto> partyCancleRespDtos = partyDao.getParticipations(partyCancleReqDto);
            return partyCancleRespDtos;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PartyReadResDto getParty(Long party_id) throws BaseException {
        try {
            PartyReadResDto partyReadResDto = partyDao.getParty(party_id);
            return partyReadResDto;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 특정 id값을 가지는 유저 조회
     *
     * @param userId
     * @return
     * @throws BaseException
     */
    public User getUser(Long userId) throws BaseException {
        try {
            return userDao.getUser(userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PartyReadResDto> getPartyList() throws BaseException {
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

