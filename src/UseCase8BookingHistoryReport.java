import java.util.*;

/**
 * MAIN CLASS UseCase8BookingHistoryReport
 *
 * Use Case 8: Booking History & Reporting
 *
 * @version 8.0
 */
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Create confirmed reservations
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add to booking history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}

/**
 * CLASS BookingHistory
 *
 * Maintains record of confirmed reservations
 *
 * @version 8.0
 */
class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * CLASS BookingReportService
 *
 * Generates reports from booking history
 *
 * @version 8.0
 */
class BookingReportService {

    public void generateReport(BookingHistory history) {

        System.out.println("\nBooking History Report");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println(
                "Guest: " + r.getGuestName() +
                ", Room Type: " + r.getRoomType()
            );
        }
    }
}

/**
 * CLASS Reservation
 *
 * Represents confirmed reservation
 *
 * @version 8.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}