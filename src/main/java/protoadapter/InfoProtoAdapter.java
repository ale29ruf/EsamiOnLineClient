package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;
import mediator.Mediatore;
import proto.Remotemethod;

public class InfoProtoAdapter implements Model{
    private AbstractMediator mediator;
    private Remotemethod.Info info;

    public InfoProtoAdapter(AbstractMediator m){
        mediator = m;
    }

    public String getTesto(){
        return info.getTesto();
    }

    @Override
    public void setRemoteInfo(MessageOrBuilder remoteInfo) {
        this.info = (Remotemethod.Info) remoteInfo;
        notifica();
    }

    @Override
    public void notifica() {
        mediator.rispostaPartecipazione(this);
    }
}
