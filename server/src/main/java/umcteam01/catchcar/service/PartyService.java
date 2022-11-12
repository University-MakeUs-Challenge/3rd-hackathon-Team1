package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCancelReqDto;
import umcteam01.catchcar.domain.PartyCreateReqDto;
import umcteam01.catchcar.domain.PartyCreateResDto;
import umcteam01.catchcar.domain.PartyExpireReqDto;
import umcteam01.catchcar.web.PartyDao;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static umcteam01.catchcar.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class PartyService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PartyDao partyDao;
    private final PartyProvider partyProvider;

    // 파티 생성
    public PartyCreateResDto createParty(PartyCreateReqDto partyReq) throws BaseException {

        getExpiredTime(partyReq);

        try {
            Long partyId = partyDao.createParty(partyReq);

            return new PartyCreateResDto(partyId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 만료시간까지 남은 시간을 계산하고 세팅
    private void getExpiredTime(PartyCreateReqDto partyReq) throws BaseException {
        // 만료시간 메시지 설정
        try {
            LocalTime now = LocalTime.now();
            LocalTime expire = LocalTime.now().plusMinutes(partyReq.getTimer());
            String expireMessage = ChronoUnit.MINUTES.between(now, expire) + "분 " + (ChronoUnit.SECONDS.between(now, expire) % 60) + "초 전";
            System.out.println(expireMessage);
            partyReq.setExpiredAt(expireMessage);
        } catch (Exception exception) {
            throw new BaseException(POST_PARTY_EXPIRE_TIME);
        }
    }

    // 파티 상태 변경 (SUCCESS / TIMEOVER / ACTIVE)
    public void updatePartyStatus(PartyExpireReqDto partyReq) throws BaseException {
        try {
            
            // TODO 파티 인원 수가 찬 경우 SUCCESS 로 상태 변경
            String res = "SUCCESS";
            int minuteIdx = partyReq.getExpiredAt().indexOf('분');
            if(isTimeOver(partyReq, res, minuteIdx)) res = "TIMEOVER";
            partyDao.updatePartyActive(partyReq, res);
            partyDao.updatePartyStatus(partyReq);
        } catch (Exception exception) {
            throw new BaseException(POST_PARTY_STATUS_ERROR);
        }
    }

    // 시간 만료 여부 체크
    private boolean isTimeOver(PartyExpireReqDto partyReq, String res, int minuteIdx) {
        System.out.println(Integer.parseInt(partyReq.getExpiredAt().substring(0, minuteIdx)));
        System.out.println(Integer.parseInt(partyReq.getExpiredAt().substring(minuteIdx +2, partyReq.getExpiredAt().length()-2)));
        if (Integer.parseInt(partyReq.getExpiredAt().substring(0, minuteIdx)) <= 0 && Integer.parseInt(partyReq.getExpiredAt().substring(minuteIdx +2, partyReq.getExpiredAt().length()-2)) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    // 회원정보 수정(Patch)
    public void modifyParticipateActive(PartyCancelReqDto partyCancelReqDto) throws BaseException {
        try {
            int result = partyDao.partyCancel(partyCancelReqDto); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_USER_ACTIVE);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
