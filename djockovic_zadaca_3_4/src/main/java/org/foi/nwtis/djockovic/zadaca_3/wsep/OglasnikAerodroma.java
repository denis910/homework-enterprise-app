package org.foi.nwtis.djockovic.zadaca_3.wsep;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa koja šalje poruku na server endpoint
 * @author Denis Jocković
 */
@ClientEndpoint
public class OglasnikAerodroma {
    
    Session session;
    
    /**
     * Funkcija u kojoj se definira sesija
     */
    @PostConstruct
    public void spajanje() {
        String uri = "ws://localhost:8380/djockovic_zadaca_3_3/ws";
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(uri));
        } catch (IOException | URISyntaxException | DeploymentException ex) {
            Logger.getLogger(OglasnikAerodroma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void posaljiPoruku(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getLogger(OglasnikAerodroma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnMessage
    public void stiglaPoruka(String message, Session session) {
    }
}
