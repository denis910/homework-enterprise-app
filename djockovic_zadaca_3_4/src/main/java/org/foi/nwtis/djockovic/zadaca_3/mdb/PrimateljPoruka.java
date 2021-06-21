package org.foi.nwtis.djockovic.zadaca_3.mdb;

import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.djockovic.zadaca_3.wsep.OglasnikAerodroma;

/**
 * Primanje poruke iz reda čekanja i slanje oglasniku
 * @author Denis Jocković
 */
@MessageDriven(mappedName = "jms/NWTiS_DZ3")
public class PrimateljPoruka implements MessageListener {

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_DZ3")
    private JMSContext context;
    
    @Inject
    OglasnikAerodroma oglasnikAerodroma;

    public void onMessage(Message msg) {
        String poruka;
        try {
            poruka = ((TextMessage) msg).getText();
            oglasnikAerodroma.posaljiPoruku(poruka);
        } catch (JMSException ex) {
            Logger.getLogger(PrimateljPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
