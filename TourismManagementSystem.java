import java.io.*;
import java.util.*;

public class TourismManagementSystem {
    private List<Tour> tours = new ArrayList<>();
    private int nextTourId = 1;

    public TourismManagementSystem() {
        this.loadToursFromFiles();
    }

    private void loadToursFromFiles() {
        File toursDir = new File("tours");
        if (!toursDir.exists()) {
            toursDir.mkdirs();
        }

        File[] files = toursDir.listFiles();
        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String name = reader.readLine();
                    String location = reader.readLine();
                    double price = Double.parseDouble(reader.readLine());
                    int seats = Integer.parseInt(reader.readLine());
                    int tourId = Integer.parseInt(file.getName().replace(".txt", ""));
                    this.tours.add(new Tour(tourId, name, location, price, seats));
                    this.nextTourId = Math.max(this.nextTourId, tourId + 1);
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Error loading tour data from file: " + file.getName());
                }
            }
        }
    }

    public void addTour(String name, String location, double price, int seats) {
        Tour tour = new Tour(this.nextTourId, name, location, price, seats);
        this.tours.add(tour);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/" + tour.getId() + ".txt"))) {
            writer.write(tour.getName() + "\n");
            writer.write(tour.getLocation() + "\n");
            writer.write(tour.getPrice() + "\n");
            writer.write(tour.getSeats() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving tour data to file.");
        }

        System.out.println("Tour added successfully!");
        this.nextTourId++;
    }

    public void viewTours() {
        if (this.tours.isEmpty()) {
            System.out.println("No tours available.");
        } else {
            for (Tour tour : this.tours) {
                System.out.println("Tour ID: " + tour.getId() + ", Name: " + tour.getName() + ", Location: " + tour.getLocation() +
                        ", Price: $" + tour.getPrice() + ", Seats Available: " + tour.getSeats());
            }
        }
    }

    public void bookTour(int tourId) {
        Tour tour = null;
        for (Tour t : this.tours) {
            if (t.getId() == tourId) {
                tour = t;
                break;
            }
        }

        if (tour == null) {
            System.out.println("Tour not found.");
        } else {
            if (tour.getSeats() > 0) {
                tour.setSeats(tour.getSeats() - 1);
                System.out.println("Tour booked successfully! Remaining seats: " + tour.getSeats());
            } else {
                System.out.println("Sorry, no available seats for this tour.");
            }
        }
    }

    public void cancelTourBooking(int tourId) {
        Tour tour = null;
        for (Tour t : this.tours) {
            if (t.getId() == tourId) {
                tour = t;
                break;
            }
        }

        if (tour == null) {
            System.out.println("Tour not found.");
        } else {
            tour.setSeats(tour.getSeats() + 1);
            System.out.println("Booking canceled successfully! Available seats: " + tour.getSeats());

            // Save updated seat count to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/" + tour.getId() + ".txt"))) {
                writer.write(tour.getName() + "\n");
                writer.write(tour.getLocation() + "\n");
                writer.write(tour.getPrice() + "\n");
                writer.write(tour.getSeats() + "\n");
            } catch (IOException e) {
                System.out.println("Error updating tour data to file.");
            }
        }
    }

    public void searchTours(String searchTerm) {
        boolean found = false;
        for (Tour tour : this.tours) {
            if (tour.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    tour.getLocation().toLowerCase().contains(searchTerm.toLowerCase())) {
                System.out.println("Tour ID: " + tour.getId() + ", Name: " + tour.getName() + ", Location: " + tour.getLocation() +
                        ", Price: $" + tour.getPrice() + ", Seats Available: " + tour.getSeats());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tours found for the given search term.");
        }
    }

    public void viewTourDetails(int tourId) {
        Tour tour = null;
        for (Tour t : this.tours) {
            if (t.getId() == tourId) {
                tour = t;
                break;
            }
        }

        if (tour == null) {
            System.out.println("Tour not found.");
        } else {
            System.out.println("Tour ID: " + tour.getId());
            System.out.println("Name: " + tour.getName());
            System.out.println("Location: " + tour.getLocation());
            System.out.println("Price: $" + tour.getPrice());
            System.out.println("Seats Available: " + tour.getSeats());
        }
    }

    public void sortToursByPrice() {
        tours.sort(Comparator.comparingDouble(Tour::getPrice));
        System.out.println("Tours sorted by price:");
        viewTours();
    }

    public void sortToursBySeats() {
        tours.sort(Comparator.comparingInt(Tour::getSeats));
        System.out.println("Tours sorted by available seats:");
        viewTours();
    }

    public void exportToursToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/tours.csv"))) {
            writer.write("Tour ID, Name, Location, Price, Available Seats\n");
            for (Tour tour : tours) {
                writer.write(tour.getId() + ", " + tour.getName() + ", " + tour.getLocation() + ", " +
                        tour.getPrice() + ", " + tour.getSeats() + "\n");
            }
            System.out.println("Tours exported to tours.csv successfully!");
        } catch (IOException e) {
            System.out.println("Error exporting tours to CSV.");
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        TourismManagementSystem system = new TourismManagementSystem();

        // Sample tours
        system.addTour("Beach Paradise", "Hawaii", 500, 10);
        system.addTour("Mountain Adventure", "Nepal", 700, 5);
        system.viewTours();

        // Example operations
        system.bookTour(1);
        system.cancelTourBooking(1);
        system.searchTours("Beach");
        system.viewTourDetails(2);
        system.sortToursByPrice();
        system.exportToursToCSV();
    }
}
