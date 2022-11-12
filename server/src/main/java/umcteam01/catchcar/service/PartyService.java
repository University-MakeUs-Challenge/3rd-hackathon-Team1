package umcteam01.catchcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.config.BaseResponseStatus;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.web.PartyDao;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyDao partyDao;
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
