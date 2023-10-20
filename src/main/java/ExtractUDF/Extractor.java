package ExtractUDF;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.*;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
@UdfDescription(name = "ExtractJson", description = "Converts XML to JSON ExtractJsonField")

public class Extractor{
    @Udf(description = "Converts XML to JSON and Extract json Field" )

    public Map<String, String> convert(@UdfParameter(value =  "inputXML")final  String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xml);
        return traverseAndCollect(jsonNode);
    }

    private Map<String, String> traverseAndCollect(JsonNode node) {
        Map<String, String> keyValuePairs = new LinkedHashMap<>();
        keyValuePairs.put(node.fieldNames().next(), ""); // add the root key
        collect(node, keyValuePairs);
        return keyValuePairs;
    }

    private void collect(JsonNode node, Map<String, String> keyValuePairs) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                System.out.println("key: " + key);
                JsonNode value = field.getValue();
                System.out.println("value : " + value);
                if (value.isValueNode()) { // check if the value is a value node
                    keyValuePairs.put(key, value.asText());
                } else if (value.fields().hasNext()) {
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

    public static void main(String[] args) throws IOException {
        String xml = "<bookstore><book>" +
                "<title>Everyday Italian</title>" +
                "<author>Giada De Laurentiis</author>" +
                "<year>2005</year>" +
                "</book></bookstore>";
        Extractor extractor = new Extractor();
        Map<String, String> result = extractor.convert(xml);
//        System.out.println(result); // print the result
    }
}
