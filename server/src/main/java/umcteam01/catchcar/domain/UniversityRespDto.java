package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityRespDto {
    Long id;
    String name;
    Long location;
    String status;
    Date createdAt;
    Date updateAt;
    String nameDetail;
    Double longtitude;
    Double latitude;






}
