package advcomp;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class Reduce {

    Map.Entry<String,Integer> output;

    public Reduce(String primaryKey, ArrayList<Combiner> combiners){ //overloaded constructors

        countValuesForKey(primaryKey,combiners);
    }

    private void countValuesForKey(String primaryKey,ArrayList<Combiner> combiners){
        int numberOfValuesForPrimaryKey = 0;

        for(Combiner combiner : combiners){ //aggregate output of each combiner
            if(combiner.getOriginAirportPassengerIdsHM().get(primaryKey) != null){ //if combiner output contains primary key
                numberOfValuesForPrimaryKey = numberOfValuesForPrimaryKey + combiner.getOriginAirportPassengerIdsHM().get(primaryKey).size(); //add values to count
            }
        }

        //System.out.print(primaryKey + " " + numberOfValuesForPrimaryKey + "\n");
        output = new SimpleEntry<>(primaryKey, numberOfValuesForPrimaryKey); //save key/count pair
    }


    public Map.Entry<String, Integer> getOutput() {
        return output;
    }
}
