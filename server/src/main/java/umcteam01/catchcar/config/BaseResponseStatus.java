package umcteam01.catchcar.config;

import lombok.Getter;

/**
 * 에러 코드 관리 -> 임의로 코드와 메시지 지정
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),

    // user 관련
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false, 2017, "중복된 이메일입니다."),

    POST_USERS_EMPTY_PASSWORD(false, 2020, "비밀번호를 입력해주세요."),
    USERS_PASSWORD_FORMAT(false, 2021, "비밀번호는 8자 이상의 영문 대소문자와 특수문자로 구성해야 합니다."),

    POST_PARTY_EMPTY_VALUE(false, 2030, "값을 입력하지 않았습니다."),
    POST_PARTY_EXISTS_LEADER(false, 2031, "이미 파티그룹을 생성했습니다."),
    POST_PARTY_STATUS_ERROR(false, 2032, "활성상태 변경을 실패했습니다."),
    POST_PARTY_EXPIRE_TIME(false, 2033, "만료 시간을 갱신할 수 없습니다."),
    
    INVALID_PARTICIPATE(false, 2040, "유효하지 않은 파티입니다."),

    /**
     * 3000 : Response 오류
     */
    // common
    RESPONSE_ERROR(false, 3000, "값을 불러오는 데 실패했습니다."),

    // user 관련
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3014, "존재하지 않는 아이디이거나 비밀번호가 틀렸습니다."),
    BANNED_USER_IN_LOGIN(false, 3015, "정지된 유저이므로 로그인이 불가합니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패했습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패했습니다."),

    // [PATCH] user 정보 수정 시
    MODIFY_FAIL_USERNAME(false, 4014, "회원 이름을 변경하는 데 실패했습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패했습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패했습니다."),

    /**
     * 5000, 6000 ~ : 필요 시 직접 구현하기!
     */
    PATCH_PARTY_EMPTY_PARTY_ID(false, 5001, "그룹 아이디를 확인해주세요."),
    PATCH_PARTY_EMPTY_USER_ID(false, 5002, "유저아이디를 확인해 주세요."),
    MODIFY_FAIL_USER_ACTIVE(false, 5003, "유저 상태 변경에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
