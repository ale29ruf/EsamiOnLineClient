package strategyvisualizer;

import protoadapter.Model;

import javax.swing.*;

/**
 * Interfaccia che le varie strategie di visualizzazione usate all'interno dell'applicazione devono realizzare.
 */
public interface Strategy {

    // source -> contenitore dei dati da visualizzare
    JComponent proietta(Model source, JComponent destination);

}
