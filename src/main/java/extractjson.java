import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



public class extractjson {
    public static void main(String[] args) throws JsonProcessingException {

        String xmlData = "<root><field1>value1</field1><field2>value2</field2></root>";


        ObjectMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xmlData);


        ObjectNode formattedJson = xmlMapper.createObjectNode();


        jsonNode.fields().forEachRemaining(entry -> {
            formattedJson.put(entry.getKey(), entry.getValue().asText());
        });


        System.out.println(formattedJson.toString());
    }
}
