package commands;

import proto.SenderGrpc;
import protoadapter.Model;

import java.util.List;

public class InviaRisposte extends Thread{
    Model m;
    SenderGrpc.SenderBlockingStub stub;
    List<Integer> risposte;

    public InviaRisposte(Model m, SenderGrpc.SenderBlockingStub stub, List<Integer> risposte) {
        this.m = m;
        this.stub = stub;
        this.risposte = risposte;
    }

    public void run(){

    }
}
