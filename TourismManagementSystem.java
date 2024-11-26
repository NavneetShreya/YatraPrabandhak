import java.io.*;
import java.util.*;

public class TourismManagementSystem {
    private List<Tour> tours;
    private int nextTourId;

    public TourismManagementSystem() {
        tours = new ArrayList<>();
        nextTourId = 1;
        loadToursFromFiles();
    }

    // Method to load existing tours from files
    private void loadToursFromFiles() {
        File folder = new File("tours");
        if (!folder.exists()) {
            folder.mkdirs(); // Create a directory for tours if not exists
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String name = reader.readLine();
                    String location = reader.readLine();
                    double price = Double.parseDouble(reader.readLine());
                    int seats = Integer.parseInt(reader.readLine());
                    int id = Integer.parseInt(file.getName().replace(".txt", ""));
                    tours.add(new Tour(id, name, location, price, seats));
                    nextTourId = Math.max(nextTourId, id + 1);
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Error loading tour data from file: " + file.getName());
                }
            }
        }
    }

    // Method to add a new tour
    public void addTour(String name, String location, double price, int seats) {
        Tour newTour = new Tour(nextTourId, name, location, price, seats);
        tours.add(newTour);

        // Create a new file for the tour data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/" + newTour.getId() + ".txt"))) {
            writer.write(newTour.getName() + "\n");
            writer.write(newTour.getLocation() + "\n");
            writer.write(newTour.getPrice() + "\n");
            writer.write(newTour.getSeats() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving tour data to file.");
        }

        System.out.println("Tour added successfully!");
        nextTourId++;  // Increment the ID for the next tour
    }

    // Method to view all tours
    public void viewTours() {
        if (tours.isEmpty()) {
            System.out.println("No tours available.");
            return;
        }

        for (Tour tour : tours) {
            System.out.println("Tour ID: " + tour.getId() + ", Name: " + tour.getName() +
                               ", Location: " + tour.getLocation() +
                               ", Price: $" + tour.getPrice() +
                               ", Seats Available: " + tour.getSeats());
        }
    }

    // Method to book a tour
    public void bookTour(int tourId) {
        Tour tourToBook = null;
        for (Tour tour : tours) {
            if (tour.getId() == tourId) {
                tourToBook = tour;
                break;
            }
        }

        if (tourToBook == null) {
            System.out.println("Tour not found.");
            return;
        }

        if (tourToBook.getSeats() > 0) {
            tourToBook.setSeats(tourToBook.getSeats() - 1);
            System.out.println("Tour booked successfully! Remaining seats: " + tourToBook.getSeats());

            // Update the file with the new available seats
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/" + tourToBook.getId() + ".txt"))) {
                writer.write(tourToBook.getName() + "\n");
                writer.write(tourToBook.getLocation() + "\n");
                writer.write(tourToBook.getPrice() + "\n");
                writer.write(tourToBook.getSeats() + "\n");
            } catch (IOException e) {
                System.out.println("Error updating tour data to file.");
            }
        } else {
            System.out.println("No seats available for this tour.");
        }
    }
}

class Tour {
    private int id;
    private String name;
    private String location;
    private double price;
    private int seats;

    public Tour(int id, String name, String location, double price, int seats) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
