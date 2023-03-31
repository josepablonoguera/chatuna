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

public class Servidor extends Thread
{
    
  public static Vector usuarios = new Vector();
  
  
  public static void main (String args[])
  {
    ServerSocket sfd = null;
    try
    {   
      sfd = new ServerSocket(8000);
      
        System.out.println("ip: "+ sfd.getLocalPort());
    }
    catch (IOException ioe)
    {
      System.out.println("Comunicación rechazada."+ioe);
      System.exit(1);
    }

    while (true)
    {
      try
      {
        Socket nsfd = sfd.accept();
        System.out.println("Conexion aceptada de: "+nsfd.getInetAddress());
	Flujo flujo = new Flujo(nsfd);
	Thread t = new Thread(flujo);
        t.start();
      }
      catch(IOException ioe)
      {
        System.out.println("Error: "+ioe);
      }
    }
  }
}
