package org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.zadaca_3.AirportsDAO;
import org.foi.nwtis.djockovic.zadaca_3.KorisnikDAO;
import org.foi.nwtis.djockovic.zadaca_3.MyAirportsDAO;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;

/**
 * Provođenje poslovnih aktivnosti povezanih s poslovnim slojem
 * @author Denis Jocković
 */
@Stateful
public class OsobniAerodromi {

    @EJB
    BankaAerodroma bankaAerodroma;

    @Resource(lookup = "jdbc/NWTiS_DZ3")
    javax.sql.DataSource nwtisBP;

    /**
     * Funkcija koja poziva funkcije za upis aerodroma u tablicu i aplikacijsku evidenciju i filtriranje aerodroma
     * @param aerodrom aerodrom koji se dodaje u aplikacijsku evidenciju i u bazu
     * @param korisnik korisnik koji kreće pratiti aerodrom
     * @param nazivAerodroma Dio naziva aerodroma koji se traže
     */
    public void dodajAerodrom(Aerodrom aerodrom, String korisnik, String nazivAerodroma) {
        bankaAerodroma.preuzmiAerodrom(aerodrom);
        try {
            MyAirportsDAO mdao = new MyAirportsDAO();
            if(mdao.dohvatiAerodromeKorisnika(aerodrom, nwtisBP.getConnection(), korisnik)){
                mdao.dodajAerodrom(aerodrom, nwtisBP.getConnection(), korisnik);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OsobniAerodromi.class.getName()).log(Level.SEVERE, null, ex);
        }
        filtrirajAerodrome(nazivAerodroma);
    }

    public void obrisiAerodrome() {
        bankaAerodroma.obrisiAerodrom();
    }

    public int brojAerodroma() {
        return bankaAerodroma.brojAerodroma();
    }

    public List<Aerodrom> vratiAerodrome() {
        return bankaAerodroma.getSviAerodromi();
    }
    
    /**
     * Funkcija koja iz baze vraća se aerodrome koji imaju nazivAerodroma kao dio svog naziva
     * @param nazivAerodroma Dio naziva aerodroma koji se traže
     * @return Lista aerodroma filtriranih po nazivu
     */
    public List<Aerodrom> filtrirajAerodrome(String nazivAerodroma) {
        AirportsDAO adao = new AirportsDAO();
        List<Aerodrom> aerodromi;
        try {
            List<Aerodrom> listaBankovnihAerodroma = bankaAerodroma.getSviAerodromi();
            aerodromi = adao.dajSveAerodromeNaziv(nwtisBP.getConnection(), nazivAerodroma);
            if(aerodromi == null)
                return new ArrayList<>();
            for (int i = 0; i < aerodromi.size(); i++) {
                if(listaBankovnihAerodroma != null) {
                    for (Aerodrom upisanAerodrom : listaBankovnihAerodroma) {
                        if (upisanAerodrom.getIcao().equals(aerodromi.get(i).getIcao())) {
                            aerodromi.remove(aerodromi.get(i));
                            break;
                        }
                    }
                }
            }
            return aerodromi;
        } catch (SQLException ex) {
            Logger.getLogger(OsobniAerodromi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public List<Korisnik> vratiKorisnike() {
        KorisnikDAO kdao = new KorisnikDAO();
        try {
            return kdao.dohvatiSveKorisnike(nwtisBP.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(OsobniAerodromi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
