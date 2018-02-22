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

}
