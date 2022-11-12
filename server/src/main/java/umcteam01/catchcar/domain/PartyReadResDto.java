package umcteam01.catchcar.domain;

import lombok.*;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartyReadResDto {
    private Long id;
    private String univ;
    private String pin;   // 모임장소
    private String destination;   // 목적지
    private int minFull;   // 최소인원
    private String active; // 모임 상태 (ACTIVE: 인원 모집중, SUCCESS: 인원 모집 완료, TIMEOVER: 모집 시간 초과)
    private Long timer;
    private Long memberNum;

}
