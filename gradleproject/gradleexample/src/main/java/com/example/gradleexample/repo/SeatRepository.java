package com.example.gradleexample.repo;


import com.example.gradleexample.domain.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {

    List<Seat> findAllByIsAvailable(boolean isAvailable);

}
