package com.example.demo.service.service;

import com.example.demo.model.StatusEntity;

public interface StatusService {

    void seedStatus();

    StatusEntity findByStatus(String status);
}
