import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
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
        }
        return itemList;
    }
}
