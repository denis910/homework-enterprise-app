package org.foi.nwtis.djockovic.zadaca_3.slusaci;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.vjezba_03.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.KonfiguracijaBP;
import org.foi.nwtis.djockovic.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

/**
 * Dohvaćanje konfiguracijskih podataka
 * @author Denis Jocković
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {
    
    @Override
    public void contextDestroyed(ServletContextEvent sce){
        ServletContext servletContext = sce.getServletContext();
        servletContext.removeAttribute("Postavke");
    }
    
    /**
     * Učitavanje konfiguracijskih podataka u servlet context
     * @param sce događaj po kojem se poziva funkcija
     */
    @Override
    public void contextInitialized(ServletContextEvent sce){
        ServletContext servletContext = sce.getServletContext();
        String putanjaKonfDatoteke = servletContext.getRealPath("WEB-INF")+File.separator+servletContext.getInitParameter("konfiguracija");
        PostavkeBazaPodataka konfBP = new PostavkeBazaPodataka(putanjaKonfDatoteke);
        
        try{
            konfBP.ucitajKonfiguraciju();
            servletContext.setAttribute("Postavke", konfBP);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
