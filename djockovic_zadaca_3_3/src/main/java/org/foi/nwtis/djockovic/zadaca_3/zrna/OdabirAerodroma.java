package org.foi.nwtis.djockovic.zadaca_3.zrna;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb.Meteorolog;
import org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb.OsobniAerodromi;
import org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb.PosiljateljPoruka;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.podaci.Korisnik;
import org.foi.nwtis.rest.podaci.Lokacija;

/**
 * Zrno za odabirAerodroma.xhtml
 * @author Denis Jocković
 */
@Named(value = "odabirAerodroma")
@ViewScoped
public class OdabirAerodroma implements Serializable {

    @EJB
    OsobniAerodromi osobniAerodromi;

    @Getter
    @Setter
    private String nazivAerodroma;
    @Getter
    @Setter
    private String odabraniAerodrom;
    @Getter
    @Setter
    private String korisnik;
    @Getter
    @Setter
    private List<Aerodrom> sviAerodromi = new ArrayList<>();
    
    private List<Korisnik> sviKorisnici = new ArrayList<>();

    public OdabirAerodroma() {
    }
    
    public List<Korisnik> getSviKorisnici(){
        sviKorisnici = osobniAerodromi.vratiKorisnike();
        return sviKorisnici;
    }
    
    public String filtrirajAerodrome() {
        sviAerodromi = osobniAerodromi.filtrirajAerodrome(nazivAerodroma);
        return "";
    }

    public String dodajAerodrom() {
        for (Aerodrom a : sviAerodromi) {
            if (a.getIcao().compareTo(odabraniAerodrom) == 0) {
                osobniAerodromi.dodajAerodrom(a, korisnik, nazivAerodroma);
                break;
            }
        }
        filtrirajAerodrome();
        return "";
    }

    public String obrisiAerodrome() {
        osobniAerodromi.obrisiAerodrome();
        filtrirajAerodrome();
        return "";
    }
}
