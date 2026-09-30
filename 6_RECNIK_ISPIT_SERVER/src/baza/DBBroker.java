/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.util.ArrayList;
import java.util.List;
import model.Korisnik;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Prevod;
import model.Rec;

/**
 *
 * @author racunar
 */
public class DBBroker {

    public List<Korisnik> vratiKorisnike() throws SQLException {
        List<Korisnik> lista = new ArrayList<>();

        String upit = "SELECT * FROM USER";
        PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Korisnik k = new Korisnik();
            k.setId(rs.getInt("id"));
            k.setIme(rs.getString("ime"));
            k.setPrezime(rs.getString("prezime"));
            k.setEmail(rs.getString("email"));
            k.setLozinka(rs.getString("lozinka"));
            k.setStatus(rs.getString("status"));

            lista.add(k);
        }

        return lista;
    }

    public Korisnik login(Korisnik korisnik) throws SQLException {
        List<Korisnik> lista = vratiKorisnike();

        for (Korisnik k : lista) {
            if (k.equals(korisnik)) {
                LocalDateTime vreme = LocalDateTime.now();
                azurirajStatus(k, "prijavljen: " + vreme);
                //azuriraj status ovog korisnika ako je prijavljen
                //iako se on vraca klijentskoj formi, mada je to azuriranje manje bitno
                k.setStatus("prijavljen");
                return k;
            }
        }

        return null;
    }

    private void azurirajStatus(Korisnik k, String status) {
        try {
            String upit = "UPDATE user SET status=? WHERE id=?";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

            ps.setString(1, status);
            ps.setInt(2, k.getId());

            int uspeh = ps.executeUpdate(); //affected rows
            if (uspeh > 0) {
                Konekcija.getInstance().getConnection().commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean logout(Korisnik korisnik) {
        try {
            LocalDateTime vreme = LocalDateTime.now();
            azurirajStatus(korisnik, "odjavljen: " + vreme);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Rec> ucitajRecnik() {
        List<Rec> lista = new ArrayList<>();

        try {
            String upit = "SELECT * FROM rec";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rec r = new Rec();
                r.setId(rs.getInt("id"));
                r.setSrpskaRec(rs.getString("srpska_rec"));

                lista.add(r);
            }
            //kuca->pas->auto
            for (Rec rec : lista) {
                upit = "SELECT * FROM prevod WHERE srpska_rec_id=" + rec.getId();
                System.out.println(upit);
                ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
                rs = ps.executeQuery();

                List<Prevod> prevodi = new ArrayList<>();

                while (rs.next()) {
                    Prevod p = new Prevod();
                    p.setId(rs.getInt("id"));
                    p.setEngleskaRec(rs.getString("eng_rec"));
                    p.setSrpskaRec(rec);
                    prevodi.add(p);
                }
                rec.setPrevodi(prevodi);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public boolean unesi(Rec rec) {
        try {
            String upit = "INSERT INTO rec (srpska_rec) values (?)";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rec.getSrpskaRec());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int noviId = -1;
            if (generatedKeys.next()) {
                noviId = generatedKeys.getInt(1);
            } else {
                System.out.println("GRESKA PRILIKOM DOBIJANJA PRIMARNOG KLJUCA");
            }

            List<Prevod> lista = rec.getPrevodi();

            for (Prevod p : lista) {
                upit = "INSERT INTO prevod (srpska_rec_id, eng_rec) values (?,?)";
                ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
                ps.setInt(1, noviId);
                ps.setString(2, p.getEngleskaRec());
                ps.executeUpdate();
            }
            Konekcija.getInstance().getConnection().commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
