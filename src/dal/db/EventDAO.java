package dal.db;

import be.Events;
import be.Ticket;
import dal.ConnectionManager;
import dal.interfaces.IEventDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO {
    ConnectionManager cm;

    public EventDAO() throws Exception {
        cm = new ConnectionManager();
    }

    @Override
    public Events addEvent(Events event) throws Exception{
        Events eventCreated=null;
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO EVENTS VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,event.getName());
            pstmt.setString(2,event.getLocation());
            pstmt.setString(3,event.getDescription());
            pstmt.setObject(4,event.getStartDate());
            if(event.getEndDate()==null) {
                pstmt.setNull(5, Types.NULL);
            } else {
                pstmt.setObject(5,event.getEndDate());
            }
            if(event.getItinerary()==null) {
                pstmt.setNull(6, Types.NULL);
            } else {
                pstmt.setString(6,event.getItinerary());
            }
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            while(rs.next())
            {
                eventCreated= new Events(
                        rs.getInt(1),
                        event.getName(),
                        event.getLocation()
                );
                eventCreated.setDescription(event.getDescription());
                eventCreated.setStartDate(event.getStartDate());
                if(event.getEndDate()!=null) {
                    eventCreated.setEndDate(event.getEndDate());
                }
                if(event.getItinerary()!=null) {
                    eventCreated.setItinerary(event.getItinerary());
                }
            }
        }

        return eventCreated;
    }


    @Override
    public void removeEvent(Events event) throws Exception {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM EVENTS WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,event.getId());
            pstmt.execute();
        }
    }


    @Override
    public void updateEvent(Events event) throws Exception{
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE EVENTS SET name=?, location=?, description=?, startDate=?,endDate=?,itinerary=?"
                        +" WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,event.getName());
            pstmt.setString(2,event.getLocation());
            pstmt.setString(3,event.getDescription());
            pstmt.setObject(4,event.getStartDate());
            if(event.getEndDate()==null) {
                pstmt.setNull(5, Types.NULL);
            } else {
                pstmt.setObject(5,event.getEndDate());
            }
            if(event.getItinerary()==null) {
                pstmt.setNull(6, Types.NULL);
            } else {
                pstmt.setString(6,event.getItinerary());
            }
            pstmt.setInt(7,event.getId());
            pstmt.execute();
        }
    }

    /**
     * This one is a method which took several years of my life...
     * Do not modify it without paying close attention at the way the method retrieve results from the query
     * Also, if you ask yourself why I used a temporary List of ticket instead of using the one I built....
     * Well, let's just say that Jeppe will have a lot of questions from me if I remember to ask him...
     * */
    @Override
    public List<Events> getAllEvents() throws Exception {
        List<Events> allEvents = new ArrayList<>();
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT EVENTS.id,events.name,events.location,events.description,events.startDate,events.endDate," +
                        "events.itinerary, TicketType.id as ticketID, TicketType.typeName " +
                        " as nameTicket, TicketType.benefit as ticketBenefit " +
                        " FROM EVENTS INNER JOIN TicketType ON EVENTS.id = TicketType.eventID" +
                    " order by id";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int idEvent = 0;
            int currentEvent = -1;
            boolean flagFirst=false;
            String name=null;
            String location = null;
            String description=null;
            LocalDateTime startDate=null;
            LocalDateTime endDate=null;
            String itinerary=null;

            while(rs.next())
            {
                idEvent= rs.getInt("id");
                if(!flagFirst) {
                    currentEvent=idEvent;
                    flagFirst=true;
                }
                if(idEvent!=currentEvent) {
                    Events event= new Events(
                            currentEvent,
                            name,
                            location
                    );
                    event.setDescription(location);
                    event.setStartDate(startDate);
                    if(endDate!=null) {
                        event.setEndDate(endDate);
                    }
                    if(itinerary!=null) {
                        event.setItinerary(itinerary);
                    }
                    List<Ticket> tickets = new ArrayList<>(ticketList);
                    event.setTicketAvailable(tickets);

                    allEvents.add(event);
                    ticketList.clear();

                }

                ticketList.add(new Ticket(rs.getInt("ticketID")
                                        ,rs.getString("nameTicket")
                                        ,rs.getString("ticketBenefit")));

                name=rs.getString("name");
                location = rs.getString("location");
                description=rs.getString("description");
                startDate=rs.getObject("startDate", LocalDateTime.class);
                if(rs.getObject("endDate",LocalDateTime.class)!=null) {
                   endDate=rs.getObject("endDate",LocalDateTime.class);
                }
                if(rs.getString("itinerary")!=null) {
                    itinerary=rs.getString("itinerary");
                }

                currentEvent=idEvent;
            }

            Events event = new Events(currentEvent,name,location);
            event.setStartDate(startDate);
            event.setDescription(description);
            if(endDate!=null) {
                event.setEndDate(endDate);
            }
            if(itinerary!=null) {
                event.setItinerary(itinerary);
            }
            List<Ticket> tickets = new ArrayList<>(ticketList);
            event.setTicketAvailable(tickets);
            allEvents.add(event);
        }
        return allEvents;
    }


    @Override
    public Events getEvent(int id) throws Exception {
         Events eventSearched = null;
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM EVENTS WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                eventSearched = new Events(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location")
                );
                eventSearched.setDescription(rs.getString("description"));
                eventSearched.setStartDate(rs.getObject("startDate", LocalDateTime.class));
                if(rs.getObject("endDate",LocalDateTime.class)!=null) {
                    eventSearched.setEndDate(rs.getObject("endDate",LocalDateTime.class));
                }
                if(rs.getString("itinerary")!=null) {
                    eventSearched.setItinerary(rs.getString("itinerary"));
                }

            }
        }

        return eventSearched;
    }
}
