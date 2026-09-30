/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import forme.ServerskaForma;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author racunar
 */
public class OsveziNit extends Thread {

    private ServerskaForma sf;

    public OsveziNit(ServerskaForma sf) {
        this.sf = sf;
    }
    
    
    
    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                Thread.sleep(5000); 
                sf.popuniTabeluKorisnici();
                sf.popuniTabeluRecnik();
                System.out.println("TABELA JE OSVEZENA");
            } catch (SQLException ex) {
                Logger.getLogger(OsveziNit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziNit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                    
        }
    }
    
}
