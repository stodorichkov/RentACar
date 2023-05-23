package com.example.demo.service.service;

import com.example.demo.model.StatusEntity;
import com.example.demo.model.enums.StatusEnum;

public interface StatusService {

    void seedStatus();

    StatusEntity findByStatus(StatusEnum status);
}
