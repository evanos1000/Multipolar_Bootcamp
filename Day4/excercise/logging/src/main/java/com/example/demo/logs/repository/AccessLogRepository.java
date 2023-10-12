package com.example.demo.logs.repository;

import org.apache.catalina.AccessLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessLogRepository extends MongoRepository<AccessLog,String> {

}
