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
    private String pin;   // 모임장소
    private String destination;   // 목적지
    private int minFull;   // 최소인원
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private Status active;
    private Date expiredAt;

}
