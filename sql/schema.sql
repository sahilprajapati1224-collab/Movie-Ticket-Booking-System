CREATE DATABASE IF NOT EXISTS movie_ticket_db;
USE movie_ticket_db;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE movies (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  genre VARCHAR(50),
  language VARCHAR(50),
  show_time VARCHAR(50),
  price DECIMAL(8,2) NOT NULL,
  total_seats INT NOT NULL DEFAULT 10
);

CREATE TABLE seats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  movie_id INT NOT NULL,
  seat_no VARCHAR(10) NOT NULL,
  is_booked BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  UNIQUE KEY uniq_seat (movie_id, seat_no)
);

CREATE TABLE bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  movie_id INT NOT NULL,
  seat_id INT NOT NULL,
  booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(8,2),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  FOREIGN KEY (seat_id) REFERENCES seats(id)
);

-- sample movies
INSERT INTO movies (title, genre, language, show_time, price, total_seats) VALUES
('Pathaan', 'Action', 'Hindi', '10:00 AM', 200.00, 10),
('Jawan', 'Action', 'Hindi', '01:00 PM', 220.00, 10),
('Animal', 'Drama', 'Hindi', '06:00 PM', 250.00, 10);

-- auto seats A1-A10 per movie
INSERT INTO seats (movie_id, seat_no)

SELECT m.id, CONCAT('A', n.n)
FROM movies m
JOIN (SELECT 1 n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) n;
