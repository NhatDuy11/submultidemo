import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToJsonExtractor {
    public static void main(String[] args) throws  Exception {
        String xmlData = "<msg xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" dbName=\"ISTP\" version=\"1.0.0\" xsi:noNamespaceSchemaLocation=\"mqcap.xsd\">\n" +
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
        ObjectMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xmlData);

        // Tạo một ObjectNode mới để chứa kết quả
        ObjectNode result = xmlMapper.createObjectNode();

        // Duyệt qua các nút JSON và trích xuất từng cột riêng biệt
        jsonNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            JsonNode fieldValue = entry.getValue();
            result.set(fieldName, fieldValue);
        });

        // In kết quả JSON đã trích xuất
        System.out.println(result.toPrettyString());

    }
}
