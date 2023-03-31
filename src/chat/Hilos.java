/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

/**
 *
 * @author pablonoguera
 */

public class Hilos extends Thread {

    
    int k;
    Hilos(int i){
    this.k = i;
    
    }
    @Override
    public void run(){
        System.out.println("Proceso: " + k);
        if(k > 1){
        try {
            System.out.println("....");
            sleep(1000);
        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
    }
    
 
    public static void main(String[] args) {
        System.out.println("Primer mensaje");
        Hilos p1 = new Hilos(1);
        Hilos p2 = new Hilos(2);
        Hilos p3 = new Hilos(3);
        p1.start();
        p2.start();
        p3.start();
        System.out.println("Ultimo mensaje");
    }
    
}
