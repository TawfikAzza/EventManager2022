package be;

public class TicketType {
    private int id;
    private String type; // This holds the name of the ticket: Regular, VIP, vegetarian,...
    private String benefit; // This describes the benefit of this type: Free drinks, reserved seating, members-only ticket, multi-day pass

    public TicketType(int id, String type, String benefit) {
        this.id = id;
        this.type = type;
        this.benefit = benefit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    @Override
    public String toString() {
        return String.format("%s",type);
    }
}
