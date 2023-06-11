package protoadapter;

import com.google.protobuf.MessageOrBuilder;

public interface Model {

    void notifica();

    void setRemoteInfo(MessageOrBuilder remoteInfo);

}
