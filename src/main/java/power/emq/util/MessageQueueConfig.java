package power.emq.util;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

/**
 * Created by 浩发 on 2019/2/4 13:54
 * 各种变量配置
 */
public class MessageQueueConfig {

    public static final String EMQ_USERNAME = "HKDDDB";
    public static final String EMQ_PASSWORD = "txjsa-1234";

    // 订阅emq的主题
    public static final Topic[] Topics = {
            new Topic("HK1111", QoS.AT_MOST_ONCE)
    };

    // kafka的主题
    public static final String KAFKA_TOPIC = "kafka.power";
}
