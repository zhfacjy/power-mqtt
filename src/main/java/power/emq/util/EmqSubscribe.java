package power.emq.util;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by 浩发 on 2019/2/3 21:35
 * 订阅emq消息
 */
@Component
public class EmqSubscribe implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Value("${mqtt.url}")
    private String mqUrl;
    @Value("${mqtt.clean-start}")
    private Boolean cleanStart;
    @Value("${mqtt.client-id}")
    private String clientId;
    @Value("${mqtt.reconnection-delay}")
    private Long reconnectionDelay;
    @Value("${mqtt.reconnection-attempt_max}")
    private Long reconnectionAttemptMax;
    @Value("${mqtt.keep-alive}")
    private Short keepAlive;

//    private final BloomFilter<String> bloomFilter = BloomFilter.create(new Funnel<String>() {
//
//        private static final long serialVersionUID = 1L;
//
//        @Override
//        public void funnel(String arg0, PrimitiveSink arg1) {
//            arg1.putString(arg0, Charsets.UTF_8);
//        }
//
//    }, 1024*1024);

    @Autowired
    private KafkaSender<String> kafkaSender;

    @Override
    public void run(ApplicationArguments args) {
        try {
            // 创建MQTT对象
            MQTT mqtt = new MQTT();
            // 设置mqtt broker的ip和端口
            mqtt.setHost(mqUrl);
            // 连接前清空会话信息
            mqtt.setCleanSession(cleanStart);
            // 设置重新连接的次数
            mqtt.setReconnectAttemptsMax(reconnectionAttemptMax);
            // 设置重连的间隔时间
            mqtt.setReconnectDelay(reconnectionDelay);
            // 设置心跳时间
            mqtt.setKeepAlive(keepAlive);
            //设置客户端id
            mqtt.setClientId(clientId);
            mqtt.setUserName(MessageQueueConfig.EMQ_USERNAME);
            mqtt.setPassword(MessageQueueConfig.EMQ_PASSWORD);
            CallbackConnection connection = mqtt.callbackConnection();

            connection.listener(new Listener() {
                @Override
                public void onConnected() {
                }

                @Override
                public void onDisconnected() {
                }

                @Override
                public void onPublish(UTF8Buffer utf8Buffer, Buffer buffer, Runnable ack) {
                    //当有设备向服务已订阅的主题发送消息时,该方法会消费
                    ack.run();
                    String receiveMessage = UTF8Buffer.decode(buffer);
                    kafkaSender.send(receiveMessage);
//                    logger.info("emq消息接收:" + UTF8Buffer.decode(buffer));
//                    boolean exists = bloomFilter.mightContain(msg.getString("msg"));
//                    if (!exists) {
//                        bloomFilter.put(msg.getString("msg"));
//                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                }
            });
            connection.connect(new Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    logger.info("地址：" + mqUrl + "连接成功");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.info("地址：" + mqUrl + "连接失败");
                }
            });
            connection.subscribe(MessageQueueConfig.Topics, new Callback<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    logger.info("订阅成功");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    logger.error("订阅失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
            // 获取mqtt的连接对象BlockingConnection  ,采用Future模式 订阅主题
            final FutureConnection connection = mqtt.futureConnection();
            connection.connect();
            connection.subscribe(topics);
            while (true) {
                Future<Message> futrueMessage = connection.receive();
                Message message = futrueMessage.await();
                kafkaSender.send(JSONObject.parseObject(UTF8Buffer.decode(message.getPayloadBuffer())));
            }
    */
}
