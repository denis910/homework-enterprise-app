package org.foi.nwtis.djockovic.zadaca_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Korisnik;

/**
 * Klasa koja služi za dohvaćanje podataka iz tablice o korisnicima
 *
 * @author Denis Jocković
 */
public class KorisnikDAO {

    /**
     * Dohvaćanje svih korisnika iz baze podataka
     *
     * @param con Konekcija na bazu podataka
     * @return Svi korisnici iz baze
     */
    public List<Korisnik> dohvatiSveKorisnike(Connection con) {
        String upit = "SELECT ime, prezime, emailAdresa, korisnik, lozinka, vrijemeKreiranja, vrijemePromjene FROM korisnici";

        List<Korisnik> korisnici = new ArrayList<>();
        try (
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(upit)) {

            while (rs.next()) {
                String korisnik1 = rs.getString("korisnik");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String email = rs.getString("emailAdresa");
                Timestamp kreiran = rs.getTimestamp("vrijemeKreiranja");
                Timestamp promjena = rs.getTimestamp("vrijemePromjene");
                Korisnik k = new Korisnik(korisnik1, "******", prezime, ime, email, kreiran, promjena, 0);

                korisnici.add(k);
            }
            return korisnici;

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
