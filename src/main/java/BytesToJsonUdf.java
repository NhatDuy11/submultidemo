import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONObject;
import org.json.XML;

import java.util.Properties;
import java.util.concurrent.Future;


public class BytesToJsonUdf {
    @Udf(description = "Converts XML to JSON")
    public String xmlToJson(final String xml) {
        if (xml == null) {
            return null;
        }
        try {
            String xmlFiltered = xml.replaceAll("\\p{C}", "");
            JSONObject xmlJSONObj = XML.toJSONObject(xmlFiltered);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            return jsonPrettyPrintString;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "172.19.182.0:9092");
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Tạo Kafka Producer
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        BytesToJsonUdf udf = new BytesToJsonUdf();
        String xmlString = "\u0006<row id=\"USD121150001\"> <c2>12115</c2> <c3>Travel</c3> <c5>Travel</c5> <c6>TRAVE12115</c6> <c7>TR</c7> <c8>USD</c8> <c9>1</c9> <c11>1</c11> <c76>NO</c76> <c78>19980723</c78> <c85>12115</c85> <c93>USD</c93> <c94>1</c94> <c95>USD</c95> <c96>1</c96> <c99>LEGACY</c99> <c108>NO</c108> <c214>1</c214> <c215 m=\"2\">213_CONV.ACCOUNT.201405</c215><c215 m=\"3\">213_CONV.ACCOUNT.201406</c215><c215 m=\"4\">213_CONV.ACCOUNT.201407</c215><c216>1704062201</c216><c217>78671_INPUTTER_OFS_MB.OFS.AUTH</c217><c218>GB0010001</c218><c219>1</c219></row>\n";
        String jsonString = udf.xmlToJson(xmlString);
        System.out.println(jsonString);

        long totalProcessingTime = 0;

        // Gửi 1000 message
        for (int i = 0; i < 1000; i++) {
            // Tạo ProducerRecord
            ProducerRecord<String, String> record = new ProducerRecord<>("abc1", jsonString);

            try {
                long startTime = System.currentTimeMillis();

//                Future<RecordMetadata> future = producer.send(record);

//                RecordMetadata metadata = future.get();

                long endTime = System.currentTimeMillis();

                long processingTime = endTime - startTime;
                totalProcessingTime += processingTime;

//                System.out.println("process kafka: " + processingTime + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        double averageProcessingTime = (double) totalProcessingTime / 1000;
//        System.out.println("Average processing time for each record: " + averageProcessingTime + " ms");

//        producer.close();
    }
}
