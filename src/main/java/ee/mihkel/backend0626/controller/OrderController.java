package ee.mihkel.backend0626.controller;

import ee.mihkel.backend0626.dto.OrderRowDto;
import ee.mihkel.backend0626.dto.ParcelMachine;
import ee.mihkel.backend0626.dto.PaymentLink;
import ee.mihkel.backend0626.entity.Order;
import ee.mihkel.backend0626.entity.OrderRow;
import ee.mihkel.backend0626.repository.OrderRepository;
import ee.mihkel.backend0626.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrderDetails(@PathVariable Long id) throws ExecutionException {
        return orderService.getOrderDetails(id);
    }

    @PostMapping("orders")
    public PaymentLink saveOrder(@RequestBody List<OrderRowDto> orderRowDtos) {
         Order order = orderService.saveOrder(orderRowDtos); // miks salvestada enne? 1) Maksesse saama saata ID
        // 2) Kui raha läheb maha, aga andmebaasi miskipärast ei salvestu, siis on jälg olemas
        return orderService.makePayment(order);
    }

    @GetMapping("check-payment")
    public boolean checkPayment(@RequestParam String paymentReference) {
        return orderService.checkPayment(paymentReference);
    }

    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        return orderService.getParcelMachines(country);
    }
}
