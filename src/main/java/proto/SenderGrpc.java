package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The greeting service definition. 
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: remotemethod.proto")
public final class SenderGrpc {

  private SenderGrpc() {}

  public static final String SERVICE_NAME = "Sender";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.Remotemethod.Info,
      proto.Remotemethod.ListaAppelli> getCaricaAppelliMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CaricaAppelli",
      requestType = proto.Remotemethod.Info.class,
      responseType = proto.Remotemethod.ListaAppelli.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Remotemethod.Info,
      proto.Remotemethod.ListaAppelli> getCaricaAppelliMethod() {
    io.grpc.MethodDescriptor<proto.Remotemethod.Info, proto.Remotemethod.ListaAppelli> getCaricaAppelliMethod;
    if ((getCaricaAppelliMethod = SenderGrpc.getCaricaAppelliMethod) == null) {
      synchronized (SenderGrpc.class) {
        if ((getCaricaAppelliMethod = SenderGrpc.getCaricaAppelliMethod) == null) {
          SenderGrpc.getCaricaAppelliMethod = getCaricaAppelliMethod = 
              io.grpc.MethodDescriptor.<proto.Remotemethod.Info, proto.Remotemethod.ListaAppelli>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Sender", "CaricaAppelli"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.Info.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.ListaAppelli.getDefaultInstance()))
                  .setSchemaDescriptor(new SenderMethodDescriptorSupplier("CaricaAppelli"))
                  .build();
          }
        }
     }
     return getCaricaAppelliMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Remotemethod.Studente,
      proto.Remotemethod.CodiceAppello> getRegistraStudenteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegistraStudente",
      requestType = proto.Remotemethod.Studente.class,
      responseType = proto.Remotemethod.CodiceAppello.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Remotemethod.Studente,
      proto.Remotemethod.CodiceAppello> getRegistraStudenteMethod() {
    io.grpc.MethodDescriptor<proto.Remotemethod.Studente, proto.Remotemethod.CodiceAppello> getRegistraStudenteMethod;
    if ((getRegistraStudenteMethod = SenderGrpc.getRegistraStudenteMethod) == null) {
      synchronized (SenderGrpc.class) {
        if ((getRegistraStudenteMethod = SenderGrpc.getRegistraStudenteMethod) == null) {
          SenderGrpc.getRegistraStudenteMethod = getRegistraStudenteMethod = 
              io.grpc.MethodDescriptor.<proto.Remotemethod.Studente, proto.Remotemethod.CodiceAppello>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Sender", "RegistraStudente"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.Studente.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.CodiceAppello.getDefaultInstance()))
                  .setSchemaDescriptor(new SenderMethodDescriptorSupplier("RegistraStudente"))
                  .build();
          }
        }
     }
     return getRegistraStudenteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Remotemethod.Info,
      proto.Remotemethod.ListaDomande> getCaricaDomandeAppelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CaricaDomandeAppello",
      requestType = proto.Remotemethod.Info.class,
      responseType = proto.Remotemethod.ListaDomande.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Remotemethod.Info,
      proto.Remotemethod.ListaDomande> getCaricaDomandeAppelloMethod() {
    io.grpc.MethodDescriptor<proto.Remotemethod.Info, proto.Remotemethod.ListaDomande> getCaricaDomandeAppelloMethod;
    if ((getCaricaDomandeAppelloMethod = SenderGrpc.getCaricaDomandeAppelloMethod) == null) {
      synchronized (SenderGrpc.class) {
        if ((getCaricaDomandeAppelloMethod = SenderGrpc.getCaricaDomandeAppelloMethod) == null) {
          SenderGrpc.getCaricaDomandeAppelloMethod = getCaricaDomandeAppelloMethod = 
              io.grpc.MethodDescriptor.<proto.Remotemethod.Info, proto.Remotemethod.ListaDomande>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Sender", "CaricaDomandeAppello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.Info.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.ListaDomande.getDefaultInstance()))
                  .setSchemaDescriptor(new SenderMethodDescriptorSupplier("CaricaDomandeAppello"))
                  .build();
          }
        }
     }
     return getCaricaDomandeAppelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Remotemethod.RispostaAppello,
      proto.Remotemethod.Modulo> getInviaRisposteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InviaRisposte",
      requestType = proto.Remotemethod.RispostaAppello.class,
      responseType = proto.Remotemethod.Modulo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Remotemethod.RispostaAppello,
      proto.Remotemethod.Modulo> getInviaRisposteMethod() {
    io.grpc.MethodDescriptor<proto.Remotemethod.RispostaAppello, proto.Remotemethod.Modulo> getInviaRisposteMethod;
    if ((getInviaRisposteMethod = SenderGrpc.getInviaRisposteMethod) == null) {
      synchronized (SenderGrpc.class) {
        if ((getInviaRisposteMethod = SenderGrpc.getInviaRisposteMethod) == null) {
          SenderGrpc.getInviaRisposteMethod = getInviaRisposteMethod = 
              io.grpc.MethodDescriptor.<proto.Remotemethod.RispostaAppello, proto.Remotemethod.Modulo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Sender", "InviaRisposte"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.RispostaAppello.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Remotemethod.Modulo.getDefaultInstance()))
                  .setSchemaDescriptor(new SenderMethodDescriptorSupplier("InviaRisposte"))
                  .build();
          }
        }
     }
     return getInviaRisposteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SenderStub newStub(io.grpc.Channel channel) {
    return new SenderStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SenderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SenderBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SenderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SenderFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition. 
   * </pre>
   */
  public static abstract class SenderImplBase implements io.grpc.BindableService {

    /**
     */
    public void caricaAppelli(proto.Remotemethod.Info request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.ListaAppelli> responseObserver) {
      asyncUnimplementedUnaryCall(getCaricaAppelliMethod(), responseObserver);
    }

    /**
     */
    public void registraStudente(proto.Remotemethod.Studente request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.CodiceAppello> responseObserver) {
      asyncUnimplementedUnaryCall(getRegistraStudenteMethod(), responseObserver);
    }

    /**
     */
    public void caricaDomandeAppello(proto.Remotemethod.Info request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.ListaDomande> responseObserver) {
      asyncUnimplementedUnaryCall(getCaricaDomandeAppelloMethod(), responseObserver);
    }

    /**
     */
    public void inviaRisposte(proto.Remotemethod.RispostaAppello request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.Modulo> responseObserver) {
      asyncUnimplementedUnaryCall(getInviaRisposteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCaricaAppelliMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Remotemethod.Info,
                proto.Remotemethod.ListaAppelli>(
                  this, METHODID_CARICA_APPELLI)))
          .addMethod(
            getRegistraStudenteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Remotemethod.Studente,
                proto.Remotemethod.CodiceAppello>(
                  this, METHODID_REGISTRA_STUDENTE)))
          .addMethod(
            getCaricaDomandeAppelloMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Remotemethod.Info,
                proto.Remotemethod.ListaDomande>(
                  this, METHODID_CARICA_DOMANDE_APPELLO)))
          .addMethod(
            getInviaRisposteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Remotemethod.RispostaAppello,
                proto.Remotemethod.Modulo>(
                  this, METHODID_INVIA_RISPOSTE)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition. 
   * </pre>
   */
  public static final class SenderStub extends io.grpc.stub.AbstractStub<SenderStub> {
    private SenderStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenderStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenderStub(channel, callOptions);
    }

    /**
     */
    public void caricaAppelli(proto.Remotemethod.Info request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.ListaAppelli> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCaricaAppelliMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registraStudente(proto.Remotemethod.Studente request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.CodiceAppello> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegistraStudenteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void caricaDomandeAppello(proto.Remotemethod.Info request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.ListaDomande> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCaricaDomandeAppelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void inviaRisposte(proto.Remotemethod.RispostaAppello request,
        io.grpc.stub.StreamObserver<proto.Remotemethod.Modulo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInviaRisposteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition. 
   * </pre>
   */
  public static final class SenderBlockingStub extends io.grpc.stub.AbstractStub<SenderBlockingStub> {
    private SenderBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenderBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenderBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.Remotemethod.ListaAppelli caricaAppelli(proto.Remotemethod.Info request) {
      return blockingUnaryCall(
          getChannel(), getCaricaAppelliMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Remotemethod.CodiceAppello registraStudente(proto.Remotemethod.Studente request) {
      return blockingUnaryCall(
          getChannel(), getRegistraStudenteMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Remotemethod.ListaDomande caricaDomandeAppello(proto.Remotemethod.Info request) {
      return blockingUnaryCall(
          getChannel(), getCaricaDomandeAppelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Remotemethod.Modulo inviaRisposte(proto.Remotemethod.RispostaAppello request) {
      return blockingUnaryCall(
          getChannel(), getInviaRisposteMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition. 
   * </pre>
   */
  public static final class SenderFutureStub extends io.grpc.stub.AbstractStub<SenderFutureStub> {
    private SenderFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SenderFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SenderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Remotemethod.ListaAppelli> caricaAppelli(
        proto.Remotemethod.Info request) {
      return futureUnaryCall(
          getChannel().newCall(getCaricaAppelliMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Remotemethod.CodiceAppello> registraStudente(
        proto.Remotemethod.Studente request) {
      return futureUnaryCall(
          getChannel().newCall(getRegistraStudenteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Remotemethod.ListaDomande> caricaDomandeAppello(
        proto.Remotemethod.Info request) {
      return futureUnaryCall(
          getChannel().newCall(getCaricaDomandeAppelloMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Remotemethod.Modulo> inviaRisposte(
        proto.Remotemethod.RispostaAppello request) {
      return futureUnaryCall(
          getChannel().newCall(getInviaRisposteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CARICA_APPELLI = 0;
  private static final int METHODID_REGISTRA_STUDENTE = 1;
  private static final int METHODID_CARICA_DOMANDE_APPELLO = 2;
  private static final int METHODID_INVIA_RISPOSTE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SenderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SenderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CARICA_APPELLI:
          serviceImpl.caricaAppelli((proto.Remotemethod.Info) request,
              (io.grpc.stub.StreamObserver<proto.Remotemethod.ListaAppelli>) responseObserver);
          break;
        case METHODID_REGISTRA_STUDENTE:
          serviceImpl.registraStudente((proto.Remotemethod.Studente) request,
              (io.grpc.stub.StreamObserver<proto.Remotemethod.CodiceAppello>) responseObserver);
          break;
        case METHODID_CARICA_DOMANDE_APPELLO:
          serviceImpl.caricaDomandeAppello((proto.Remotemethod.Info) request,
              (io.grpc.stub.StreamObserver<proto.Remotemethod.ListaDomande>) responseObserver);
          break;
        case METHODID_INVIA_RISPOSTE:
          serviceImpl.inviaRisposte((proto.Remotemethod.RispostaAppello) request,
              (io.grpc.stub.StreamObserver<proto.Remotemethod.Modulo>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SenderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.Remotemethod.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Sender");
    }
  }

  private static final class SenderFileDescriptorSupplier
      extends SenderBaseDescriptorSupplier {
    SenderFileDescriptorSupplier() {}
  }

  private static final class SenderMethodDescriptorSupplier
      extends SenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SenderMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SenderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SenderFileDescriptorSupplier())
              .addMethod(getCaricaAppelliMethod())
              .addMethod(getRegistraStudenteMethod())
              .addMethod(getCaricaDomandeAppelloMethod())
              .addMethod(getInviaRisposteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
