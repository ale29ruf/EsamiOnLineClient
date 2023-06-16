package mediator;


import proto.Remotemethod;

import javax.swing.*;
import java.util.List;

public interface Mediatore {

    void caricaAppelli();

    void registraStudente(JButton widget);

    void partecipaAppello(JButton widget);

    void comunicaRisposte(List<Remotemethod.Risposta> risposte);

}
