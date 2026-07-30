# Movie Ticket Booking System (Java + MySQL)

## Structure
```
MovieTicketBookingSystem/
├── sql/schema.sql          -> DB + tables + sample movies
├── src/
│   ├── Main.java           -> console menu (entry point)
│   ├── db/DBConnection.java
│   ├── model/               (User, Movie, Seat, Booking)
│   └── service/              (AuthService, MovieService, BookingService)
```

## Setup
1. MySQL me schema run karo:
   ```
   mysql -u root -p < sql/schema.sql
   ```
2. `src/db/DBConnection.java` me apna MySQL password daalo (line: `PASSWORD`).
3. MySQL Connector/J jar download karo: https://dev.mysql.com/downloads/connector/j/
4. Compile + run (jar path apne hisaab se change karo):
   ```
   cd src
   javac -cp .:mysql-connector-j-9.x.x.jar -d ../out db/*.java model/*.java service/*.java Main.java
   java -cp ../out:mysql-connector-j-9.x.x.jar Main
   ```
   Windows pe `:` ki jagah `;` use karo classpath me.

## Flow
1. Register -> Login
2. Movie List dekho
3. Book Seat -> movie ID + seat no (A1-A10) choose karo
4. Ticket auto print hota hai booking ke baad
5. Booking History se apni saari bookings dekho

## Notes
- Seat booking transaction-safe hai (double-booking nahi hoga, `setAutoCommit(false)` + rollback use kiya).
- 3 sample movies + 10-10 seats already schema.sql me insert ho rahe hain.
- Password plain-text store ho raha hai abhi (college project ke liye theek hai); production me BCrypt use karna.
