import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.*;

public class xmltojson1{
    public static void main(String[] args) throws IOException {
        String xml = "<bookstore><book>" +
                "<title>Everyday Italian</title>" +
                "<author>Giada De Laurentiis</author>" +
                "<year>2005</year>" +
                "</book></bookstore>";
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xml);
        System.out.println(jsonNode);
        Map<String, String> result = traverseAndCollect(jsonNode);
        System.out.println(result); // print the result
    }

    public static Map<String, String> traverseAndCollect(JsonNode node) {
        Map<String, String> keyValuePairs = new LinkedHashMap<>();
        keyValuePairs.put(node.fieldNames().next(), ""); // add the root key
        collect(node, keyValuePairs);
        return keyValuePairs;
    }
    private static void collect(JsonNode node, Map<String, String> keyValuePairs) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();
                if (value.isValueNode()) { // check if the value is a value node
                    keyValuePairs.put(key, value.asText());
                } else {
                    keyValuePairs.put(key, ""); // add the key with an empty value
                }
                collect(value, keyValuePairs);
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                collect(node.get(i), keyValuePairs);
            }
        }
    }
}