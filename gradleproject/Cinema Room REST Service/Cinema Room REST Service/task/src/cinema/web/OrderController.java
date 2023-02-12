package cinema.web;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Token;
import cinema.repo.CinemaRepository;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import cinema.util.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping()
public class OrderController {

    private static final Logger LOGGER =  LoggerFactory.getLogger(OrderRepository.class);
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

                throw new OrderException("The number of a row or a column is out of bounds!");
            }
            if (_seat.isAvailable()) {

                _seat.setIsAvailable(false);

                seatRepository.save(_seat);

                Order order = new Order(_seat);

                LOGGER.info("Order " + order);


                orderRepository.save(order);

//                responseBody.putAll(Map.of("row",_seat.getRow(),"column",_seat.getColumn(),"price",_seat.getPrice()));
                return new ResponseEntity<>(order, HttpStatus.OK);

            } else {

                throw new OrderException("The ticket has been already purchased!");
            }
        } catch (OrderException e) {

//                throw new SeatPurchaseException(e.getError());
                responseBody.put("error", e.getError());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/return")
    public ResponseEntity<Object> refundTicket(@RequestBody Token token)  {

        HashMap<String, Object> responseBody = new HashMap<>();

        try {

            Order order = orderRepository.findByToken(token.getToken());

            if (order == null) {
                throw new OrderException("Wrong token!");
            }

            Seat seat = order.getTicket();

            seat.setIsAvailable(true);

            LOGGER.info("Refund ticket: " + seat.isAvailable());

            seatRepository.save(seat);

            order.setTicket(null);

            orderRepository.delete(order);

            responseBody.putAll(Map.of("returned_ticket",seat));

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (OrderException e) {

//                throw new SeatPurchaseException(e.getError());
            responseBody.put("error", e.getError());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

        }
    }


}
