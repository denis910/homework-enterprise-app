package org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.foi.nwtis.podaci.Aerodrom;

/**
 * Provođenje poslovnih aktivnosti povezanih sa aplikacijskom evidencijom aerodroma
 * @author Denis Jocković
 */
@Startup
@Singleton
public class BankaAerodroma {

    @Getter
    private List<Aerodrom> sviAerodromi = new ArrayList<Aerodrom>();
    
    @EJB
    PosiljateljPoruka posiljateljPoruka;

    public void preuzmiAerodrome(List<Aerodrom> aerodromi) {
        this.sviAerodromi.addAll(aerodromi);
        posiljateljPoruka.saljiPoruku("Broj aerodroma: " + brojAerodroma());
    }

    public void preuzmiAerodrom(Aerodrom aerodrom) {
        this.sviAerodromi.add(aerodrom);
        posiljateljPoruka.saljiPoruku("Broj aerodroma: " + brojAerodroma());
    }

    public void obrisiAerodrom() {
        this.sviAerodromi.clear();
        posiljateljPoruka.saljiPoruku("Broj aerodroma: " + brojAerodroma());
    }

    public int brojAerodroma() {
        return this.sviAerodromi.size();
    }
}
