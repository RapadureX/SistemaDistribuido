/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketThread;

import Util.PegarTexto;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Família Miguel
 */
public class TratadorDeMensagens extends Thread{
    
    private Socket entrada; 
    private PegarTexto captura;
    PrintStream saidaCliente = null;
    
    public TratadorDeMensagens(Socket entrada, PegarTexto captura) {
        this.entrada = entrada;
        this.captura = captura;  
    }

    @Override
    public void run() {
        while (true) {
            InputStream in ;
            try {
                
                in = entrada.getInputStream();
                Scanner lerDoSocket = new Scanner(in);
                while (lerDoSocket.hasNextLine()) {
                    captura.escrever("Cliente [ " + entrada.getInetAddress().getHostAddress() + " ] -> " + lerDoSocket.nextLine() + "\n");
                }

            } catch (IOException ex1) {
                break;
            }
        }
    }
}
    


