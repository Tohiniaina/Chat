package Listen;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ecoute implements ActionListener {
    JButton buttonConnecter;
    JButton sendMessage;
    JButton File;
    JTextField textFieldHost;
    JTextField textFieldPort;
    JTextField textFieldNom;
    JTextField mess;
    DefaultListModel<String> model;
    boolean isConnected;

    int etat = 0;
    PrintWriter pw;

    public JButton getFile() {
        return File;
    }

    public void setFile(JButton file) {
        File = file;
    }

    public JTextField getTextFieldNom() {
        return textFieldNom;
    }

    public void setTextFieldNom(JTextField textFieldNom) {
        this.textFieldNom = textFieldNom;
    }

    public JTextField getMess() {
        return mess;
    }

    public void setMess(JTextField mess) {
        this.mess = mess;
    }

    public JButton getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(JButton sendMessage) {
        this.sendMessage = sendMessage;
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public void setModel(DefaultListModel<String> model) {
        this.model = model;
    }

    public JButton getButtonConnecter() {
        return buttonConnecter;
    }

    public void setButtonConnecter(JButton buttonConnecter) {
        this.buttonConnecter = buttonConnecter;
    }

    public JTextField getTextFieldHost() {
        return textFieldHost;
    }

    public void setTextFieldHost(JTextField textFieldHost) {
        this.textFieldHost = textFieldHost;
    }

    public JTextField getTextFieldPort() {
        return textFieldPort;
    }

    public void setTextFieldPort(JTextField textFieldPort) {
        this.textFieldPort = textFieldPort;
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (this.buttonConnecter == btn && etat != 1) {
            this.etat = 1;
            String host = textFieldHost.getText();
            int port = Integer.parseInt(textFieldPort.getText());
            String nom = textFieldNom.getText();
            try {
                Socket socket = new Socket(host,port);
                InputStream inputStream = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(isr);
                pw = new PrintWriter(socket.getOutputStream(), true);
                pw.println(nom);
                new Thread(()->{
                    try {
                        while(true) {
                            String response = bufferedReader.readLine();
                            model.addElement(response);
                        }
                    } catch (IOException erro) {
                        erro.printStackTrace();
                    }
                }).start();
                isConnected = true;
            } catch (IOException er) {
                er.printStackTrace();
            }
        }
        if (this.sendMessage == btn && isConnected == true) {
            String message = mess.getText();
            pw.println(message);
        }

        if (this.File == btn && isConnected == true) {
            System.out.println("File");
            JFileChooser choose = new JFileChooser();

            choose.setDialogTitle("Selectionner un Fichier");
            choose.setAcceptAllFileFilterUsed(true);

            int res = choose.showOpenDialog(null);

            File file = null;

            if (res == JFileChooser.APPROVE_OPTION) {
                file = choose.getSelectedFile();
            }

            String host = textFieldHost.getText();
            int port = Integer.parseInt(textFieldPort.getText());
            String nom = textFieldNom.getText();
        
            try {
                Socket socket = new Socket(host,port);
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String filename = file.getName();
                byte[] filenameBytes = filename.getBytes();

                byte[] fileContentBytes = new byte[(int)file.length()];
                fileInputStream.read(fileContentBytes);

                dataOutputStream.writeInt(filenameBytes.length);
                dataOutputStream.write(filenameBytes);

                dataOutputStream.writeInt(fileContentBytes.length);
                dataOutputStream.write(fileContentBytes);
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

}
