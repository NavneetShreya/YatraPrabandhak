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

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("tours/" + tour.getId() + ".txt"))) {
                    writer.write(tour.getName() + "\n");
                    writer.write(tour.getLocation() + "\n");
                    writer.write(tour.getPrice() + "\n");
                    writer.write(tour.getSeats() + "\n");
                } catch (IOException e) {
                    System.out.println("Error updating tour data to file.");
                }
            } else {
                System.out.println("No seats available for this tour.");
            }
        }
    }
}
