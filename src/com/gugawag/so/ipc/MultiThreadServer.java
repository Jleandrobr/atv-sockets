package com.gugawag.so.ipc;

/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.21
 *
 * @author Silberschatz, Gagne, and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Ninth Edition
 * Copyright John Wiley & Sons - 2013.
 */
import java.net.*;
import java.io.*;
import java.util.Date;

public class MultiThreadServer{
    public static void main(String[] args)  {
        try {
            ServerSocket sock = new ServerSocket(6013);
            System.out.println("=== Servidor iniciado ===\n");

            while (true) {
                // Aguarda e aceita a conexão do cliente
                Socket client = sock.accept();
                // Inicia uma nova thread para lidar com a conexão do cliente
                Thread thread = new Thread(new ClientHandler(client));
                thread.start();
            }
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            System.out.println("Servidor recebeu comunicação do ip: " + client.getInetAddress() + "-" + client.getPort());
            PrintWriter pout = new PrintWriter(client.getOutputStream(), true);

            // Escreve a data atual no socket
            pout.println(new Date().toString() + "-Boa noite alunos!");

            InputStream in = client.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));

            String line = bin.readLine();
            System.out.println("O cliente me disse:" + line);

            // Fechar o socket após a comunicação
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
