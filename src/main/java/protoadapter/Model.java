package protoadapter;

import com.google.protobuf.MessageOrBuilder;

/**
 * Le classi che implementano questa interfaccia vengono utilizzate per notificare l'AbstractMediator nel momento in cui
 * il task relativo riceve una risposta dal server.
 */
public interface Model {

    void notifica();

    void setRemoteInfo(MessageOrBuilder remoteInfo);

}
