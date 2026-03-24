import java.util.*;

/**
 * MAIN CLASS UseCase9ErrorHandlingValidation
 *
 * Use Case 9: Error Handling & Validation
 *
 * @version 9.0
 */
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Take user input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate input
            validator.validate(guestName, roomType, inventory);

            // If valid → add to queue
            bookingQueue.addRequest(new Reservation(guestName, roomType));

            System.out.println("Booking request added successfully.");

        } catch (InvalidBookingException e) {
            // Handle validation errors
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

/**
 * CLASS InvalidBookingException
 *
 * Custom exception for invalid booking
 *
 * @version 9.0
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * CLASS ReservationValidator
 *
 * Validates booking input
 *
 * @version 9.0
 */
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type (case sensitive)
        if (!roomType.equals("Single") &&
            !roomType.equals("Double") &&
            !roomType.equals("Suite")) {

            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Validate availability
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

/**
 * CLASS RoomInventory
 *
 * Stores room availability
 *
 * @version 9.0
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/**
 * CLASS Reservation
 *
 * Represents booking request
 *
 * @version 9.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * CLASS BookingRequestQueue
 *
 * FIFO queue for requests
 *
 * @version 9.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }
}