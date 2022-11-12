package umcteam01.catchcar.domain;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class PartyJoinReqDto {
    private Long party_id;
    private Long user_id;
}
