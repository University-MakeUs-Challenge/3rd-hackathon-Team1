package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.Party;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;

import javax.sql.DataSource;

@Repository
public class PartyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int partyCancle(PartyCancleReqDto partyCancleReqDto) {
        String getUserQuery = "UPDATE Participate SET status WHERE id = ? AND member = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        String status = "INACTIVE";
        Object[] modifyParticipateStatusParams = new Object[]{status, partyCancleReqDto.getParty_id(), partyCancleReqDto.getUser_id()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(getUserQuery,modifyParticipateStatusParams);
    }


}
