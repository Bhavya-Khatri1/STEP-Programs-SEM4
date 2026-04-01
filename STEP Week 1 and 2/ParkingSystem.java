import java.util.*;
class ParkingSpot {
    String plate;
    long entryTime;
    boolean occupied;
    public ParkingSpot(String plate) {
        this.plate = plate;
        this.entryTime = System.currentTimeMillis();
        this.occupied = true;
    }
}
public class ParkingSystem {
    private ParkingSpot[] table = new ParkingSpot[500];
    private int size = 500;
    private int totalProbes = 0;
    private int totalParks = 0;
    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }
    public String parkVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;
        while (table[index] != null && table[index].occupied) {
            index = (index + 1) % size;
            probes++;
        }
        table[index] = new ParkingSpot(plate);
        totalProbes += probes;
        totalParks++;
        return "Assigned spot #" + index + " (" + probes + " probes)";
    }
    public String exitVehicle(String plate) {
        int index = hash(plate);
        while (table[index] != null) {
            if (table[index].occupied && table[index].plate.equals(plate)) {
                long duration = System.currentTimeMillis() - table[index].entryTime;
                table[index].occupied = false;
                double hours = duration / (1000.0 * 60 * 60);
                double fee = hours * 5;
                return "Spot #" + index + " freed, Fee: $" + String.format("%.2f", fee);
            }
            index = (index + 1) % size;
        }
        return "Vehicle not found";
    }
    public String getStatistics() {
        int occupied = 0;
        for (ParkingSpot spot : table) {
            if (spot != null && spot.occupied) {
                occupied++;
            }
        }
        double occupancyRate = (occupied * 100.0) / size;
        double avgProbes = totalParks == 0 ? 0 : (totalProbes * 1.0) / totalParks;
        return "Occupancy: " + occupancyRate + "%, Avg Probes: " + avgProbes;
    }
}