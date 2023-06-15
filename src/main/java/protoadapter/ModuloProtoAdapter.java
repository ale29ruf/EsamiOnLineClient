package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import proto.Remotemethod;

public class ModuloProtoAdapter implements Model{
    AbstractMediator mediator;
    Remotemethod.Modulo modulo;


    public ModuloProtoAdapter(AbstractMediator mediator) {
        this.mediator = mediator;
    }

    public Remotemethod.ListaRisposte getListaRisposte(){
        return modulo.getListaRisposte();
    }

    public int getIdAppello(){
        return modulo.getIdAppello();
    }

    public int getPunteggio(){
        return modulo.getPunteggio();
    }

    @Override
    public void notifica() {
        mediator.moduloRicevuto(this);
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder remoteInfo) {
        modulo = (Remotemethod.Modulo) remoteInfo;
        notifica();
    }
}
