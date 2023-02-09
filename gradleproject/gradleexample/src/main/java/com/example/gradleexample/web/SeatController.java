package com.example.gradleexample.web;

import com.example.gradleexample.domain.Room;
import com.example.gradleexample.domain.Seat;
import com.example.gradleexample.dto.SeatDto;
import com.example.gradleexample.repo.SeatRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;



// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/seats")
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

    @GetMapping()
    public ResponseEntity<Room> findByAvailability() {
        try {
            List<SeatDto> seats = new ArrayList<>();
            LOGGER.info("VEIO CÁ");
            seatRepository.findAllByIsAvailable(true).forEach((seat) -> {
                seats.add(new SeatDto(seat.getRow(), seat.getColumn()));
            });
            LOGGER.info("Seats: " + seats.toString());
            if (seats.isEmpty()) {
                LOGGER.error("IH, RAPAZ");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Room room = new Room(seats);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}