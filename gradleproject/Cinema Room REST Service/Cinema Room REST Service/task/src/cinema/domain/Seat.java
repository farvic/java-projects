package cinema.domain;


import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private long id;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_column")
    private int column;
    @Column(name = "is_available")
    private boolean isAvailable;

    protected Seat() {
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Seat(int row, int column, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", row=" + row + ", column=" + column + ", isAvailable=" + isAvailable + "]";
    }

}