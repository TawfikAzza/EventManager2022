package be;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Events {
    private int eventId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String nameEvent;
    private String eventLocation;
    List<Participant> listParticipants;
    private String eventDescription;
    private String eventItinerary;

    public Events(int eventId,String nameEvent, String eventLocation, String eventDescription, LocalDateTime startDate, LocalDateTime endDate, String eventItinerary) {
        this.eventId=eventId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nameEvent = nameEvent;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventItinerary = eventItinerary;
    }

    public Events(String nameEvent, String eventLocation) {
        this.nameEvent = nameEvent;
        this.eventLocation = eventLocation;
    }
    public Events(int eventID, String nameEvent, String eventLocation) {
        this.eventId=eventID;
        this.nameEvent = nameEvent;
        this.eventLocation = eventLocation;
    }

    public int getId() {
        return eventId;
    }

    public void setId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return nameEvent;
    }

    public void setName(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getLocation() {
        return eventLocation;
    }

    public void setLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public List<Participant> getListParticipants() {
        return listParticipants;
    }

    public void setListParticipants(List<Participant> listParticipants) {
        this.listParticipants = listParticipants;
    }

    public String getDescription() {
        return eventDescription;
    }

    public void setDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getItinerary() {
        return eventItinerary;
    }

    public void setItinerary(String eventItinerary) {
        this.eventItinerary = eventItinerary;
    }
}
