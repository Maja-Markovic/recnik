/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author racunar
 */
public class KlijentskiZahtev implements Serializable {
    private int operacija;
    private Object param;

    public KlijentskiZahtev() {
    }

    public KlijentskiZahtev(int operacija, Object param) {
        this.operacija = operacija;
        this.param = param;
    }

    public int getOperacija() {
        return operacija;
    }

    public void setOperacija(int operacija) {
        this.operacija = operacija;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
    
    
}
