package care.cuddliness.hex.statuscode;

import lombok.Getter;

public class StatusCode {

    @Getter
    private String code;
    @Getter
    private String description;

    public StatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
