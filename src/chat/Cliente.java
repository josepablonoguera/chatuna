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
import java.awt.*;
import java.awt.event.*;

public class Cliente extends Frame implements ActionListener {

    static Socket socket = null;
    static DataInputStream EntradaSocket;
    static DataOutputStream SalidaSocket;
    static TextField salida;
    static TextArea entrada;
    static String texto;
   static  String name;
    

    public Cliente() {
        name= "No hay usuario";
        setTitle("Chat");
        setSize(350, 200);
        salida = new TextField(30);
        salida.addActionListener(this);

        entrada = new TextArea();
        entrada.setEditable(false);

        add("South", salida);
        add("Center", entrada);
        setVisible(true);
    }

    public static void main(String[] args)throws IOException{
        Cliente cliente = new Cliente();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            socket = new Socket("localhost", 8000); // 172.20.10.5 : 8000
            EntradaSocket = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            SalidaSocket = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println("Ingrese su nombre: ");
                 name = br.readLine();
                 System.out.println("----");
                SalidaSocket.writeUTF(name);
                SalidaSocket.flush();
          
        } catch (UnknownHostException uhe) {
            System.out.println("No se puede acceder al servidor.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Comunicación rechazada.");
            System.exit(1);
        }
        while (true) {
            try {
                String linea = EntradaSocket.readUTF();
                entrada.append(linea + "\n");
            } catch (IOException ioe) {
                System.exit(1);
            }

        }
    }

    public void actionPerformed(ActionEvent e) {
        texto = salida.getText();
        salida.setText("");
        try {
            SalidaSocket.writeUTF(name+": " + texto);
            SalidaSocket.flush();
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    public boolean handleEvent(Event e) {
        if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    System.out.println("Error: " + ioe);
                }
                this.dispose();
            }
        }
        return true;
    }
}
