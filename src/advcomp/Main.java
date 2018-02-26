package advcomp;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception{

        ReaderCleaner reader = new ReaderCleaner();
        reader.read();
        MasterControlNode master = new MasterControlNode(reader.getPassengerDataList(),reader.getAirportDataList());
        master.MapReduce();



    }

}
