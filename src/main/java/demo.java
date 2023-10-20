import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class demo {
    public static void main(String[] args) throws IOException {
        // Chuỗi XML đầu vào (đây có thể là bất kỳ cấu trúc XML nào)
        String xml = "<avv><abc>4100</abc><cde>4045</cde><llala>2979</llala></avv>";

        // Sử dụng XmlMapper để chuyển đổi XML thành JSON
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xml);

        // Duyệt qua JSON để trích xuất cặp khóa-giá trị
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();
            System.out.println(key + ":" + value.asText());
        }
    }
}
