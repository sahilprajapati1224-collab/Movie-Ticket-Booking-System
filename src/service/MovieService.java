package service;

import db.DBConnection;
import model.Movie;
import model.Seat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getString("genre"),
                        rs.getString("language"), rs.getString("show_time"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch movies failed: " + e.getMessage());
        }
        return movies;
    }

    public List<Seat> getAvailableSeats(int movieId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE movie_id = ? AND is_booked = FALSE";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(new Seat(rs.getInt("id"), rs.getInt("movie_id"), rs.getString("seat_no"), rs.getBoolean("is_booked")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch seats failed: " + e.getMessage());
        }
        return seats;
    }

    public Movie getMovieById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Movie(rs.getInt("id"), rs.getString("title"), rs.getString("genre"),
                        rs.getString("language"), rs.getString("show_time"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("Fetch movie failed: " + e.getMessage());
        }
        return null;
    }
}
