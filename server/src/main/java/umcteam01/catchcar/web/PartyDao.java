package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.Party;
import umcteam01.catchcar.domain.PartyCancleReqDto;
import umcteam01.catchcar.domain.PartyCancleRespDto;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PartyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int partyCancle(PartyCancleReqDto partyCancleReqDto) {
        String getUserQuery = "UPDATE Participate SET active WHERE id = ? AND member = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        String status = "INACTIVE";
        Object[] modifyParticipateStatusParams = new Object[]{status, partyCancleReqDto.getParty_id(), partyCancleReqDto.getUser_id()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(getUserQuery,modifyParticipateStatusParams);
    }

    public List<PartyCancleRespDto> getParticipations(PartyCancleReqDto partyCancleReqDto) {
        String getParticipateQuery = "select * from Participate where id = ? AND statuc = ?"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        Long partyId = partyCancleReqDto.getParty_id();
        String status = "ACTIVE";
        return this.jdbcTemplate.query(getParticipateQuery,
                (rs, rowNum) -> new PartyCancleRespDto(
                        rs.getLong("id"),
                        rs.getLong("member"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getDate("updateAt"),
                        rs.getDate("active")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                ,partyId,status); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }


}
