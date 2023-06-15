package mediator;


import javax.swing.*;
import java.util.List;

public interface Mediatore {

    void caricaAppelli();

    void registraStudente(JButton widget);

    void partecipaAppello(JButton widget);

    void comunicaRisposte(List<Integer> risposte);

}
