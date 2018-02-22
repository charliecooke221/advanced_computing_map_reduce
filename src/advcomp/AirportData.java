package advcomp;

public class AirportData {

    private String airportName;
    private String airportCode;
    private Double latititude;
    private Double longitiude;

    public AirportData(String apName,String apCode, double lat, double lon){
        this.airportName = apName;
        this.airportCode = apCode;
        this.latititude = lat;
        this.longitiude = lon;
}

}
