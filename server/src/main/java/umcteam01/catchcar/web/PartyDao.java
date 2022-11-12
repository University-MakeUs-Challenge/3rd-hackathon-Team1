package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.*;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PartyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<PartyJoinRes> participateParty(PartyJoinReq partyJoinReq){
        String participatePartyQuery = "insert into Participate (id, member, status, createdAt, updateAt, active)" +
                "VALUE(?, ?, \"ACTIVE\", now(), now(), \"ACTIVE\")";
        Object[] participatePartyParams = new Object[]{partyJoinReq.getUser_id(), partyJoinReq.getParty_id()};
        this.jdbcTemplate.update(participatePartyQuery, participatePartyParams);

        String displayPartyQuery = "SELECT id FROM Participate WHERE member = ?";
        Long displayPartyParams = partyJoinReq.getParty_id();
        return this.jdbcTemplate.query(displayPartyQuery,
                (rs, rowNum) -> new PartyJoinRes(rs.getLong("id")),
                displayPartyParams);
    }
}
