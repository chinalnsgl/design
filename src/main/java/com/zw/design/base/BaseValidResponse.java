package com.zw.design.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BaseValidResponse implements Serializable {
    private Boolean valid ;

    public static BaseValidResponse SUCCESS = new BaseValidResponse(true);
    public static BaseValidResponse FAILE = new BaseValidResponse(false);

    public static BaseValidResponse toResponse(Object o) {
        if (o == null) {
            return SUCCESS;
        }
        return FAILE;
    }

}
