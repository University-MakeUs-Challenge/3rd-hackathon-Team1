package umcteam01.catchcar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umcteam01.catchcar.domain.User;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDatasource(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    // 특정 id를 가지는 유저 조회
    public User getUser(Long userId) {
        String getUserQuery = "select * from User where id=?";
        Long getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new User(
                        rs.getLong("userid"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")
                ),
                getUserParams
        );
    }
}
