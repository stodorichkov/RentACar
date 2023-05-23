package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.StatusEntity;
import com.example.demo.model.enums.StatusEnum;
import com.example.demo.repository.StatusRepository;
import com.example.demo.service.service.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;


    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }


    @Override
    public void seedStatus() {

        if(this.statusRepository.count()!=0) {
            return;
        }
        StatusEntity reserved = new StatusEntity(StatusEnum.Reserved,0.0);
        StatusEntity active = new StatusEntity(StatusEnum.Active,0.0);
        StatusEntity canceled = new StatusEntity(StatusEnum.Canceled,0.0);
        StatusEntity late = new StatusEntity(StatusEnum.Late,0.0);
        StatusEntity completedOnTime = new StatusEntity(StatusEnum.CompletedOnTime,1.5);
        StatusEntity completedEarly = new StatusEntity(StatusEnum.CompletedEarly,1.0);
        StatusEntity completedLate = new StatusEntity(StatusEnum.CompletedLate,0.5);
        this.statusRepository.saveAll(List.of(
                reserved,active,canceled,late,completedOnTime,completedEarly,completedEarly,completedLate));

    }

    @Override
    public StatusEntity findByStatus(StatusEnum status) {
        return this.statusRepository.findByStatus(status).orElseThrow(
                () -> new ObjectNotFoundException("Status with name:" + status + " was not found.")
        );

    }
}
