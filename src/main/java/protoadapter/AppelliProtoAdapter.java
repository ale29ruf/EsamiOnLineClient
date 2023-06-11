package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import proto.Remotemethod;

import java.util.List;

public class AppelliProtoAdapter implements Model {
    Remotemethod.ListaAppelli appelli;
    AbstractMediator mediator;

    public AppelliProtoAdapter(Remotemethod.ListaAppelli appelli, AbstractMediator mediator){
        this.appelli = appelli;
        this.mediator = mediator;
    }

    public AppelliProtoAdapter(AbstractMediator mediator){
        this.mediator = mediator;
    }

    public AppelliProtoAdapter(Remotemethod.ListaAppelli appelli){
        this.appelli = appelli;
    }

    public List<Remotemethod.Appello> getList(){
        return appelli.getAppelliList();
    }

    public void set(Remotemethod.ListaAppelli appelli){
        this.appelli = appelli;
        notifica();
    }

    public Remotemethod.ListaAppelli get(){
        return appelli;
    }

    @Override
    public void notifica() {
        mediator.notificaComponenti(this);
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder model) {
        this.appelli = (Remotemethod.ListaAppelli) model;
        notifica();
    }
}
