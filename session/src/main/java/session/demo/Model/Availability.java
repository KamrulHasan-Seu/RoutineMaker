package session.demo.Model;

public enum Availability {
    UPCOMING("Upcoming"), COMPLETED("Completed"),WORKING("Working");

    private final String name;

    private Availability(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
