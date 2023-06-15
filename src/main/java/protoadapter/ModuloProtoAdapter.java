package protoadapter;

import com.google.protobuf.MessageOrBuilder;
import mediator.AbstractMediator;

public class ModuloProtoAdapter implements Model{
    AbstractMediator mediator;

    public ModuloProtoAdapter(AbstractMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void notifica() {

    }

    @Override
    public void setRemoteInfo(MessageOrBuilder remoteInfo) {

    }
}
