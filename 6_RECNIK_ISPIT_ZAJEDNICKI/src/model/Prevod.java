/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author racunar
 */
public class Prevod implements Serializable {
    private int id;
    private Rec srpskaRec;
    private String engleskaRec;

    public Prevod() {
    }

    public Prevod(int id, Rec srpskaRec, String engleskaRec) {
        this.id = id;
        this.srpskaRec = srpskaRec;
        this.engleskaRec = engleskaRec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rec getSrpskaRec() {
        return srpskaRec;
    }

    public void setSrpskaRec(Rec srpskaRec) {
        this.srpskaRec = srpskaRec;
    }

    public String getEngleskaRec() {
        return engleskaRec;
    }

    public void setEngleskaRec(String engleskaRec) {
        this.engleskaRec = engleskaRec;
    }

    
    
}
