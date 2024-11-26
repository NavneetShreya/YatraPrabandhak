# Yatra Prabadhak: Tourism Management System

**Yatra Prabadhak** is a comprehensive tourism management system designed to streamline the process of tour management, bookings, and data storage. The system provides role-based access for admins and regular users, allowing for the efficient addition, viewing, and booking of tours. The project is implemented in **Java** and uses a file-based storage system to store tour data and booking information.

## Features

- **Admin Functionality**:
  - **Add Tours**: Admins can create new tour packages by specifying details such as name, location, price, and available seats.
  - **View Tours**: Admins can view a list of all available tours, including tour details like location, price, and seat availability.
  - **Book Tours**: Admins can book tours on behalf of users or themselves.

- **User Functionality**:
  - **View Tours**: Users can browse through available tours, checking their details such as price, location, and seats available.
  - **Book Tours**: Users can book tours by selecting the tour ID. Seat availability is updated in real time after each booking.

- **File-Based Storage**:
  - Tour details are stored persistently in text files in the `tours` directory.
  - Every tour has its own file, which contains its name, location, price, and seat availability.

- **Role-Based Access**:
  - Admins can manage and update tours, while users can only view and book tours.

## Technologies Used

- **Programming Language**: Java (JDK 17)
- **IDE**: VS Code (or any other Java-compatible IDE)
- **File-Based Persistence**: Text files used for storing tour data and bookings
- **No Database**: Data is managed using text files within the `tours` directory

## Setup Instructions

### Prerequisites:
- **Java**: Ensure that you have **Java JDK 17** or higher installed on your system.

### Steps to Run:
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/Yatra-Prabadhak.git
