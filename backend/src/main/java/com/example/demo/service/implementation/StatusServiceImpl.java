package com.example.demo.service.implementation;

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
        StatusEntity active = new StatusEntity(StatusEnum.Active,0.0);
        StatusEntity completed = new StatusEntity(StatusEnum.Completed,1.5);
        StatusEntity halfCompleted = new StatusEntity(StatusEnum.HalfCompleted,1.0);
        StatusEntity canceled = new StatusEntity(StatusEnum.Canceled,0.5);
        StatusEntity late = new StatusEntity(StatusEnum.Late,0.2);
        this.statusRepository.saveAll(List.of(active,completed,halfCompleted,canceled,late));

    }
}
