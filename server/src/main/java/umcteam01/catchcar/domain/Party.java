package umcteam01.catchcar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
public class Party {
    private Long id;
    private String univ;
    private String pin;   // 모임장소명 
    private String destination;   // 목적지명
    private String leader; // 모임 생성자 닉네임
    private int minFull;   // 최소인원
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private Status active;
    private String expiredAt;

}
