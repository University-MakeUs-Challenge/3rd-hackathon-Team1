package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyCancleRespDto {
    private Long id;
    private Long member;
    private String status;
    private String active;
    private Date createdAt;
    private Date updatedAt;

}
