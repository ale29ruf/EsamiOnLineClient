package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import proto.Remotemethod;

public class CodiceAppelloAdapter implements Model {
    AbstractMediator mediator;
    Remotemethod.CodiceAppello codiceAppello;


    public CodiceAppelloAdapter(AbstractMediator mediator) {
        this.mediator = mediator;
    }

    public CodiceAppelloAdapter(Remotemethod.CodiceAppello codiceAppello) {
        this.codiceAppello = codiceAppello;
    }

    public Remotemethod.CodiceAppello get(){
        return codiceAppello;
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder model) {
        codiceAppello = (Remotemethod.CodiceAppello) model;
        notifica();
    }

    @Override
    public void notifica() {
        mediator.comunicaCodice(this);
    }

    public boolean isCodice(){
        return codiceAppello.getCodice().length() == 32;
    }
}
