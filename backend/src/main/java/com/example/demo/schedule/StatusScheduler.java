package com.example.demo.schedule;


import com.example.demo.model.CarEntity;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.enums.StatusEnum;
import com.example.demo.repository.RentalRepository;
import com.example.demo.service.service.StatusService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatusScheduler {

    private final RentalRepository rentalRepository;

    private final StatusService statusService;

    public StatusScheduler(RentalRepository rentalRepository, StatusService statusService) {
        this.rentalRepository = rentalRepository;
        this.statusService = statusService;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void scheduled(){

        LocalDateTime now = LocalDateTime.now();

        List<RentalEntity> reservedRentals = this.rentalRepository.findAllByStatus(StatusEnum.Reserved);
        List<RentalEntity> activeRentals = this.rentalRepository.findAllByStatus(StatusEnum.Active);
        List<RentalEntity> lateRentals = this.rentalRepository.findAllByStatus(StatusEnum.Late);

        for(RentalEntity reserved : reservedRentals){
            if(reserved.getStartTime().plusHours(1).isBefore(now)){
                reserved.setStatus(this.statusService.findByStatus(StatusEnum.Active));
                this.rentalRepository.save(reserved);
            }
        }


        for(RentalEntity active : activeRentals){
            if(active.getEndTime().plusHours(2).isBefore(now)) {
                active.setEndTime(active.getEndTime().plusHours(2).plusDays(1));
                active.setStatus(this.statusService.findByStatus(StatusEnum.Late));
                this.rentalRepository.save(active);
            }
        }

        for(RentalEntity late : lateRentals){
            if(late.getEndTime().isBefore(now)){

                late.setEndTime(late.getEndTime().plusDays(1));

                CarEntity currentCar = late.getRentedCar();
                List<RentalEntity> carRentals = currentCar.getCarRental();
                for(RentalEntity r : carRentals){
                    if(StatusEnum.Reserved.equals(r.getStatus().getStatus())){
                        if(late.getEndTime().isBefore(r.getStartTime())){
                            r.setStatus(this.statusService.findByStatus(StatusEnum.Canceled));
                            this.rentalRepository.save(r);
                        }
                    }
                }
                this.rentalRepository.save(late);

            }
        }
    }
}
