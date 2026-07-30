package model;

import java.sql.Timestamp;

public class Booking {
    private int id;
    private String movieTitle;
    private String seatNo;
    private Timestamp bookingTime;
    private double amount;

    public Booking(int id, String movieTitle, String seatNo, Timestamp bookingTime, double amount) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.seatNo = seatNo;
        this.bookingTime = bookingTime;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Booking#" + id + " | " + movieTitle + " | Seat " + seatNo + " | " + bookingTime + " | Rs." + amount;
    }
}
