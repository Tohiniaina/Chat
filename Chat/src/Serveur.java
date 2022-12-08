package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

import Serveur.Conversation;

public class Serveur extends Thread {
    private boolean isActive=true;
    private int nombreClients=0;
    private List<Conversation> clients = new ArrayList<Conversation>();
    public static void main(String[] args) {
        new Serveur().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (isActive) {
                Socket socket = serverSocket.accept();
                InputStreamReader in = new InputStreamReader(socket.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                String nom = bf.readLine();
                ++nombreClients;
                Conversation conversation = new Conversation(socket,nombreClients,clients,nom);
                clients.add(conversation);
                conversation.start();
                for(Conversation Client : clients) {
                    System.out.println(Client.nom);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}