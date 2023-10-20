import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class test3 {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "  \"row\": {\n" +
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
                "      \"1_DIM\",\n" +
                "      {\n" +
                "        \"m\": 2,\n" +
                "        \"content\": \"213_CONV.ACCOUNT.201405\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"c94\": 1,\n" +
                "    \"c214\": 1\n" +
                "  }\n" +
                "}"; // Thay thế bằng JSON của bạn

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject result = processObject(jsonObj);
            System.out.println(result);
        } catch (JSONException e) {
            e.printStackTrace();
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


