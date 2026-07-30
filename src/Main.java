import model.Movie;
import model.Seat;
import model.User;
import model.Booking;
import service.AuthService;
import service.MovieService;
import service.BookingService;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static AuthService authService = new AuthService();
    static MovieService movieService = new MovieService();
    static BookingService bookingService = new BookingService();
    static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=== MOVIE TICKET BOOKING SYSTEM ===");
        while (true) {
            if (currentUser == null) {
                authMenu();
            } else {
                mainMenu();
            }
        }
    }

    static void authMenu() {
        System.out.println("\n1. Login\n2. Register\n3. Exit");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine().trim());
        switch (ch) {
            case 1:
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                currentUser = authService.login(email, pass);
                if (currentUser == null) System.out.println("Invalid credentials.");
                else System.out.println("Welcome, " + currentUser.getName() + "!");
                break;
            case 2:
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Email: ");
                String regEmail = sc.nextLine();
                System.out.print("Password: ");
                String regPass = sc.nextLine();
                if (authService.register(name, regEmail, regPass)) System.out.println("Registered! Please login.");
                break;
            case 3:
                System.out.println("Bye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice.");
        }
    }

    static void mainMenu() {
        System.out.println("\n1. Movie List\n2. Book Seat\n3. Booking History\n4. Logout");
        System.out.print("Choice: ");
        int ch = Integer.parseInt(sc.nextLine().trim());
        switch (ch) {
            case 1:
                showMovies();
                break;
            case 2:
                bookSeatFlow();
                break;
            case 3:
                showHistory();
                break;
            case 4:
                currentUser = null;
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    static void showMovies() {
        List<Movie> movies = movieService.getAllMovies();
        System.out.println("\n--- Now Showing ---");
        for (Movie m : movies) System.out.println(m);
    }

    static void bookSeatFlow() {
        showMovies();
        System.out.print("Enter Movie ID: ");
        int movieId = Integer.parseInt(sc.nextLine().trim());
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("Invalid movie.");
            return;
        }
        List<Seat> seats = movieService.getAvailableSeats(movieId);
        if (seats.isEmpty()) {
            System.out.println("No seats available.");
            return;
        }
        System.out.println("Available seats:");
        for (Seat s : seats) System.out.print(s.getSeatNo() + " ");
        System.out.print("\nEnter seat number (e.g., A1): ");
        String seatNo = sc.nextLine().trim();
        Seat chosen = null;
        for (Seat s : seats) if (s.getSeatNo().equalsIgnoreCase(seatNo)) chosen = s;
        if (chosen == null) {
            System.out.println("Invalid seat.");
            return;
        }
        int bookingId = bookingService.bookSeat(currentUser.getId(), movieId, chosen.getId(), movie.getPrice());
        if (bookingId != -1) {
            System.out.println("Booking successful! Booking ID: " + bookingId);
            bookingService.generateTicket(bookingId);
        }
    }

    static void showHistory() {
        List<Booking> history = bookingService.getBookingHistory(currentUser.getId());
        System.out.println("\n--- Booking History ---");
        if (history.isEmpty()) System.out.println("No bookings yet.");
        for (Booking b : history) System.out.println(b);
    }
}
