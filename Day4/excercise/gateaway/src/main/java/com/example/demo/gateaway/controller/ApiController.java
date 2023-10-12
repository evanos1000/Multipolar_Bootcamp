package com.example.demo.gateaway.controller;

import com.example.demo.gateaway.util.RestTemplateUtil;
import com.example.demo.gateaway.Kafka.AccessLog;
import com.example.demo.gateaway.dto.ErrorMessageDTO;
import com.example.demo.gateaway.dto.ProductDTO;
import com.example.demo.gateaway.service.AccessLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String PRODUCT_URL = "http://localhost:8081/product";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService logService;
    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }
    @GetMapping("/getProduct")
    public ResponseEntity<?> getProduct() throws JsonProcessingException {
        //akses API customer dan dapatkan datanya
        //untuk mengatasi error saat client mengakses
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<>(){});
            //Mengirim AccessLog
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"getProduct Success");;
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,ex.getRawStatusCode(),LocalDateTime.now()," failed");
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }
    }
    @GetMapping("/getProductid/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) throws JsonProcessingException {
        //akses API customer dan dapatkan datanya
        //untuk mengatasi error saat client mengakses
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL+"/id/"+id, new ParameterizedTypeReference<>(){});
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"getProduct Success");;
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO>errorResponse=objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body((errorResponse));
        }
    }
    @PostMapping("/createProduct")
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO customerDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, customerDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"createProduct Success");;
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            // You may want to validate the input, check if the product exists, etc.
            ResponseEntity<?> response = restTemplateUtil.put(PRODUCT_URL + "/id/" + id, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"updateProduct Success");;
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            // You may want to validate the input, check if the product exists, etc.
            ResponseEntity<?> response = restTemplateUtil.put(PRODUCT_URL + "/id/" + id, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("GET",PRODUCT_URL,response.getStatusCodeValue(), LocalDateTime.now(),"deleteProduct Success");;
            logService.logAccess(accessLog);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }


}