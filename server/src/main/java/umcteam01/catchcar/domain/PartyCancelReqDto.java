package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyCancelReqDto {
    private Long partyId;
    private Long userId;
}
