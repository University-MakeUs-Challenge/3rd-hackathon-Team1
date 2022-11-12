package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {

    private Long userId;
    private String email;
    private String password;
    private String nickname;
//    private String univ;
//    private String location;
}
