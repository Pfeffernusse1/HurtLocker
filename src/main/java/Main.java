import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        Map<String, List<String>> map = mainParse(output);
        printToFile(map);
        //System.out.println(output);

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

    public static Map<String, List<String>> mainParse(String data) throws Exception {
        Map<String, List<String>> map = new LinkedHashMap<>();
        String regex = "##";
        Pattern pattern = Pattern.compile(regex);
        String[] items = pattern.split(data);
        int errCount = 0;

        for (String item : items) {
            List<String> itemList = miniParse(item);

            // checking cookies + variations of cookies, replacing the name in list with "cookies"
            if (itemList.get(0) != null && itemList.get(0).matches("^c[\\w]*s$")) {
                itemList.set(0, "cookies");

            }

            // if null, up the count
            // else if the map already has the grocery item in it,
            // add the price to the list
            // else create a new key-value pair

            if (itemList.contains(null)) {
                errCount++;
            } else if (map.containsKey(itemList.get(0))) {
                map.get(itemList.get(0)).add(itemList.get(1));
            } else {

                List<String> prices = new ArrayList<>();
                prices.add(itemList.get(1));
                map.put(itemList.get(0), prices);
            }
        }
        // creating the error key-value pair using errCount to make a new list of that size
        map.put("error", Arrays.asList(new String[errCount]));
        return map;
    }

    public static ArrayList<String> miniParse (String item) throws Exception {
        String regex = "[$&+,;=?@#|'<>^*()%!-]";
        Pattern pattern = Pattern.compile(regex);
        String[] temp = pattern.split(item.toLowerCase());
        ArrayList<String> itemList = new ArrayList<>();
//        for(String items: temp){
//            String patternString = "(item)";
//            Pattern patternTemp = pattern.compile(item);
//            Matcher matcher = patternTemp.matcher(items);
//        }
        // temp[0] = {name: milk}
        Pattern newPattern = Pattern.compile("(.+):");
        for (String items : temp) {
            String[] newTemp = newPattern.split(items);
            if (newTemp.length == 0) {
                itemList.add(null);
                //System.out.println("null");
            } else {
                itemList.add(newTemp[1]);
                //System.out.println("in mini for: " + items + "       thing being added: " + newTemp[1]);
            }
        }
        return itemList;
    }

    public static String capitalizeFirstLetter(String groceryItem){
        String firstLetterCap = "" + groceryItem.charAt(0);
        return firstLetterCap.toUpperCase() + groceryItem.substring(1);
    }

    public static void printToFile (Map < String, List < String >> map) {
        File file = new File("hurtlocker-results.txt");

        try {
            PrintWriter printWriter = new PrintWriter(file);

            String formattedStringName = "name:%8s        seen: %s times\n=============        =============";
            String formattedStringPrice = "Price:%7s        seen: %s times";
            String formattedStringError = "Errors               seen: %s times";

            for (String key : map.keySet()) {
                String capitalizedKey = capitalizeFirstLetter(key);
                String outputName = String.format(formattedStringName, capitalizedKey, map.get(key).size());
                if(key.equals("error")){
                    outputName = String.format(formattedStringError, map.get(key).size());
                    printWriter.println(outputName);
                }
                else{
                    printWriter.println(outputName);
                }

//                System.out.println(outputName);
                if (key != "error") {
                    Set<String> uniquePrice = new LinkedHashSet<>(map.get(key));
                    Iterator iterator = uniquePrice.iterator();
                    int count = 1;

                    while (iterator.hasNext()) {
                        String price = (String) iterator.next();
                        // TODO: REPLACE map.get(key).size() with count from counter()
                        String outputPrice = String.format(formattedStringPrice, price, counter(map.get(key), price));
                        //System.out.println(outputPrice);
                        printWriter.println(outputPrice);
                        if(count == 1){
                            printWriter.println("-------------        -------------");
                        }
                        count++;
                    }
                    printWriter.println();
                }
            }
            printWriter.close();
        }
        catch(IOException e){
            System.out.println("HELP");
        }
    }
}
