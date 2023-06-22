package guicomponent;

import proto.Remotemethod;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class JSenderButton extends JButton {
    private final List<Remotemethod.Risposta> listaRisposte = new LinkedList<>();

    public JSenderButton(String concludiTest) {
        super(concludiTest);
    }

    public void addRisposta(Remotemethod.Risposta e){
        listaRisposte.add(e);
    }

    public List<Remotemethod.Risposta> getListaRisposte(){
        return listaRisposte;
    }
}
