-- CREATE TABLE rooms (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     total_rows INT NOT NULL,
--     total_columns INT NOT NULL
-- );
CREATE TABLE seats (
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_row INT NOT NULL,
    seat_column INT NOT NULL,
    price INT NOT NULL,
    is_available BOOLEAN NOT NULL -- room_id INT NOT NULL -- FOREIGN KEY (room_id) REFERENCES Room(id)
);