package cinema.web;

import cinema.domain.Room;
import cinema.domain.Seat;
import cinema.dto.SeatDto;
import cinema.repo.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;


@CrossOrigin(origins = "http://localhost:28852")
@RestController
//@RequestMapping("/seats")
public class SeatController {

    SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }



    private static final Logger LOGGER =  LoggerFactory.getLogger(SeatRepository.class);

     @GetMapping("/all")
     public ResponseEntity<List<Seat>> getAllSeats() {
     // try {
     List<Seat> seats = new ArrayList<Seat>();

     seatRepository.findAll().forEach(seats::add);
     // else
     // seatRepository.findByTitleContaining(title).forEach(seats::add);

     if (seats.isEmpty()) {
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     return new ResponseEntity<>(seats, HttpStatus.OK);
     // } catch (Exception e) {
     // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
     // }
     }

     @PostMapping("/purchase")
        public ResponseEntity<Object> purchaseSeat(@RequestBody Seat seat) {
//            LOGGER.info("REQUEST: seat: " + seat.toString());
            HashMap<String, Object> responseBody = new HashMap<>();


            Seat _seat = seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn());

            if (_seat == null) {
                responseBody.put("error", "The number of a row or a column is out of bounds!");
                    return new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);

            }
            if (_seat.isAvailable()) {
                _seat.setIsAvailable(false);
                seatRepository.save(_seat);
                responseBody.putAll(Map.of("row",_seat.getRow(),"column",_seat.getColumn(),"price",_seat.getPrice()));
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                responseBody.put("error", "The ticket has been already purchased!");
                return new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);
            }

     }

    @GetMapping("/seats")
    public ResponseEntity<Room> findByAvailability() {
        try {
            List<SeatDto> availableSeats = new ArrayList<>();
            LOGGER.info("VEIO CÁ");
            seatRepository.findAllByIsAvailable(true).forEach((seat) -> {
                availableSeats.add(new SeatDto(seat.getRow(), seat.getColumn(), seat.getPrice()));
            });
            LOGGER.info("availableSeats: " + availableSeats.toString());
            if (availableSeats.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Room room = new Room(availableSeats);
            LOGGER.info(room.toString());
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}