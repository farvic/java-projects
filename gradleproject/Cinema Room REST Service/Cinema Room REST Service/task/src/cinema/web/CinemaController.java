package cinema.web;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.repo.CinemaRepository;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import cinema.util.SeatPurchaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:28852")
@RestController
//@RequestMapping("/seats")
public class CinemaController {


    private final CinemaRepository cinemaRepository;
    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;

    public CinemaController(CinemaRepository cinemaRepository, SeatRepository seatRepository, OrderRepository orderRepository) {
        this.cinemaRepository = cinemaRepository;
        this.seatRepository = seatRepository;
        this.orderRepository = orderRepository;
    }



    private static final Logger LOGGER =  LoggerFactory.getLogger(CinemaRepository.class);

    @GetMapping("/seats")
    public ResponseEntity<Cinema> getCinema() {

        Cinema cinema;

        try {
            cinema = cinemaRepository.findById(1)
                    .orElseThrow(() -> new ResourceNotFoundException("The cinema is closed"));

            List<Seat> seats = seatRepository.findByCinemaIdAndIsAvailable(1, true);

            cinema.setAvailableSeats(seats);

            return new ResponseEntity<>(cinema,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}