package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PartyExpireReqDto {
    private Long partyId;
    private String expiredAt;

}
