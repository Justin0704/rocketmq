package cn.enjoyedu.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步发送
 *
 * 可靠的同步传输应用于广泛的场景，如重要的通知消息、短信通知、短信营销系统等。。
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {

        //创建生产者实例对象
        DefaultMQProducer producer = new DefaultMQProducer("sync");
        //链接指定的rocketMq服务器地址
        producer.setNamesrvAddr("localhost:9876");
        //启动实例
        producer.start();

        for(int i = 0;i < 10; i++){
            //创建一个消息实例，指定topic tag和消息体
            Message message = new Message("TopicTest","TagB",("Hello RocketMq " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //调用send message将消息传递给一个代理
            SendResult sendResult = producer.send(message);
            /**
             * MsgId: rocketMq 全局唯一标志（内部机制ID 机器IP+消息偏移量）
             * queue（在一个主题中有多个，默认有4个），queueId的值是（0/1/2/3）
             */
            System.out.printf("%s%n%n%n",sendResult.getSendStatus()
                    + ":(MsgId):" + sendResult.getMsgId()
                    + ":(queueId):" + sendResult.getMessageQueue().getQueueId()
                    + ":(value):" + new String(message.getBody()));
        }
        //一旦生产者实例不再使用，就关闭它
        producer.shutdown();
    }
}
