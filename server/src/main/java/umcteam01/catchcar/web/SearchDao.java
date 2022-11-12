package umcteam01.catchcar.web;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.UniversityRespDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchDao {
    private final JdbcTemplate jdbcTemplate;

    public List<UniversityRespDto> getUniversity(String universityName) {
        String getParticipateQuery = "" +
                "select univ.id, univ.name, univ.location, univ.status, univ.createdAt, univ.updateAt, L.name, L.longtitude, L.latitude " +
                "from University AS univ " +
                "LEFT OUTER JOIN Location L on L.id = univ.location " +
                "Where longtitude IS NOT NULL AND univ.name like '%?%'";

        return this.jdbcTemplate.query(getParticipateQuery,
                (rs, rowNum) -> new UniversityRespDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("location"),
                        rs.getString("status"),
                        rs.getDate("createdAt"),
                        rs.getDate("updateAt"),
                        rs.getString("name"),
                        rs.getDouble("longtitude"),
                        rs.getDouble("latitude")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                ,universityName); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }
}
