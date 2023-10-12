package com.example.demo.gateaway.Kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessLog implements Serializable {

    private String requestMethod;
    private String requestUri;
    private int responseStatusCode;
    private LocalDateTime timeStamp;
    private String content;

}