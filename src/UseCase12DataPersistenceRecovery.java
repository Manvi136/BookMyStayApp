import java.io.*;
import java.util.*;

/**
 * MAIN CLASS UseCase12DataPersistenceRecovery
 *
 * @version 12.0
 */
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        String filePath = "inventory.txt";

        // Load inventory (on startup)
        persistence.loadInventory(inventory, filePath);

        // Display current inventory
        System.out.println("\nCurrent Inventory:");
        Map<String, Integer> avail = inventory.getRoomAvailability();
        for (String key : avail.keySet()) {
            System.out.println(key + ": " + avail.get(key));
        }

        // Save inventory (before shutdown)
        persistence.saveInventory(inventory, filePath);
    }
}

/**
 * CLASS FilePersistenceService
 *
 * @version 12.0
 */
class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            Map<String, Integer> availability = inventory.getRoomAvailability();

            for (String roomType : availability.keySet()) {
                writer.write(roomType + "-" + availability.get(roomType));
                writer.newLine();
            }

            System.out.println("\nInventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            Map<String, Integer> map = inventory.getRoomAvailability();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("-");

                if (parts.length == 2) {
                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    map.put(roomType, count);
                }
            }

            System.out.println("Inventory loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

/**
 * CLASS RoomInventory
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}