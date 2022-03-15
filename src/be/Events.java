package be;

import bll.utils.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Events {
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private String location;
    List<Participant> listParticipants;
    private String description;
    private String itinerary;
    private String strStartDate;
    private String strEndDate;
    public Events(int eventId,String nameEvent, String eventLocation, String eventDescription, LocalDateTime startDate, LocalDateTime endDate, String eventItinerary) {
        this.id=eventId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = nameEvent;
        this.location = eventLocation;
        this.description = eventDescription;
        this.itinerary = eventItinerary;
    }

    public Events(String nameEvent, String eventLocation) {
        this.name = nameEvent;
        this.location = eventLocation;
    }
    public Events(int eventID, String nameEvent, String eventLocation) {
        this.id=eventID;
        this.name = nameEvent;
        this.location = eventLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int eventId) {
        this.id = eventId;
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
        return name;
    }

    public void setName(String nameEvent) {
        this.name = nameEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String eventLocation) {
        this.location = eventLocation;
    }

    public List<Participant> getListParticipants() {
        return listParticipants;
    }

    public void setListParticipants(List<Participant> listParticipants) {
        this.listParticipants = listParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String eventDescription) {
        this.description = eventDescription;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String eventItinerary) {
        this.itinerary = eventItinerary;
    }

    public String getStrStartDate() {
        this.strStartDate = DateUtil.formatDateGui(startDate);
        return strStartDate;
    }

    public void setStrStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }

    public String getStrEndDate() {
        if(endDate!=null) {
            strEndDate=DateUtil.formatDateGui(endDate);
        }
        return strEndDate;
    }

    public void setStrEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }
}
