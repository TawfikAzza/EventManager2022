package dal.db;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import dal.interfaces.IEventDAO;
import org.apache.commons.collections4.list.PredicatedList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<TicketType> ticketList = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT EVENTS.id,events.name,events.location,events.description,events.startDate,events.endDate," +
                        "events.itinerary FROM EVENTS " +
                        " WHERE EVENTS.startDate>=GetDate() ";

            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();


            while(rs.next()) {
                Events event = new Events(rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("location"),
                                        rs.getString("description"),
                                        rs.getObject("startDate",LocalDateTime.class),
                                        rs.getObject("endDate",LocalDateTime.class),
                                        rs.getString("itinerary"));
                allEvents.add(event);
                }
            }
        HashMap<Integer,Integer> mapEventParticpantNumber = new HashMap<>();
        HashMap<Integer,List<Participant>> mapParticipantByEvents = getParticipantByEvent();
        mapEventParticpantNumber = getParticpantNumberByEvent();
        for (Events event:allEvents)
            if(mapEventParticpantNumber.get(event.getId())!=null) {
                event.setNumberParticipants(""+mapEventParticpantNumber.get(event.getId()));
                event.setListParticipants(mapParticipantByEvents.get(event.getId()));
            }

        return allEvents;
    }

    /**
     * This one is a method which took several years of my life...
     * Do not modify it without paying close attention at the way the method retrieve results from the query
     * Also, if you ask yourself why I used a temporary List of ticket instead of using the one I built....
     * Well, let's just say that Jeppe will have a lot of questions from me if I remember to ask him...
     * */

    public List<Events> getAllEventsWithTicketType() throws Exception {
        List<Events> allEvents = new ArrayList<>();
        List<TicketType> ticketList = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT EVENTS.id,events.name,events.location,events.description,events.startDate,events.endDate," +
                    "events.itinerary, TicketType.id as ticketID, TicketType.typeName " +
                    " as nameTicket, TicketType.benefit as ticketBenefit " +
                    " FROM EVENTS LEFT JOIN TicketType ON EVENTS.id = TicketType.eventID " +
                    " WHERE events.startDate >= getDate() " +
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
                    event.setDescription(description);
                    event.setStartDate(startDate);
                    if(endDate!=null) {
                        event.setEndDate(endDate);
                    }
                    if(itinerary!=null) {
                        event.setItinerary(itinerary);
                    }
                    List<TicketType> tickets = new ArrayList<>(ticketList);
                    event.setTicketAvailable(tickets);

                    allEvents.add(event);
                    ticketList.clear();

                }

                ticketList.add(new TicketType(rs.getInt("ticketID")
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
            List<TicketType> tickets = new ArrayList<>(ticketList);
            event.setTicketAvailable(tickets);
            allEvents.add(event);
        }
        HashMap<Integer,Integer> mapEventParticpantNumber = new HashMap<>();
        HashMap<Integer,List<Participant>> mapParticipantByEvents = getParticipantByEvent();
        mapEventParticpantNumber = getParticpantNumberByEvent();
        for (Events event:allEvents)
            if(mapEventParticpantNumber.get(event.getId())!=null) {
                event.setNumberParticipants(""+mapEventParticpantNumber.get(event.getId()));
                event.setListParticipants(mapParticipantByEvents.get(event.getId()));
            }

        return allEvents;
    }

    @Override
    public void addParticipantToEvent(Events eventChosen, Participant participant, Ticket ticketSold) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sqlInsert = "INSERT INTO EventParticipant VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            pstmt.setInt(1,eventChosen.getId());
            pstmt.setInt(2,participant.getId());
            pstmt.setInt(3,ticketSold.getId());
            System.out.println("In AddParticipantToEvent");
            pstmt.execute();
        }
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
    public HashMap<Integer,List<Participant>> getParticipantByEvent() throws SQLException{
        HashMap<Integer,List<Participant>> mapParticipantByEvent = new HashMap<>();
        HashMap<Integer,Participant> mapParticipant = getParticipantByID();
        List<Participant> participantList = new ArrayList<>();
        try (Connection con = cm.getConnection()) {

            String sql = "SELECT idEvent, idParticipant,ticketID FROM EventParticipant Order by idEvent ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int idEvent = 0;
            int currentEvent = -1;
            boolean flagFirst=false;
            while(rs.next()){
                idEvent= rs.getInt("idEvent");
                int idParticipant =rs.getInt("idParticipant");
                int ticketID = rs.getInt("ticketID");
                if(!flagFirst) {
                    currentEvent=idEvent;
                    flagFirst=true;
                }
                if(idEvent!=currentEvent) {
                    List<Participant> participants = new ArrayList<>(participantList);

                    mapParticipantByEvent.put(currentEvent,participants);
                    participantList.clear();
                }
                Participant participant = new Participant(mapParticipant.get(idParticipant).getId(),
                                                        mapParticipant.get(idParticipant).getFname(),
                                                        mapParticipant.get(idParticipant).getLname(),
                                                        mapParticipant.get(idParticipant).getEmail(),
                                                        mapParticipant.get(idParticipant).getPhoneNumber());
                participant.setTicketID(ticketID);
                participantList.add(participant);

                participantList.get(participantList.size()-1).setTicketID(ticketID);
                currentEvent=idEvent;
            }
            List<Participant> participants = new ArrayList<>(participantList);
            mapParticipantByEvent.put(currentEvent,participants);
        }
        return mapParticipantByEvent;
    }
    public List<Events> getParticipantEvent(Participant participant) throws SQLServerException {
        List<Events> participantEvents = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql="SELECT e.id as eventID, e.name as eventName, e.location as eventLocation, e.description as eventDescription, " +
                        " e.startDate as eventStartDate, e.endDate as eventEndDate, e.itinerary as eventItinerary, count(*) as numberTickets " +
                        " FROM EVENTS e INNER JOIN EventParticipant ep ON e.id = ep.idEvent " +
                        " INNER JOIN Participant p ON ep.idParticipant = p.id " +
                        " WHERE p.id = ?" +
                        " group by e.id, e.name, e.location, e.description, " +
                        " e.startDate, e.endDate, e.itinerary";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,participant.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Events event = new Events(rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getString("eventLocation"),
                        rs.getString("eventDescription"),
                        rs.getObject("eventStartDate",LocalDateTime.class),
                        rs.getObject("eventEndDate",LocalDateTime.class),
                        rs.getString("eventItinerary"));
                participantEvents.add(event);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return participantEvents;
    }

    public String[][] getParticipantsForEventById (int idOfEvent) {
            //int counter = 1;
            int row = 0;
            int column = 0;
            String[][] finalArray = new String[getNumberOfRow(idOfEvent)][4];


        try (Connection con = cm.getConnection()) {
            String sql ="SELECT p.fname, p.lname, p.phoneNumber, tt.typeName " +
                    "FROM Participant p, EventParticipant ep,Events e,TicketType tt,Ticket t " +
                    "WHERE " +
                    "ep.idParticipant = p.id AND " +
                    "e.id = ep.idEvent AND " +
                    "t.ticketTypeID = tt.id  AND " +
                    "t.id = ep.ticketID AND " +
                    "e.id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idOfEvent);
            ResultSet rs = pstmt.executeQuery();


            while(rs.next())
            {
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String phoneNumber = String.valueOf(rs.getInt("phoneNumber"));
                String typeTicket = rs.getString("typeName");
                finalArray[column][row] = fName;
                finalArray[column][row + 1] = lName;
                finalArray[column][row + 2] = phoneNumber;
                finalArray[column][row + 3] = typeTicket;
                column++;
                row = 0;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return finalArray;
    } // TODO also add information about unique id of ticket


    public int getNumberOfRow (int id) {

        int number = 0;

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT COUNT(*) AS numberRow FROM Participant p " +
                    "JOIN EventParticipant ep ON ep.idParticipant = p.id " +
                    "JOIN Events e ON e.id = ep.idEvent " +
                    "INNER JOIN Ticket tt ON tt.id = ep.ticketID " +
                    "WHERE e.id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();


            while(rs.next())
            {
                number = rs.getInt("numberRow");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("number of items: " + number);
        return number;
    } // this private method returns the number of rows in order to create a 2D array in getParticipantsForEventById method

    private HashMap<Integer,Integer> getParticpantNumberByEvent() {
        HashMap<Integer,Integer> mapEventParticpantNumber = new HashMap<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT idEvent,count(idEvent) as countLines " +
                        "FROM EventParticipant " +
                        "GROUP BY idEvent";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                mapEventParticpantNumber.put(rs.getInt("idEvent"),rs.getInt("countLines"));
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mapEventParticpantNumber;
    }
    private HashMap<Integer,Participant> getParticipantByID() throws SQLException {
        HashMap<Integer,Participant> mapParticipant = new HashMap<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM PARTICIPANT";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int idParticipant =0;
            while(rs.next()) {
                idParticipant=rs.getInt("id");
                Participant participant = new Participant(idParticipant,
                                                        rs.getString("fname"),
                                                        rs.getString("lname"),
                                                        rs.getString("email"),
                                                        rs.getString("phoneNumber") );
                mapParticipant.put(idParticipant,participant);
            }
        }
        return mapParticipant;
    }
}
