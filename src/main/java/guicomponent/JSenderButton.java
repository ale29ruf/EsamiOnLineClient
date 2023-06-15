package guicomponent;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class JSenderButton extends JButton {
    List<Integer> listaRisposte = new LinkedList<>();

    public JSenderButton(String concludiTest) {
        super(concludiTest);
    }

    public void addRisposta(int e){
        listaRisposte.add(e);
    }

    public List<Integer> getListaRisposte(){
        return listaRisposte;
    }
}
