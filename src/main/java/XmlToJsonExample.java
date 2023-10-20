import org.json.JSONObject;
import org.json.XML;
public class XmlToJsonExample {
    public static void main(String[] args) {
        String xmlData = "<root><field1>value1</field1><field2>value2</field2></root>";


        JSONObject jsonObject = XML.toJSONObject(xmlData);


        String field1 = jsonObject.getJSONObject("root").getString("field1");
        String field2 = jsonObject.getJSONObject("root").getString("field2");


        System.out.println("field1: " + field1);
        System.out.println("field2: " + field2);

    }
}
