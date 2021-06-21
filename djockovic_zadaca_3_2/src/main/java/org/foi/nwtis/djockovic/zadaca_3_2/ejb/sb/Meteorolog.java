package org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.djockovic.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.Lokacija;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 * Dovođenje meteo podataka za aerodrom
 * @author Denis Jocković
 */
@Stateless
public class Meteorolog {

    /**
     * Funkcija koja vraća meteopodatke nekog aerodroma 
     * @param aerodrom aerodrom za kojeg se dohvaćaju meteo podaci
     * @return meteo podaci aerodroma
     */
    public MeteoPodaci dajMeteoPodatke(Aerodrom aerodrom, String kljuc) {
        OWMKlijent klijent = new OWMKlijent(kljuc);
        Lokacija lokacija = aerodrom.getLokacija();
        String latitude = lokacija.getLatitude();
        String longitude = lokacija.getLongitude();
        
        MeteoPodaci mp = klijent.getRealTimeWeather(latitude, longitude);
        return mp;
    }
}
