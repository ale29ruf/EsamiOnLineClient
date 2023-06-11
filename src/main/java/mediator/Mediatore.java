package mediator;


import javax.swing.*;

public interface Mediatore {

    void caricaAppelli();

    void interrompiCaricamento();

    void registraStudente(JButton widget);

    void partecipaAppello(JButton widget);

}
