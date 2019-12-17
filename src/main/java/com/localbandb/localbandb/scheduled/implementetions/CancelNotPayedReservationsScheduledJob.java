package com.localbandb.localbandb.scheduled.implementetions;

import com.localbandb.localbandb.scheduled.ScheduledJob;
import com.localbandb.localbandb.services.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class CancelNotPayedReservationsScheduledJob implements ScheduledJob {


    private final ReservationService reservationService;

    public CancelNotPayedReservationsScheduledJob(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    @Scheduled(cron = "0 0 2 ? * * *")
    public void scheduledJob() {
        reservationService.findReservationsToCancelAndCancelAutomaticScheduled();
    }
}
