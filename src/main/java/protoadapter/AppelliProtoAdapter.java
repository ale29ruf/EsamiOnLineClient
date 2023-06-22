package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import proto.Remotemethod;

import java.util.List;

public class AppelliProtoAdapter implements Model {
    private Remotemethod.ListaAppelli appelli;
    private AbstractMediator mediator;

    public AppelliProtoAdapter(Remotemethod.ListaAppelli appelli, AbstractMediator mediator){
        this.appelli = appelli;
        this.mediator = mediator;
    }

    public AppelliProtoAdapter(AbstractMediator mediator){
        this.mediator = mediator;
    }

    public List<Remotemethod.Appello> getAppelliList(){
        return appelli.getAppelliList();
    }

    @Override
    public void notifica() {
        mediator.notificaComponenti(this);
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder remoteInfo) {
        this.appelli = (Remotemethod.ListaAppelli) remoteInfo;
        notifica();
    }
}
