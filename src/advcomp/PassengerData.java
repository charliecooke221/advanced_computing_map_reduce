package advcomp;

public class PassengerData {



    private String passengerId;
    private String flightId;
    private String originAirport;
    private String destinationAirport;
    private int departureTime;
    private int totalFlightTime;

    //constructor
    public PassengerData(String passID, String flyID, String origAirport,String destAirport,int depTime, int flyTime){
        this.passengerId = passID;
        this.flightId = flyID;
        this.originAirport = origAirport;
        this.destinationAirport = destAirport;
        this.departureTime = depTime;
        this.totalFlightTime = flyTime;

    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public int getTotalFlightTime() {
        return totalFlightTime;
    }

    public void setTotalFlightTime(int totalFlightTime) {
        this.totalFlightTime = totalFlightTime;
    }
}
