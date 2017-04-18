package Swing;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;

public class MessageReceiver implements Runnable {
    private String url;
    private String user;
    private String password;
    private final String QUEUE;
    private JTextArea textArea;
 
    public MessageReceiver(String queue, String url, String user, String password,JTextArea textArea) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.QUEUE = queue;
        this.textArea = textArea;
    }
 
    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                user, password, url);
        Session session = null;
        Destination receiveQueue;
        try {
            Connection connection = connectionFactory.createConnection();
 
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            receiveQueue = session.createQueue(QUEUE);
            MessageConsumer consumer = session.createConsumer(receiveQueue);
 
            connection.start();
 
            while (true) {
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage receiveMessage = (TextMessage) message;
                    System.out.println("Get message : \r" + receiveMessage.getText()+"\n");
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                textArea.append("receive : "+ receiveMessage.getText()+"\n");;
                            } catch (JMSException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    session.commit();
                } else {
                    session.commit();
                    break;
                }
            }
            session.close();
            connection.close();
        } catch (JMSException e) {
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