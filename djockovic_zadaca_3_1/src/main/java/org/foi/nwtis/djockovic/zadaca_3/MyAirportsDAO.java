package org.foi.nwtis.djockovic.zadaca_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;

/**
 * Klasa za rad s tablicom myairports
 *
 * @author Denis Jocković
 */
public class MyAirportsDAO {

    

    

    /**
     * Lista svih aerodroma koje prati neki korisnik
     *
     * @param a Aerodrom kojeg se pregledava
     * @param con Konekcija za bazu
     * @param korisnik Korisničko ime korisnika za kojeg se dobavljaju aerodromi koje prati
     * @return istina ako korisnik ne prati aerodrom, inače laž
     */
    public boolean dohvatiAerodromeKorisnika(Aerodrom a, Connection con, String korisnik) {
        String upit = "SELECT ident FROM myairports WHERE username = ?";
        try (PreparedStatement s = con.prepareStatement(upit)) {
            
            s.setString(1, korisnik);
            ResultSet rs = s.executeQuery();
            
            while (rs.next()) {
                if(rs.getString("ident").equals(a.getIcao())){
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AirportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * Dodavanje aerodroma za praćenje nekom korisniku
     *
     * @param a Aerodrom koji se dodaje korisniku
     * @param con Konekcija za bazu
     * @param korisnik korisničko ime korisnika kojem se dodaje aerodrom
     * @return True ako je unos bio uspješan, inače false
     */
    public boolean dodajAerodrom(Aerodrom a, Connection con, String korisnik) {
        String upit = "INSERT INTO myairports (ident, username, stored) "
                + "VALUES (?, ?, CURRENT_TIMESTAMP)";
        System.out.println(korisnik);
        System.out.println(a.getIcao());

        try (PreparedStatement s = con.prepareStatement(upit)) {

            s.setString(1, a.getIcao());
            s.setString(2, korisnik);

            int brojAzuriranja = s.executeUpdate();

            return brojAzuriranja == 1;

        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
