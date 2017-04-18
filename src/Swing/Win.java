package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by rain on 2017/4/7.
 */
public class Win {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Win");
        frame.setContentPane(new Win().jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JTextArea textArea;
    private JButton send;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField sendText;
    private JPanel jpanel;
    private JButton startButton;
    public StringBuffer text;
    public  String user;
    public  String targe;
    private  String url = "tcp://localhost:61616";
    public Win() {
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = null;
                String str  = sendText.getText();
                textArea.append(user+" : "+str+"\n");
                MessageSender messageSender = new MessageSender(targe,url,user,password,str);
                new Thread(messageSender).start();
            }
        });


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = textField1.getText();
                targe = textField2.getText();
                new Thread(new MessageReceiver(user,url,null,null,textArea)).start();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
