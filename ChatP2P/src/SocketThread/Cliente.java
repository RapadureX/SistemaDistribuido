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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import Util.PegarTexto;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {

    String ip;
    int porta;
    Socket cliente;
    PegarTexto captura;
    PrintStream saidaCliente = null;

    public Cliente(String ip, int porta, PegarTexto captura) {
        this.ip = ip;
        this.porta = porta;
        this.captura = captura;
    }

    @Override
    public void run() {
        try {
            cliente = new Socket(ip, porta);
            captura.escrever("Conectado ao servidor " + ip + " com sucesso!\n");
            executarCliente();

        } catch (Exception ex) {
            captura.escrever("Erro ao tentar conectar ao servidor!");
        }
    }

    private void executarCliente() {

        while (true) {
            InputStream msg;
            try {
                saidaCliente = new PrintStream(cliente.getOutputStream());
                msg = cliente.getInputStream();
                Scanner lerDoSocketServidor = new Scanner(msg);
                while (lerDoSocketServidor.hasNextLine()) {
                    captura.escrever("Remoto -> " + lerDoSocketServidor.nextLine()+ "\n");
                }

            } catch (IOException | NullPointerException e) {
                break;
            }
        }

    }
    
    public Socket getCliente(){
        return cliente;
    }

    
    public void enviar(String msg) {
        try {
            saidaCliente = new PrintStream(cliente.getOutputStream());
            if (saidaCliente != null) {
                saidaCliente.println(msg);
                captura.escrever("Você -> " + msg +"\n");
            }
        } catch (IOException | NullPointerException ex) {
            captura.escrever("Não foi possivél enviar a mensagem!");
        }
    }

}


