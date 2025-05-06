/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

/**
 *
 * @author pablonoguera
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class Flujo extends Thread {

    Socket socket;
    DataInputStream FlujoLectura;
    DataOutputStream FlujoEscritura;

    public Flujo(Socket socketEntrada) {
        socket = socketEntrada;
        try {
            FlujoLectura = new DataInputStream(new BufferedInputStream(socketEntrada.getInputStream()));
            FlujoEscritura = new DataOutputStream(new BufferedOutputStream(socketEntrada.getOutputStream()));
        } catch (IOException ioe) {
            System.out.println("IOException(Flujo): " + ioe);
        }
    }

    public void run() {
        broadcast(socket.getInetAddress() + "> se ha conectado");
        Servidor.usuarios.add((Object) this);
        while (true) {
            try {
                String linea = FlujoLectura.readUTF();

              
                    broadcast(linea);
                
                
                

            } catch (IOException ioe) {
                Servidor.usuarios.removeElement(this);
                broadcast(socket.getInetAddress() + "> se ha desconectado");
                break;
            }
        }
    }

    public void broadcast(String mensaje) {
        synchronized (Servidor.usuarios) {
            Enumeration e = Servidor.usuarios.elements();
            while (e.hasMoreElements()) {
                Flujo f = (Flujo) e.nextElement();

                try {
                    synchronized (f.FlujoEscritura) {
                        f.FlujoEscritura.writeUTF(mensaje);
                        f.FlujoEscritura.flush();
                    }
                } catch (IOException ioe) {
                    System.out.println("Error: " + ioe);
                }
            }
        }
    }
}
