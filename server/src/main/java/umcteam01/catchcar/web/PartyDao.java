package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.*;

import javax.sql.DataSource;

@Repository
public class PartyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int participateParty(PartyJoinReq partyJoinReq){
        String participatePartyQuery = "insert into Participate (id, member, status, createdAt, updateAt, active)" +
                "VALUE(1, 1, \"ACTIVE\", now(), now(), \"ACTIVE\")";
        Object[] participatePartyParams = new Object[]{partyJoinReq.getUser_id(), partyJoinReq.getParty_id()};

        return this.jdbcTemplate.update(participatePartyQuery, participatePartyParams);
    }
}
