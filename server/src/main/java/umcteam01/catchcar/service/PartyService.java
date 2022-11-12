package umcteam01.catchcar.service;

import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.domain.PartyJoinReq;
import umcteam01.catchcar.domain.PartyJoinRes;
import umcteam01.catchcar.web.*;

import static umcteam01.catchcar.config.BaseResponseStatus.*;

@Service
public class PartyService {
    private final PartyDao partyDao;

    public PartyService(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    public PartyJoinRes participateParty(PartyJoinReq partyJoinReq) throws BaseException {
        try {
            int result = partyDao.participateParty(partyJoinReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
            return new PartyJoinRes(true, 200, "요청에 성공하였습니다.");
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
