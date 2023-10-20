import org.json.*;
import org.json.JSONArray;

import java.util.Iterator;

public class test2 {
    public String xmlToJson(final String xml) {
        if (xml == null) {
            return null;
        }
        try {
            String xmlFiltered = xml.replaceAll("\\p{C}", "");
            JSONObject xmlJSONObj = XML.toJSONObject(xmlFiltered);
            convertJsonArrayToString(xmlJSONObj);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            return jsonPrettyPrintString;
        } catch (Exception e) {
            return null;
        }
    }

    private void convertJsonArrayToString(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                convertJsonArrayToString((JSONObject) value);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                if (jsonArray.length() == 2 && jsonArray.get(0) instanceof String && jsonArray.get(1) instanceof JSONObject) {
                    jsonObject.put(key, jsonArray.getJSONObject(1).getString("content"));
                }
            }
        }
    }

    public static void main(String[] args) {
        test2 test = new test2();
        String xmlString = "\u0006<row id=\"USD121150001\"> <c2>12115</c2> <c3>Travel</c3> <c5>Travel</c5> <c6>TRAVE12115</c6> <c7>TR</c7> <c8>USD</c8> <c9>1</c9> <c11>1</c11> <c76>NO</c76> <c78>19980723</c78> <c85>12115</c85> <c93>USD</c93> <c94>1</c94> <c95>USD</c95> <c96>1</c96> <c99>LEGACY</c99> <c108>NO</c108> <c214>1</c214> <c215 m=\"2\">213_CONV.ACCOUNT.201405</c215><c215 m=\"3\">213_CONV.ACCOUNT.201406</c215><c215 m=\"4\">213_CONV.ACCOUNT.201407</c215><c216>1704062201</c216><c217>78671_INPUTTER_OFS_MB.OFS.AUTH</c217><c218>GB0010001</c218><c219>1</c219></row>\n";
        String jsonString = test.xmlToJson(xmlString);
        if (jsonString != null) {
            System.out.println(jsonString);
        } else {
            System.out.println("Conversion failed.");
        }

    }
}