package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import mediator.Mediatore;
import proto.Remotemethod;

import java.util.List;

public class ListaDomandeProtoAdapter implements Model{
    AbstractMediator mediator;
    Remotemethod.ListaDomande domande;

    public ListaDomandeProtoAdapter(AbstractMediator mediator){
        this.mediator = mediator;
    }

    public List<Remotemethod.Domanda> getDomandeList(){
        return domande.getDomandeList();
    }

    @Override
    public void notifica() {
        mediator.domandeRicevute(this);
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder remoteInfo) {
        this.domande = (Remotemethod.ListaDomande) remoteInfo;
        notifica();
    }
}
