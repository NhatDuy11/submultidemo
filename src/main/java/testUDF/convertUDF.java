package testUDF;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.json.JSONObject;
import org.json.XML;
import java.util.HashMap;
import java.util.Map;
import io.confluent.ksql.function.udf.UdfParameter;
@UdfDescription(name = "xml_to_json1", description = "Converts XML to JSON")

public class convertUDF {
    @Udf(description = "Converts XML to JSON")
    public Map<String, String> xmlToJson(@UdfParameter(value = "xml") final  String xml ) {
        if (xml == null) {
            return null;
        }
        try {
            String xmlFiltered = xml.replaceAll("\\p{C}", "");
            JSONObject xmlJSONObj = XML.toJSONObject(xmlFiltered);
            System.out.println(xmlJSONObj);

            // Create a map to store the key-value pairs
            Map<String, String> keyValuePairs = new HashMap<>();

            // Iterate over keys in the JSON object
            for (String key : xmlJSONObj.keySet()) {
                // Get the value corresponding to the key
                String value = xmlJSONObj.get(key).toString();
                // Add the key-value pair to the map
                keyValuePairs.put(key, value);
            }

            return keyValuePairs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        convertUDF converter = new convertUDF();

        // Test XML string
        String testXml = "<bookstore><book>" +
                "<title>Everyday Italian</title>" +
                "<author>Giada De Laurentiis</author>" +
                "<year>2005</year>" +
                "</book></bookstore>";

        // Convert XML to JSON and print the result
        Map<String, String> result = converter.xmlToJson(testXml);
        System.out.println(result);

    }
}
