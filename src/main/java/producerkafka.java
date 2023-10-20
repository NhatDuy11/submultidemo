import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class producerkafka {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.19.182.0:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        //If the request fails, the producer can automatically retry,
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //Reduce the no of requests less than 0
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);
        String topic = "xml1";
        String xmlString = "\u0006<row id=\"USD121150001\"> <c2>12115</c2> <c3>Travel</c3> <c5>Travel</c5> <c6>TRAVE12115</c6> <c7>TR</c7> <c8>USD</c8> <c9>1</c9> <c11>1</c11> <c76>NO</c76> <c78>19980723</c78> <c85>12115</c85> <c93>USD</c93> <c94>1</c94> <c95>USD</c95> <c96>1</c96> <c99>LEGACY</c99> <c108>NO</c108> <c214>1</c214> <c215 m=\"2\">213_CONV.ACCOUNT.201405</c215><c215 m=\"3\">213_CONV.ACCOUNT.201406</c215><c215 m=\"4\">213_CONV.ACCOUNT.201407</c215><c216>1704062201</c216><c217>78671_INPUTTER_OFS_MB.OFS.AUTH</c217><c218>GB0010001</c218><c219>1</c219></row>\n";


        StringBuilder sbt = new StringBuilder();
        for (int i = 0 ; i <1000;i++){
            sbt.append(xmlString);
            producer.send(new ProducerRecord<String,String>(topic,"key",xmlString));


        }
        producer.close();
    }
}
