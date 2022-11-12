package umcteam01.catchcar.web;

import org.springframework.jdbc.core.JdbcTemplate;
import umcteam01.catchcar.domain.*;

public class PartyDao {

    private JdbcTemplate jdbcTemplate;
    public int participateParty(PartyJoinReq partyJoinReq){
        String participatePartyQuery = "insert into Participate (id, member, status, createdAt, updateAt, active)" +
                "VALUE(1, 1, \"ACTIVE\", now(), now(), \"ACTIVE\")";
        Object[] participatePartyParams = new Object[]{partyJoinReq.getUser_id(), partyJoinReq.getParty_id()};

        return this.jdbcTemplate.update(participatePartyQuery, participatePartyParams);
    }
}
