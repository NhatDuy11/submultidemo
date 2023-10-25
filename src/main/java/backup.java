import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

@UdfDescription(name = "demo_submultivalue", description = "Converts XML to JSON submultivalue")

public class backup{
    @Udf(description = "Process JSON and return a new JSON")
    public String processJson(@UdfParameter(value = "submultivalue3") final String xmlStr) {
        try {
            String xmlFiltered = xmlStr.replaceAll("\\p{C}", "");
            JSONObject jsonObj = XML.toJSONObject(xmlFiltered);

            JSONObject result = processObject(jsonObj);
            String jsonPrettyPrintString = result.toString(4);
            return jsonPrettyPrintString;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject processObject(JSONObject obj) throws JSONException {
        Iterator<String> keys = obj.keys();
        JSONObject result = new JSONObject();

        while (keys.hasNext()) {
            String key = keys.next();
            Object value = obj.get(key);

            if (value instanceof JSONArray) {
                StringBuilder sb = new StringBuilder();
                JSONArray jsonArray = (JSONArray) value;
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object item = jsonArray.get(i);
                    if (item instanceof JSONObject) {
                        JSONObject subObj = (JSONObject) item;
                        if (subObj.has("content")) {
                            String content = subObj.getString("content");
                            sb.append(content);
                            if (i != jsonArray.length() - 1) {
                                sb.append(" | ");
                            }
                        } else {
                            sb.append("");
                            if (i != jsonArray.length() - 1) {
                                sb.append(" | ");
                            }
                        }
                    } else {
                        sb.append(item.toString());
                        if (i != jsonArray.length() - 1) {
                            sb.append(" | ");
                        }
                    }
                }
                result.put(key, sb.toString());
            } else if (value instanceof JSONObject) {

                result.put(key, processObject((JSONObject) value));
            }
            else {
                if (value instanceof Number) {
                    result.put(key, value);
                } else {
                    result.put(key, value.toString());
                }
            }
        }

        return result;
    }


    public static void main(String[] args) {
        backup udf = new backup();
        String xml = "<row id=\"75825\">\n" +
                "  <c1>140140</c1>\n" +
                "  <c2>1001</c2>\n" +
                "  <c3>Current Account</c3>\n" +
                "  <c5>John Hay Maddison</c5>\n" +
                "  <c7>TR</c7>\n" +
                "  <c8>USD</c8>\n" +
                "  <c9>1</c9>\n" +
                "  <c10>100.01</c10>\n" +
                "  <c11>26</c11>\n" +
                "  <c12>27</c12>\n" +
                "  <c46>20170331</c46>\n" +
                "  <c76>NO</c76>\n" +
                "  <c78>20170321</c78>\n" +
                "  <c85>1001</c85>\n" +
                "  <c89>YES</c89>\n" +
                "  <c93>USD</c93>\n" +
                "  <c94>1</c94>\n" +
                "  <c95>USD</c95>\n" +
                "  <c96>1</c96>\n" +
                "  <c99>LEGACY</c99>\n" +
                "  <c99 m=\"2\">T24.IBAN</c99>\n" +
                "  <c99 m=\"3\">PREV.IBAN</c99>\n" +
                "  <c100/>\n" +
                "  <c100 m=\"2\">GB16DEMO60161300075825</c100>\n" +
                "  <c100 m=\"3\"/>\n" +
                "  <c108>YES</c108>\n" +
                "  <c142>Y</c142>\n" +
                "  <c157>WORKING</c157>\n" +
                "  <c158>DEBITS</c158>\n" +
                "  <c181>AA170803M4HP</c181>\n" +
                "  <c214>1</c214>\n" +
                "  <c215>93133_OFFICER__OFS_SEAT_AAACT170802VCXJMX1</c215>\n" +
                "  <c216>1705140716</c216>\n" +
                "  <c217>93133_OFFICER_OFS_SEAT_AAACT170802VCXJMX1</c217>\n" +
                "  <c218>GB0010001</c218>\n" +
                "  <c219>1</c219>\n" +
                "</row>";
        String result = udf.processJson(xml);
        System.out.println(result);
    }
}