package advcomp;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Map extends Thread {

    private List<PassengerData> PassengerDataList = new ArrayList<PassengerData>();
    private List<AirportData> AirportDataList = new ArrayList<AirportData>();
    HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

    public Map(List<PassengerData> passDataList,List<AirportData> airDataList){

    }

}
