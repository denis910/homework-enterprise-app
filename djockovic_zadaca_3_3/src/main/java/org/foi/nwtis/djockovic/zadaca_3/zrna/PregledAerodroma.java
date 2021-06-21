package org.foi.nwtis.djockovic.zadaca_3.zrna;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.PostavkeBazaPodataka;
import org.foi.nwtis.djockovic.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb.Meteorolog;
import org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb.OsobniAerodromi;
import org.foi.nwtis.podaci.Aerodrom;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 * Zrno za pregledAerodroma.xhtml
 * @author Denis Jocković
 */
@Named(value = "pregledAerodroma")
@ViewScoped
public class PregledAerodroma implements Serializable {

    @EJB
    OsobniAerodromi osobniAerodromi;

    @EJB
    Meteorolog meteorolog;
    
    @Inject
    private ServletContext context;

    @Getter
    @Setter
    private int brojAerodroma;

    @Getter
    @Setter
    private String odabraniAerodrom;

    @Getter
    @Setter
    private String temperatura;

    @Getter
    @Setter
    private String vlaga;

    @Getter
    @Setter
    private String tlak;

    @Getter
    @Setter
    private List<Aerodrom> evidentiraniAerodromi = new ArrayList<>();

    public PregledAerodroma() {

    }

    /**
     * Pozivanje funkcije za dohvaćanje meteo podataka i postavljanje pripadnih vrijednosti
     * @return prazan String
     */
    public String dajMeteoPodatke() {
        PostavkeBazaPodataka konfBP = (PostavkeBazaPodataka) context.getAttribute("Postavke");
        try {
            konfBP.ucitajKonfiguraciju();
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(Meteorolog.class.getName()).log(Level.SEVERE, null, ex);
        }
        String kljuc = konfBP.dajPostavku("OpenWeatherMap.apikey");
        for (Aerodrom a : evidentiraniAerodromi) {
            System.out.println(a.getIcao());
            System.out.println(odabraniAerodrom);
            if (a.getIcao().compareTo(odabraniAerodrom) == 0) {
                MeteoPodaci meteo = meteorolog.dajMeteoPodatke(a, kljuc);
                temperatura = meteo.getTemperatureValue().toString();
                vlaga = meteo.getHumidityValue().toString();
                tlak = meteo.getPressureValue().toString();
                break;
            }
        }
        return "";
    }

    public String osvjeziAerodrome() {
        System.out.println("prolaz1");
        brojAerodroma = osobniAerodromi.brojAerodroma();
        evidentiraniAerodromi = osobniAerodromi.vratiAerodrome();
        return "";
    }
    
    public void obradiPoruku(){
        System.out.println("prolaz2");
        brojAerodroma = osobniAerodromi.brojAerodroma();
        evidentiraniAerodromi = osobniAerodromi.vratiAerodrome();
    }
}
