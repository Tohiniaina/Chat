package Client;

import java.awt.*;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.html.ListView;

import Listen.Ecoute;

public class Client extends JFrame {
    Ecoute ecoute;

    public class RoundBtn implements Border {
        int r;
    
        RoundBtn(int r) {
            this.r = r;
        }
    
        public Insets getBorderInsets(Component c) {
            return new Insets(this.r+1, this.r+1, this.r+2, this.r);
        }
    
        public boolean isBorderOpaque() {
            return true;
        }
    
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, r, r);
        }
    }
    public void generateAffi() {
        JFrame frame = new JFrame("Chat Client");
        JPanel panel = new JPanel();
        panel.setFocusable(true);
        panel.setBackground(Color.RED);
        panel.setBounds(0, 0, 600, 40);
        JLabel labelHost = new JLabel("Host :");
        labelHost.setBounds(10, 5, 50, 30);
        JTextField textFieldHost = new JTextField("localhost");
        textFieldHost.setBounds(55, 5, 100, 30);
        JLabel labelPort = new JLabel("Port :");
        labelPort.setBounds(170, 5, 100, 30);
        JTextField textFieldPort = new JTextField("1234");
        textFieldPort.setBounds(210, 5, 100, 30);
        JLabel labelNom = new JLabel("Nom : ");
        labelNom.setBounds(320, 5, 50, 30);
        JTextField textFieldNom = new JTextField("");
        textFieldNom.setBounds(360, 5, 100, 30);
        
        DefaultListModel<String> model = new DefaultListModel<String>();
        JList<String> list = new JList<String>(model);
        list.setBounds(5, 45, 575, 250); 
        list.setBorder(new RoundBtn(5));

        frame.add(list);

        ecoute = new Ecoute();

        JPanel panelSendMessage = new JPanel();
        panelSendMessage.setBounds(5, 300, 575, 100);
        panelSendMessage.setBorder(new RoundBtn(10));
        panelSendMessage.setFocusable(true);
        JLabel label = new JLabel("Message : ");
        label.setBounds(10,5,100,20);
        JTextField Mess = new JTextField();
        Mess.setBounds(80, 5, 260, 30);
        JButton send = new JButton("Send");
        send.setBounds(350,5,80,30);

        JButton sendFile = new JButton("File");
        sendFile.setBounds(440, 5, 80, 30);
        sendFile.addActionListener(ecoute);

        JLabel guide = new JLabel("NB : Raha te handefa message prive @ client iray : numClient=>Message");
        guide.setBounds(10,50,450,30);
        
        panelSendMessage.add(guide);
        panelSendMessage.add(label);
        panelSendMessage.add(Mess);
        panelSendMessage.add(send);
        panelSendMessage.setLayout(null);
        send.addActionListener(ecoute);
        ecoute.setSendMessage(send);
        ecoute.setMess(Mess);

        JButton buttonConnecter = new JButton("Connecter");
        buttonConnecter.setBounds(475, 5, 100, 30);
        buttonConnecter.addActionListener(ecoute);
        buttonConnecter.setBorder(new RoundBtn(15));


        ecoute.setButtonConnecter(buttonConnecter);
        ecoute.setTextFieldHost(textFieldHost);
        ecoute.setTextFieldPort(textFieldPort);
        ecoute.setTextFieldNom(textFieldNom);
        ecoute.setModel(model);
        
        panel.add(labelHost);
        panel.add(textFieldHost);
        panel.add(labelPort);
        panel.add(textFieldPort);
        panel.add(labelNom);
        panel.add(textFieldNom);
        panel.add(buttonConnecter);
        panel.setLayout(null);

        frame.add(panel);
        frame.add(panelSendMessage);


        frame.setSize(600, 440);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.generateAffi();
    }

}
