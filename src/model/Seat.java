package model;

public class Seat {
    private int id;
    private int movieId;
    private String seatNo;
    private boolean booked;

    public Seat(int id, int movieId, String seatNo, boolean booked) {
        this.id = id;
        this.movieId = movieId;
        this.seatNo = seatNo;
        this.booked = booked;
    }

    public int getId() { return id; }
    public String getSeatNo() { return seatNo; }
    public boolean isBooked() { return booked; }
}
