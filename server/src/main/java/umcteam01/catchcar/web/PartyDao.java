package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.domain.*;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PartyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDatasource(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }


    /**
     * 새로운 파티 생성
     * @param partyReq
     * @return partyId
     * @throws BaseException
     */
    public Long createParty(PartyCreateReqDto partyReq)  {
        String createPartyQuery = "insert into Party (univ, pin, destination, min_full, timer, leader) VALUES (?,?,?,?,?,?)";
        Object[] createPartyParams = new Object[]{partyReq.getUniv(), partyReq.getPin(), partyReq.getDestination(), partyReq.getMinFull(), partyReq.getTimer(), partyReq.getLeader()};
        this.jdbcTemplate.update(createPartyQuery, createPartyParams);

        System.out.println("PartyDao 실행: createParty");
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public Long checkPartyLeader(Long userId) {
        String checkPartyLeaderQuery = "select exists(select leader from Party where leader=?)";
        Long checkPartyLeaderParam = userId;
        return this.jdbcTemplate.queryForObject(checkPartyLeaderQuery, Long.class, checkPartyLeaderParam);
    }

    /**
     * 파티 만료 시 status 비활성화로 설정
     * @param
     */
    public void updatePartyStatus(PartyCreateResDto partyRes) {
        String updatePartyStatusQuery = "update Party set status='INACTIVE' where id=?";
        Long updatePartyStatusParam = partyRes.getPartyId();
        this.jdbcTemplate.update(updatePartyStatusQuery, updatePartyStatusParam);
    }

    /**
     * 파티의 상태값을 저장 (ACTIVE : 인원 모집 중, SUCCESS: 모집 완료, TIMEOVER: 시간만료)
     */
//    public void updatePartyActive()


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

    public int partyCancle(PartyCancleReqDto partyCancleReqDto) {
        String getUserQuery = "UPDATE Participate SET active = ? WHERE id = ? AND member = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        String status = "INACTIVE";
        Object[] modifyParticipateStatusParams = new Object[]{status, partyCancleReqDto.getParty_id(), partyCancleReqDto.getUser_id()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(getUserQuery,modifyParticipateStatusParams);
    }

    public List<PartyCancleRespDto> getParticipations(PartyCancleReqDto partyCancleReqDto) {
        String getParticipateQuery = "select * from Participate AS pa " +
                                        "LEFT OUTER JOIN User AS user ON pa.member = user.id where pa.id = ? AND pa.active = ? ";
        Long partyId = partyCancleReqDto.getParty_id();
        String status = "ACTIVE";
        return this.jdbcTemplate.query(getParticipateQuery,
                (rs, rowNum) -> new PartyCancleRespDto(
                        rs.getLong("id"),
                        rs.getLong("member"),
                        rs.getString("status"),
                        rs.getDate("createdAt"),
                        rs.getDate("updateAt"),
                        rs.getString("active"),
                        rs.getString("email"),
                        rs.getString("nickname")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
               ,partyId ,status); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }

    public PartyReadResDto getParty(Long party_id) {
        String getPartyQuery = "select p.id, un.name, pin.name, des.name, p.min_full, p.active, p.timer\n" +
                "                from Party p, University un, Location pin, Location des, User u\n" +
                "                where p.id = ? and p.status='ACTIVE'\n" +
                "                and p.univ = un.id and p.pin = pin.id and p.destination = des.id and p.leader = u.id";
        Long getPartyParams = party_id;

        return this.jdbcTemplate.queryForObject(getPartyQuery,
                (rs, rowNum) -> new PartyReadResDto(
                        rs.getLong("p.id"),
                        rs.getString("un.name"),
                        rs.getString("pin.name"),
                        rs.getString("des.name"),
                        rs.getInt("p.min_full"),
                        rs.getString("p.active"),
                        rs.getLong("p.timer")),
                getPartyParams);
    }

    public List<PartyReadResDto> getPartyList() {
        String getPartyQuery = "select p.id, un.name, pin.name, des.name, p.min_full, p.active, p.timer\n" +
                "                from Party p, University un, Location pin, Location des, User u\n" +
                "                where p.status='ACTIVE'\n" +
                "                and p.univ = un.id and p.pin = pin.id and p.destination = des.id and p.leader = u.id" +
                "                order by p.expiredAt";

        return this.jdbcTemplate.query(getPartyQuery,
                (rs, rowNum) -> new PartyReadResDto(
                        rs.getLong("p.id"),
                        rs.getString("un.name"),
                        rs.getString("pin.name"),
                        rs.getString("des.name"),
                        rs.getInt("p.min_full"),
                        rs.getString("p.active"),
                        rs.getLong("p.timer"))
        );
    }

    public List<PartyReadResDto> getPartyListByPin(Long pin_id) {
        String getPartyQuery = "select p.id, un.name, pin.name, des.name, p.min_full, p.active, p.timer\n" +
                "                                from Party p, University un, Location pin, Location des, User u\n" +
                "                                where p.status='ACTIVE'\n" +
                "                                and p.univ = un.id and pin.id=? and p.pin = pin.id and p.destination = des.id and p.leader = u.id\n" +
                "                                order by p.expiredAt";

        Long getPartyParams = pin_id;
        System.out.println(getPartyParams);

        List<PartyReadResDto> results = jdbcTemplate.query(getPartyQuery,
                new RowMapper<PartyReadResDto>() {
                    @Override
                    public PartyReadResDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PartyReadResDto partyReadResDto = new PartyReadResDto(
                                rs.getLong("p.id"),
                                rs.getString("un.name"),
                                rs.getString("pin.name"),
                                rs.getString("des.name"),
                                rs.getInt("p.min_full"),
                                rs.getString("p.active"),
                                rs.getLong("p.timer"));
                                return partyReadResDto;
                    }
                },
                getPartyParams);
        System.out.println(results);
        return results;
    }

    public List<PartyReadResDto> getPartyListByUniv(Long univ_id) {
        String getPartyQuery = "select p.id, un.name, pin.name, des.name, p.min_full, p.active, p.timer\n" +
                "                                from Party p, University un, Location pin, Location des, User u\n" +
                "                                where p.status='ACTIVE'\n" +
                "                                and p.univ = un.id and un.id=? and p.pin = pin.id and p.destination = des.id and p.leader = u.id\n" +
                "                                order by p.expiredAt";

        Long getPartyParams = univ_id;
        System.out.println(getPartyParams);

        List<PartyReadResDto> results = jdbcTemplate.query(getPartyQuery,
                new RowMapper<PartyReadResDto>() {
                    @Override
                    public PartyReadResDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PartyReadResDto partyReadResDto = new PartyReadResDto(
                                rs.getLong("p.id"),
                                rs.getString("un.name"),
                                rs.getString("pin.name"),
                                rs.getString("des.name"),
                                rs.getInt("p.min_full"),
                                rs.getString("p.active"),
                                rs.getLong("p.timer"));
                        return partyReadResDto;
                    }
                },
                getPartyParams);
        System.out.println(results);
        return results;
    }

}
