package cn.enjoyedu.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * rocketMq 单向发送
 * 单向传输用于需要中等可靠性的情况，例如日志收集。
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        //生产者实例化
        DefaultMQProducer producer = new DefaultMQProducer("oneway");
        //指定rocketmq服务器地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动实例
        producer.start();

        for(int i = 0;i < 10; i++){
            //创建一个消息实例，指定topic tag和消息体
            /**
             * tags标签：二级消息主题
             */
            Message message = new Message("TopicTest","TagA",("Hello RocketMq " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息
            producer.sendOneway(message);
            //打印出发送的消息体
            System.out.printf("%s%n",new String(message.getBody()));
        }
        //关闭
        producer.shutdown();
    }
}
