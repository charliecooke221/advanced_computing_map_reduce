package advcomp;

import java.util.ArrayList;
import java.util.List;

public class MasterControlNode {

    int job = 1;
    int threadCount = 4;
    private ArrayList<PassengerData> passengerDataList;
    private ArrayList<AirportData> airportDataList;


    public MasterControlNode(ArrayList<PassengerData> passDataList, ArrayList<AirportData> airDataList){
        passengerDataList = passDataList;
        airportDataList = airDataList;
    }

    public void MapReduce() {

        ArrayList<ArrayList<PassengerData>> dataChunks = SplitPassengerArrayListIntoChunks(passengerDataList, threadCount);
        ArrayList<Map> mappers = new ArrayList<>();

        ArrayList<PassengerData> test1 = dataChunks.get(1);


        for(int i = 0; i < threadCount; i++){

            ArrayList<PassengerData> tempPassengerDataArrayList = dataChunks.get(i);

            String threadName = String.format("Mapper%d", i);

            Map map = new Map(tempPassengerDataArrayList, airportDataList,job,threadName);
            map.start();

            //mappers.add(new Map(tempPassengerDataArrayList,))

            // instantiate new map objects for thread count

        }




    }






    public ArrayList<ArrayList<PassengerData>> SplitPassengerArrayListIntoChunks(ArrayList<PassengerData> passData, int noOfChunks)
    {
        int arrayLen = passData.size();
        int chunkSize = arrayLen / noOfChunks;

        ArrayList<ArrayList<PassengerData>> dataChunks = new ArrayList<ArrayList<PassengerData>>();

        int endIndex = chunkSize;
        int startIndex = 0;

        for (int i = 0; i < noOfChunks; i++){

            ArrayList<PassengerData> passengerDataListChunk = new ArrayList<PassengerData>(passData.subList(startIndex,endIndex ));
            dataChunks.add(passengerDataListChunk);

            startIndex = startIndex + chunkSize;
            endIndex = endIndex + chunkSize;
        }

        //System.out.print(dataChunks.get(0).size());
        return dataChunks;

    }
}
