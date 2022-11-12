package umcteam01.catchcar.domain;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class PartyJoinRes {
    private boolean isSuccess;
    private int code;
    private String message;
}
