package service;

import db.DBConnection;
import model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    // returns booking id, or -1 on failure
    public int bookSeat(int userId, int movieId, int seatId, double amount) {
        String updateSeat = "UPDATE seats SET is_booked = TRUE WHERE id = ? AND is_booked = FALSE";
        String insertBooking = "INSERT INTO bookings (user_id, movie_id, seat_id, total_amount) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psSeat = con.prepareStatement(updateSeat)) {
                psSeat.setInt(1, seatId);
                int rows = psSeat.executeUpdate();
                if (rows == 0) {
                    con.rollback();
                    System.out.println("Seat already booked.");
                    return -1;
                }
            }
            int bookingId;
            try (PreparedStatement psBook = con.prepareStatement(insertBooking, Statement.RETURN_GENERATED_KEYS)) {
                psBook.setInt(1, userId);
                psBook.setInt(2, movieId);
                psBook.setInt(3, seatId);
                psBook.setDouble(4, amount);
                psBook.executeUpdate();
                ResultSet keys = psBook.getGeneratedKeys();
                keys.next();
                bookingId = keys.getInt(1);
            }
            con.commit();
            return bookingId;
        } catch (SQLException e) {
            System.out.println("Booking failed: " + e.getMessage());
            return -1;
        }
    }

    public void generateTicket(int bookingId) {
        String sql = "SELECT b.id, u.name, m.title, s.seat_no, m.show_time, b.total_amount, b.booking_time " +
                "FROM bookings b " +
                "JOIN users u ON b.user_id = u.id " +
                "JOIN movies m ON b.movie_id = m.id " +
                "JOIN seats s ON b.seat_id = s.id " +
                "WHERE b.id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("\n===== TICKET =====");
                System.out.println("Booking ID : " + rs.getInt("id"));
                System.out.println("Name       : " + rs.getString("name"));
                System.out.println("Movie      : " + rs.getString("title"));
                System.out.println("Seat No    : " + rs.getString("seat_no"));
                System.out.println("Show Time  : " + rs.getString("show_time"));
                System.out.println("Amount     : Rs." + rs.getDouble("total_amount"));
                System.out.println("Booked On  : " + rs.getTimestamp("booking_time"));
                System.out.println("==================\n");
            }
        } catch (SQLException e) {
            System.out.println("Ticket generation failed: " + e.getMessage());
        }
    }

    public List<Booking> getBookingHistory(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.id, m.title, s.seat_no, b.booking_time, b.total_amount " +
                "FROM bookings b " +
                "JOIN movies m ON b.movie_id = m.id " +
                "JOIN seats s ON b.seat_id = s.id " +
                "WHERE b.user_id = ? ORDER BY b.booking_time DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Booking(rs.getInt("id"), rs.getString("title"), rs.getString("seat_no"),
                        rs.getTimestamp("booking_time"), rs.getDouble("total_amount")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch history failed: " + e.getMessage());
        }
        return list;
    }
}
