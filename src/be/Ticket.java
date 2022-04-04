package be;

public class Ticket {
    private int id;
    private String ticketNumber;
    private int ticketTypeID;
    private boolean valid;
    public Ticket(int id, String ticketNumber, int ticketTypeID) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.ticketTypeID = ticketTypeID;
    }

    public Ticket(int id, String ticketNumber, int ticketTypeID, boolean valid) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.ticketTypeID = ticketTypeID;
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }
}
