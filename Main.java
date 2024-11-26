import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        TourismManagementSystem system = new TourismManagementSystem();

        // Admin login authentication
        boolean isAdmin = authenticateAdmin(scanner);

        do {
            System.out.println("\n--- Tourism Management System ---");

            // Menu options based on user role
            if (isAdmin) {
                System.out.println("1. Add Tour");
                System.out.println("2. View Tours");
                System.out.println("3. Book Tour");
                System.out.println("4. Exit");
            } else {
                System.out.println("1. View Tours");
                System.out.println("2. Book Tour");
                System.out.println("3. Exit");
            }

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            // Admin or User-specific functionality
            if (isAdmin) {
                switch (choice) {
                    case 1:
                        // Add tour option for Admin
                        System.out.print("Enter tour name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter available seats: ");
                        int seats = scanner.nextInt();
                        system.addTour(name, location, price, seats);
                        break;

                    case 2:
                        // View tours for Admin and User
                        system.viewTours();
                        break;

                    case 3:
                        // Book tour option for Admin
                        System.out.print("Enter tour ID to book: ");
                        int tourId = scanner.nextInt();
                        system.bookTour(tourId);
                        break;

                    case 4:
                        System.out.println("Exiting system...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                switch (choice) {
                    case 1:
                        // View tours for User
                        system.viewTours();
                        break;

                    case 2:
                        // Book tour option for User
                        System.out.print("Enter tour ID to book: ");
                        int tourId = scanner.nextInt();
                        system.bookTour(tourId);
                        break;

                    case 3:
                        System.out.println("Exiting system...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 4);

        scanner.close();
    }

    // Admin authentication method
    public static boolean authenticateAdmin(Scanner scanner) {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        // List of admin usernames
        String[] adminUsernames = {"Aakash", "Navneet", "Harsh", "Sandeep"};
        String adminPassword = "Admin@123"; // Common password for all admins

        // Check if username is in the list of valid admins and password matches
        for (String adminUsername : adminUsernames) {
            if (username.equals(adminUsername) && password.equals(adminPassword)) {
                System.out.println("Admin authenticated successfully!");
                return true; // Admin authenticated
            }
        }

        System.out.println("User authenticated as regular user.");
        return false; // Regular user
    }
}
