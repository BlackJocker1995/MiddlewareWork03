package Swing;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * 消息发送器
 * @author rain
 *
 */
public class MessageSender implements Runnable {
     
    private String url;
    private String user;
    private String password;
    private final String QUEUE;
    private String message;
 
    public MessageSender(String queue, String url, String user, String password,String message) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.QUEUE = queue;
        this.message = message;
    }
 
    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                user, password, url);
        Session session = null;
        Destination sendQueue;
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
 
            connection.start();

            session = connection.createSession(true, Session.SESSION_TRANSACTED);
 
            sendQueue = session.createQueue(QUEUE);
            MessageProducer sender = session.createProducer(sendQueue);
            TextMessage outMessage = session.createTextMessage();
            outMessage.setText(this.message);
 
            sender.send(outMessage);
 
            session.commit();
 
            sender.close();

            Thread.sleep(1000);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    public String getUrl() {
        return url;
    }
 
    public void setUrl(String url) {
        this.url = url;
    }
 
    public String getUser() {
        return user;
    }
 
    public void setUser(String user) {
        this.user = user;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
}
