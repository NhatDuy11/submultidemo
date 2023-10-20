import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Iterator;
import java.util.Map;

public class subvalue {
    public static void main(String[] args) throws JsonProcessingException {
        String xmlData = "\u0006<row id=\"USD121150001\"> <c2>12115</c2> <c3>Travel</c3> <c5>Travel</c5> <c6>TRAVE12115</c6> <c7>TR</c7> <c8>USD</c8> <c9>1</c9> <c11>1</c11> <c76>NO</c76> <c78>19980723</c78> <c85>12115</c85> <c93>USD</c93> <c94>1</c94> <c95>USD</c95> <c96>1</c96> <c99>LEGACY</c99> <c108>NO</c108> <c214>1</c214> <c215 m=\"2\">213_CONV.ACCOUNT.201405</c215><c215 m=\"3\">213_CONV.ACCOUNT.201406</c215><c215 m=\"4\">213_CONV.ACCOUNT.201407</c215><c216>1704062201</c216><c217>78671_INPUTTER_OFS_MB.OFS.AUTH</c217><c218>GB0010001</c218><c219>1</c219></row>\n";
        String xmlFiltered = xmlData.replaceAll("\\p{C}", "");

        // Chuyển đổi XML sang JSON
        JSONObject xmlJSONObj = XML.toJSONObject(xmlFiltered);
        String jsonData = xmlJSONObj.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        rootNode.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            String result = processJson(value);
            System.out.println(result);
//            System.out.println("\"" + key + "\": " + result);
        });
    }

    public static String processJson(JsonNode node) {
        StringBuilder result = new StringBuilder();

        if (node.isArray()) {
            for (JsonNode element : node) {
                if (element.isArray() || element.isObject()) {
                    result.append(processJson(element));
                } else {
                    result.append(element.asText());
                }
                result.append(" | ");
            }
        } else if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getValue().isArray() || field.getValue().isObject()) {
                    result.append(processJson(field.getValue()));
                } else {
                    result.append(field.getValue().asText());
                }
                result.append(" ^ ");
            }
        } else {
            result.append(node.asText());
        }

        return result.toString();
    }
}
