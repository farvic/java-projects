package cinema.web;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import cinema.util.SeatPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
public class OrderController {

    private final OrderRepository orderRepository;
    private SeatRepository seatRepository;

    public OrderController(OrderRepository orderRepository, SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseSeat(@RequestBody Seat seat)  {

        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Seat _seat = seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn());

            if (_seat == null) {

                throw new SeatPurchaseException("The number of a row or a column is out of bounds!");
            }
            if (_seat.isAvailable()) {

                _seat.setIsAvailable(false);

                seatRepository.save(_seat);

                Order order = new Order(_seat);

                orderRepository.save(order);

//                responseBody.putAll(Map.of("row",_seat.getRow(),"column",_seat.getColumn(),"price",_seat.getPrice()));
                return new ResponseEntity<>(order, HttpStatus.OK);

            } else {

                throw new SeatPurchaseException("The ticket has been already purchased!");
            }
        } catch (SeatPurchaseException e) {

//                throw new SeatPurchaseException(e.getError());
                responseBody.put("error", e.getError());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

        }
    }


}
