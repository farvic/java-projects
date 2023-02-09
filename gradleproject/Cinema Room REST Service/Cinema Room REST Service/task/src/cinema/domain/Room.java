package cinema.domain;

import cinema.dto.SeatDto;

import java.util.List;

public class Room {

    private final int totalRows;

    private final int totalColumns;

    private final List<SeatDto> availableSeats;

    public Room(List<SeatDto> availableSeats) {
        this.totalRows = 9;
        this.totalColumns = 9;
        this.availableSeats = availableSeats;
    }

    protected Room() {
        this(null);
    }



    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<SeatDto> getAvailableSeats() {
        return availableSeats;

    }

    public List<SeatDto> setAvailableSeats() {
        return availableSeats;
    }
}
