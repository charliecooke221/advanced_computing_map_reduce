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
    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public Double getLatititude() {
        return latititude;
    }

    public void setLatititude(Double latititude) {
        this.latititude = latititude;
    }

    public Double getLongitiude() {
        return longitiude;
    }

    public void setLongitiude(Double longitiude) {
        this.longitiude = longitiude;
    }
}
