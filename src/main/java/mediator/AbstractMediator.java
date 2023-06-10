package mediator;

import component.ListaAppelli;
import proto.Remotemethod;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractMediator implements Mediatore{ //Si occupa della comunicazione locale

    boolean interrupt = false;
    Lock l = new ReentrantLock();

    JPanel pannello;
    JToolBar barraControllo;

    //JTextArea logger;
    ListaAppelli appelli = new ListaAppelli(this);

    public void setPannello(JPanel pannello){
        this.pannello = pannello;
    }

    public void setBarraControllo(JToolBar barraControllo){
        this.barraControllo = barraControllo;
    }

    //public void setLogger(JTextArea logger){
    //    this.logger = logger;
    //}

    public void setAppelli(ListaAppelli appelli){
        this.appelli = appelli;
    }


    public void notificaComponenti(ListaAppelli listaAppelli){
        DefaultListModel<Remotemethod.Appello> lista = new DefaultListModel<>();
        for (Remotemethod.Appello appello : listaAppelli.getAppelloList()) {
            lista.addElement(appello);
        }
        JList<Remotemethod.Appello> jList = new JList<>(lista);
        JScrollPane scrollPane = new JScrollPane(jList);

    }

    @Override
    public void interrompiCaricamento() {
        try{
            l.lock();
            interrupt = true;
        } finally {
            l.unlock();
        }
    }




}
