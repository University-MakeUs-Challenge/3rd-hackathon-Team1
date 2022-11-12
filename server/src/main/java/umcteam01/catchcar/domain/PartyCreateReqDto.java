package umcteam01.catchcar.domain;

import lombok.*;

import java.sql.Date;

/**
 * 참여 그룹(Party) 생성을 위해 서버에 전달할 데이터의 형태
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyCreateReqDto {

    private Long univ;
    private Long pin;
    private Long destination;
    private int minFull;
    private int timer;
    private Long leader;
    private String expiredAt;   // ex. "3분 00초 전"

}
