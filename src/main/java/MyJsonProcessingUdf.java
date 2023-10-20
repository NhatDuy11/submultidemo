import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Iterator;
@UdfDescription(name = "demo_submultivalue", description = "Converts XML to JSON submultivalue")

public class MyJsonProcessingUdf {
    @Udf(description = "Process JSON and return a new JSON")
    public String processJson(@UdfParameter(value = "submultivalue1") final String xmlStr) {
        try {
            String xmlFiltered = xmlStr.replaceAll("\\p{C}", "");

            JSONObject jsonObj = XML.toJSONObject(xmlFiltered);
            JSONObject result = processObject(jsonObj);
            String jsonPrettyPrintString = result.toString(4);
            return jsonPrettyPrintString.toString();
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
                        if (subObj.has("m")) {
                            String content = subObj.getString("content");
                            sb.append(content);
                            if (i != jsonArray.length() - 1) {
                                sb.append(" | ");
                            }
                        } else if (subObj.has("s")) {
                            String content = subObj.getString("content");
                            sb.append(content);
                            if (i != jsonArray.length() - 1) {
                                sb.append(" ^ ");
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
            } else {
                if (value instanceof Number) {
                    result.put(key, value);
                } else {
                    result.put(key, value.toString());
                }
            }
        }

        return result;
    }
}
