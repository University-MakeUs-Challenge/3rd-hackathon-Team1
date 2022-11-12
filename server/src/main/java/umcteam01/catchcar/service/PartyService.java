package umcteam01.catchcar.service;

import org.springframework.stereotype.Service;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.domain.PartyJoinReq;
import umcteam01.catchcar.domain.PartyJoinRes;
import umcteam01.catchcar.web.*;

import java.util.List;

import static umcteam01.catchcar.config.BaseResponseStatus.*;

@Service
public class PartyService {
    private final PartyDao partyDao;

    public PartyService(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    public List<PartyJoinRes> participateParty(PartyJoinReq partyJoinReq) throws BaseException {
            List<PartyJoinRes> partyJoinRes = partyDao.participateParty(partyJoinReq);
            if (partyJoinRes.size() == 0){  // participate 추가에 실패한 경우
                throw new BaseException(INVALID_PARTICIPATE);
            }
            return partyJoinRes;

    }
}
