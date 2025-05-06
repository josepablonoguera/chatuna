/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

/**
 *
 * @author pablonoguera
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor extends Thread {

    public static Vector usuarios = new Vector();
    


    public static void main(String args[]) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8000);

            System.out.println("Servidor Activo....");
        } catch (IOException ioe) {
            System.out.println("Comunicación rechazada." + ioe);
            System.exit(1);
        }
        System.out.println("Esperando .....");

        while (true) {
            try {
                Socket socket = server.accept();
                DataInputStream FlujoLectura = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                String nombre = FlujoLectura.readUTF();
                System.out.println("Conexión aceptada de: " + nombre);
                Flujo flujo = new Flujo(socket);
                Thread t = new Thread(flujo);
                t.start();
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }
    }
}
