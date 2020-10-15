package cn.enjoyedu.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步发送
 * 异步传输通常用于响应时间敏感的业务场景
 */
public class AsyncProducer {


    public static void main(String[] args) throws Exception {
        //使用生产者组名称实例化
        DefaultMQProducer producer = new DefaultMQProducer("asyn");
        //Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //启动实例
        producer.start();
        //失败重试机制，0：不重试，1：重试一次
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for(int i = 0;i < messageCount;i++){
            try{
                final int index = i;
                final Message message = new Message("TopicTest","TagC","OrderId" + index,("Hello World " + index).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //生产者异步发送
                producer.send(message, new SendCallback() {
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, new String(message.getBody()));
                    }

                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
