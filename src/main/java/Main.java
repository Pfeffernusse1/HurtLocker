import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main  {

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
        System.out.println(output);

    }


    // count() how many time I see  the prices.
    public static Integer counter(List<String> prices, String priceToCount) {
       int count = 0;
        for (int i = 0; i < prices.size(); i++) {
         if(priceToCount.equals(prices.get(i))){
             count++;
         }

          }
        return count;
        }
        p
    }
