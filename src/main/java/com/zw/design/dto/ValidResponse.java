package com.zw.design.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ValidResponse implements Serializable {
    private Boolean valid ;

    public static ValidResponse SUCCESS = new ValidResponse(true);
    public static ValidResponse FAILE = new ValidResponse(false);

    public static ValidResponse toResponse(Object o) {
        if (o == null) {
            return SUCCESS;
        }
        return FAILE;
    }

}
