package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCreateReqDto;
import umcteam01.catchcar.domain.PartyCreateResDto;
import umcteam01.catchcar.web.PartyDao;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static umcteam01.catchcar.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class PartyService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PartyDao partyDao;
    private final PartyProvider partyProvider;

    public PartyCreateResDto createParty(PartyCreateReqDto partyReq) throws BaseException {

        getExpiredTime(partyReq);

        try {
            Long partyId = partyDao.createParty(partyReq);

            return new PartyCreateResDto(partyId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

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

    public void updatePartyStatus(PartyCreateResDto partyRes) throws BaseException {
        try {
            partyDao.updatePartyStatus(partyRes);
        } catch (Exception exception) {
            throw new BaseException(POST_PARTY_STATUS_ERROR);

    // 회원정보 수정(Patch)
    public void modifyParticipateActive(PartyCancleReqDto partyCancleReqDto) throws BaseException {
        try {
            int result = partyDao.partyCancle(partyCancleReqDto); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_USER_ACTIVE);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
