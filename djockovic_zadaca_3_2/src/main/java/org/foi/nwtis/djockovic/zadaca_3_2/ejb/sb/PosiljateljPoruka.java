package org.foi.nwtis.djockovic.zadaca_3_2.ejb.sb;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;

/**
 * Provođenje poslovnih aktivnosti za slanje JMS poruke u red čekanja
 * @author Denis Jocković
 */
@Stateless
public class PosiljateljPoruka {

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_DZ3")
    private JMSContext context;
    
    @Resource(lookup = "jms/NWTiS_DZ3")
    Queue requestQueue;
    
    public void saljiPoruku (String textPoruke) {
        TextMessage poruka = context.createTextMessage(textPoruke);
        context.createProducer().send(requestQueue, poruka);
    }
}
