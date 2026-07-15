package ee.mihkel.backend0626.service;

import ee.mihkel.backend0626.entity.Order;
import ee.mihkel.backend0626.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CronService {

    // * ---> sekundid 0-59
    // * * ---> minutid 0-59
    // * * * ---> tunnid 0-23
    // * * * * ---> kuus olev päev 1-31
    // * * * * * ---> kuu 1-12 või Jan-Dec
    // * * * * * *---> nädalapäev 0-7, kus 0 ja 7 on mõlemad pühapäev

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 8-20 * * 1-5")
    public void sendReminders() {
        Date current = new Date();
        Date after = new Date(current.getTime() + TimeUnit.HOURS.toMillis(24));
        Date before = new Date(current.getTime() + TimeUnit.HOURS.toMillis(25));
        List<Order> ordersToBeSentReminder = orderRepository.findByCreatedAfterAndCreatedBefore(after, before);
    }

    // iga kuu viimasel tööpäeval kell 17
    @Scheduled(cron = "* * 17 LW * *")
    public void sendWorkReport() {

    }
}
