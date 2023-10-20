import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class test1{
    public static void main(String[] args) throws IOException {
        String jsonData = "{\"row\": {\n" +
                "    \"c85\": 12115,\n" +
                "    \"c96\": 1,\n" +
                "    \"c11\": 1,\n" +
                "    \"c99\": \"LEGACY\",\n" +
                "    \"c76\": \"NO\",\n" +
                "    \"c78\": 19980723,\n" +
                "    \"c2\": 12115,\n" +
                "    \"c3\": \"Travel\",\n" +
                "    \"c5\": \"Travel\",\n" +
                "    \"c6\": \"TRAVE12115\",\n" +
                "    \"c7\": \"TR\",\n" +
                "    \"c8\": \"USD\",\n" +
                "    \"c9\": 1,\n" +
                "    \"c217\": \"78671_INPUTTER_OFS_MB.OFS.AUTH\",\n" +
                "    \"c216\": 1704062201,\n" +
                "    \"c219\": 1,\n" +
                "    \"id\": \"USD121150001\",\n" +
                "    \"c108\": \"NO\",\n" +
                "    \"c218\": \"GB0010001\",\n" +
                "    \"c93\": \"USD\",\n" +
                "    \"c95\": \"USD\",\n" +
                "    \"c215\": [\n" +
                "        {\n" +
                "            \"m\": 2,\n" +
                "            \"content\": \"213_CONV.ACCOUNT.201405\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"m\": 3,\n" +
                "            \"content\": \"213_CONV.ACCOUNT.201406\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"m\": 4,\n" +
                "            \"content\": \"213_CONV.ACCOUNT.201407\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"c94\": 1,\n" +
                "    \"c214\": 1\n" +
                "}}\n"; // Thay thế bằng JSON của bạn

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        processJson(rootNode, "");
    }

    public static Object processJson(JsonNode node, String keyPrefix) {
        if (node.isArray()) {
            StringBuilder sb = new StringBuilder();
            for (JsonNode element : node) {
                sb.append(processJson(element, keyPrefix));
                sb.append(" | ");
            }
            System.out.println("\"" + keyPrefix + "\": " + sb.toString());
        } else if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();
                if (value.isObject() || value.isArray()) {
                    processJson(value, key);
                } else {
                    System.out.println("\"" + keyPrefix + "." + key + "\": \"" + value.asText() + "\"");
                }
            }
        } else {
            System.out.println("\"" + keyPrefix + "\": \"" + node.asText() + "\"");
        }
        return null;
    }
}