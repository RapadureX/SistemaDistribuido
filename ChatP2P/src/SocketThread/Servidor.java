/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketThread;

/**
 *
 * @author Família Miguel
 */
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import Util.PegarTexto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Servidor extends Thread {

    PegarTexto captura;
    int porta;
    ServerSocket servidor;
    PrintStream saidaServidor;
    Socket cliente;

    public Servidor(int porta, PegarTexto captura) {
        this.captura = captura;
        this.porta = porta;
    }

    public void executarServidor() {
        try {
            servidor = new ServerSocket(porta);
            captura.escrever("Servidor iniciado na porta: " + porta + "\n");
            while (true) {

                cliente = servidor.accept();
                captura.escrever("Cliente: " + cliente.getInetAddress().getHostAddress() + " conectado\n");

                saidaServidor = new PrintStream(cliente.getOutputStream());

                TratadorDeMensagens clienteinserver = new TratadorDeMensagens(cliente, captura);
                clienteinserver.start();

            }

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null,"Erro ao tentar criar o servidor!", "ERRO", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    public ServerSocket getServidor(){
        return servidor;
    }

    @Override
    public void run() {
        executarServidor();
    }

    public void enviar(String msg) {
       
        try {
            saidaServidor = new PrintStream(cliente.getOutputStream());
            saidaServidor.println(msg);
            captura.escrever("Remoto -> " + msg + "\n");
        } catch (IOException ex) {
            captura.escrever("Não foi possivél enviar a mensagem!");
        }
    }
}
