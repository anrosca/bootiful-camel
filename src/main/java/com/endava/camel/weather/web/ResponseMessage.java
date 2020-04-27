package com.endava.camel.weather.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {
    private Object payload;
    private ResponseStatus status;

    public static ResponseMessage ok() {
        return builder()
                .status(ResponseStatus.OK)
                .build();
    }

    public static ResponseMessage ok(Object payload) {
        return builder()
                .status(ResponseStatus.OK)
                .payload(payload)
                .build();
    }
}
