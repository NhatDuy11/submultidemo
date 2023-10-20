import org.json.JSONArray;
import org.json.JSONObject;

public class multileveljson {
    public static void main(String[] args) {
        String jsonString = "{ \"c215\": [ \"1_DIM\", { \"m\": 2, \"content\": \"213_CONV.ACCOUNT.201405\" } ] }";

        // Tạo một JSONObject từ chuỗi JSON
        JSONObject jsonObject = new JSONObject(jsonString);

        // Lấy JSONArray từ JSONObject
        JSONArray jsonArray = jsonObject.getJSONArray("c215");

        // Lấy các phần tử từ JSONArray
        String firstElement = jsonArray.getString(0);
        JSONObject secondElement = jsonArray.getJSONObject(1);

        // Lấy các giá trị từ JSONObject thứ hai
        int m = secondElement.getInt("m");
        String content = secondElement.getString("content");

        // In các giá trị
        System.out.println("First element: " + firstElement);
        System.out.println("m: " + m);
        System.out.println("content: " + content);
    }
}
