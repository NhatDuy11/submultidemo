import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

public class XmlToJson {
    public static void main(String[] args) throws IOException {
        // Chuỗi XML đầu vào (XML phức tạp)
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<msg xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" dbName=\"ISTP\" version=\"1.0.0\" xsi:noNamespaceSchemaLocation=\"mqcap.xsd\">\n" +
                "    <rowOp>\n" +
                "        <Row hasLOBCols=\"0\" srcName=\"ATMSTATUS\" srcOwner=\"SW_OWN\" subName=\"ATMSTATUS0001\">\n" +
                "            <col name=\"SHCLOG_ID\">\n" +
                "                <after>AAEAsgAkZN52XQAB </after>\n" +
                "            </col>\n" +
                "            <col name=\"INSTITUTION_ID\">\n" +
                "                <after>1</after>\n" +
                "            </col>\n" +
                "            <col name=\"GROUP_NAME\">\n" +
                "                <after>SGE5050101</after>\n" +
                "                <primary_key>0</primary_key>\n" +
                "                <data_type>varchar2</data_type>\n" +
                "            </col>\n" +
                "            <col name=\"UNIT\">\n" +
                "                <after>97</after>\n" +
                "                <primary_key>0</primary_key>\n" +
                "                <data_type>integer</data_type>\n" +
                "            </col>\n" +
                "            <col name=\"FUNCTION_CODE\">\n" +
                "                <after>200</after>\n" +
                "            </col>\n" +
                "            <col name=\"LOGGED_TIME\">\n" +
                "                <after>2023-08-18 02:34:53.000000000</after>\n" +
                "            </col>\n" +
                "            <col name=\"LOG_DATA\">\n" +
                "                <after>12097002033P20</after>\n" +
                "            </col>\n" +
                "            <col name=\"SITE_ID\">\n" +
                "                <after>1</after>\n" +
                "                <primary_key>1</primary_key>\n" +
                "                <data_type>integer</data_type>\n" +
                "            </col>\n" +
                "        </Row>\n" +
                "    </rowOp>\n" +
                "</msg>\n";
        // Sử dụng XmlMapper để chuyển đổi XML thành JSON
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xml);

        // Duyệt qua JSON để trích xuất cặp khóa-giá trị và in ra từng cặp
         traverseAndPrint(jsonNode, "");
    }

    public static void traverseAndPrint(JsonNode node, String prefix) {
        if (node.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();

                // In ra key và value
                System.out.println(key + ":" + value.asText());

                traverseAndPrint(value, prefix);
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayItem = node.get(i);
                // In ra chỉ mục của mảng
                System.out.println(prefix);
                traverseAndPrint(arrayItem, prefix);
            }
        }
    }
}
