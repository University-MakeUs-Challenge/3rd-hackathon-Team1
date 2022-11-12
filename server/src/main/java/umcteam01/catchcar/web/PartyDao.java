package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.config.BaseException;
import umcteam01.catchcar.domain.PartyCreateReqDto;
import umcteam01.catchcar.domain.PartyCreateResDto;

import javax.sql.DataSource;

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
     * @param partyReq
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
}
