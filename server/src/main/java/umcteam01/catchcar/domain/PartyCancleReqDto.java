package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyCancleReqDto {
    private Long party_id;
    private Long user_id;
}
