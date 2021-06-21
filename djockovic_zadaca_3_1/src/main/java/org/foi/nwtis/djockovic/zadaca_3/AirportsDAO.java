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
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 * Klasa koja služi za dohvaćanje podataka iz tablice o aerodromima
 *
 * @author Denis Jocković
 */
public class AirportsDAO {

    /**
     * Vraca sve aerodrome koji imaju naziv slican nazivAerodroma
     * @param con Konekcija na bazu podataka
     * @param nazivAerodroma Dio naziva aerodroma koji se traže
     * @return 
     */
    public List<Aerodrom> dajSveAerodromeNaziv(Connection con, String nazivAerodroma) {
        String upit = "SELECT * FROM airports ";
        List<Aerodrom> aerodromi = new ArrayList<>();
        if (nazivAerodroma != null && !nazivAerodroma.trim().isEmpty()) {
            upit += " WHERE NAME LIKE '" + nazivAerodroma + "'";
        }
        

        try (
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(upit)) {
            while (rs.next()){
                String icao = rs.getString("ident");
                String naziv = rs.getString("name");
                String drzava = rs.getString("iso_country");
                String koordinate = rs.getString("coordinates");
                Lokacija l = new Lokacija();
                if(koordinate != null && !koordinate.trim().isEmpty() && koordinate.contains(",")){
                    String[] k = koordinate.split(",");
                    l = new Lokacija(k[1], k[0]);
                }
                Aerodrom a = new Aerodrom(icao, naziv, drzava, l);
                
                aerodromi.add(a);
            }
            return aerodromi;
        } catch (SQLException ex) {
            Logger.getLogger(AirportsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}