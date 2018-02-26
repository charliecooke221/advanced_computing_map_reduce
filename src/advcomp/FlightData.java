package advcomp;

import java.util.Date;

public class FlightData {


    private String passengerId;
    private String originAirport;
    private String destinationAirport;
    private String departureTime;
    private String arrivalTime;
    private String totalFlightTime;

    //constructor
    public FlightData(String passID, String origAirport,String destAirport,String depTime,String arrTime, String flyTime){
        this.passengerId = passID;
        this.originAirport = origAirport;
        this.destinationAirport = destAirport;
        this.departureTime = depTime;
        this.arrivalTime = arrTime;
        this.totalFlightTime = flyTime;

    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTotalFlightTime() {
        return totalFlightTime;
    }
}
