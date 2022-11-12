package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyCancelRespDto {
    private Long id;
    private Long member;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String active;
    private String email;
    private String nickname;

}
