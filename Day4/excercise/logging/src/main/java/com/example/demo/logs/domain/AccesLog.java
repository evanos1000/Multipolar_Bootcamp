package com.example.demo.logs.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "access_log")
public class AccesLog implements Serializable {
    @Id
    private String id;
    private String requestMethod;
    private String requestUri;
    private int responseStatusCode;
    private LocalDateTime timeStamp;
    private String content;

}
