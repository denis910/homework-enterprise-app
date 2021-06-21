package org.foi.nwtis.djockovic.zadaca_3.wsep;

import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Prosljeđivanje JMS poruka svim aktivnim sjednicama
 * @author Denis Jocković
 */
@ServerEndpoint("/ws")
public class InfoAerodroma {

    @OnMessage
    public void stiglaPoruka(String message, Session session) {
        try {
            for (Session sess : session.getOpenSessions()) {
                if (sess.isOpen()) {
                    sess.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(InfoAerodroma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
