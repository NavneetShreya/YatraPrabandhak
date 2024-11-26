public class Tour {
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
