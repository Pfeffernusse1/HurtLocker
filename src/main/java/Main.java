import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);
    }

    public ArrayList<String> miniParse(String item) throws Exception {
        String regex = "[$&+,;=?@#|'<>.^*()%!-]";
        Pattern pattern = Pattern.compile(regex);
        String[] temp = pattern.split(item.toLowerCase());
        ArrayList<String> itemList = new ArrayList<>();
//        for(String items: temp){
//            String patternString = "(item)";
//            Pattern patternTemp = pattern.compile(item);
//            Matcher matcher = patternTemp.matcher(items);
//        }
        // temp[0] = {name: milk}
        Pattern newPattern = Pattern.compile(":(.+)");
        for(String items: temp){
            String[] newTemp = newPattern.split(items);
            itemList.add(newTemp[0]);
            //System.out.println(output);

        }return itemList;
    }


    // count() how many time I see  the prices.
    public static Integer counter(List<String> prices, String priceToCount) {
        int count = 0;
        for (int i = 0; i < prices.size(); i++) {
            if (priceToCount.equals(prices.get(i))) {
                count++;
            }

        }
        return count;
    }
    public static Map<String, List<String>> mainParse(String data){
        Map<String, List<String>> map = new HashMap<>();
        String regex = "##";
        Pattern pattern = Pattern.compile(regex);
        String[] items = pattern.split(data);
        int errCount = 0;
        /*
              ^[A-Za-z0-9]*$:^[A-Za-z0-9]*$
        */

        for(String item: items){
            List<String> itemList = new ArrayList<>();//miniParse(item);

            // if error is returned, up the count (tbc, dunno how we're doing error yet)
            // else if the map already has the grocery item in it,
            // add the price to the list
            // else create a new key-value pair
            if(itemList.get(0).equals("error")){
                errCount++;
            }
            else if(map.containsKey(itemList.get(0))){
                map.get(itemList.get(0)).add(itemList.get(1));
            }
            else{
                List<String> prices = new ArrayList<>();
                prices.add(itemList.get(1));
                map.put(itemList.get(0), prices);
            }
            //System.out.println(item);
        }
        map.put("error", Arrays.asList(new String[errCount]));
        return map;
    }

    public static void printToFile(){
        String formattedStringName = "name:%8s       seen: %s times";
        String[] names = new String[]{"Milk", "Cookies", "Bread", "tacos"};
        String[] times = new String[]{"6", "2", "4", "3"};

        for(int i = 0; i < names.length; i++){
            String output = String.format(formattedStringName, names[i], times[i]);
            System.out.println(output);
        }

    }

}
