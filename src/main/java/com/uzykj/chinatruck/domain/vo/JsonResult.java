package com.uzykj.chinatruck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ghostxbh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {
    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;


    private Integer code;
    private String message;
    private T data;

    public JsonResult(T data) {
        this.code = SUCCESS;
        this.message = "ok";
        this.data = data;
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult<?> success() {
        return new JsonResult(SUCCESS, "ok");
    }

    public JsonResult<?> fail(String message) {
        return new JsonResult(FAIL, message);
    }

    public JsonResult<?> error(RuntimeException e) {
        return new JsonResult(FAIL, e.getMessage());
    }
}
