package component;

import mediator.AbstractMediator;
import mediator.Mediatore;
import proto.Remotemethod;

import java.util.List;

public class ListaAppelli implements Model{

    List<Remotemethod.Appello> appelloList;
    AbstractMediator mediatore;

    public ListaAppelli(AbstractMediator mediatore){
        this.mediatore = mediatore;
    }

    public List<Remotemethod.Appello> getAppelloList(){
        return appelloList;
    }

    public void setAppelloList(List<Remotemethod.Appello> appelloList){
        this.appelloList = appelloList;
        notifica();
    }


    @Override
    public void notifica() {
        mediatore.notificaComponenti(this);
    }
}
