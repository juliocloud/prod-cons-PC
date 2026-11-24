package prodcons.v1;

import java.util.Objects;

public class ProdConsBuffer implements IProdConsBuffer {
    public int nfull, nempty;
    public int in, out;
    public final Message[] buff;

    public ProdConsBuffer(int bufSize) {
        buff = new Message[bufSize];
        nempty = 0;
        nfull = bufSize;
    }

    @Override
    public synchronized void put(Message m) throws InterruptedException {
        while (nfull == 0){
            System.out.println("Produtor: " + m.prodId + ". Aguardando");
            wait();
        }
        buff[in] = m;
        in = (in + 1) % buff.length;
        nempty++;
        nfull--;
        notifyAll();
        System.out.println("Produtor: " + m.prodId + ". Produziu msg");
        printBuff();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        System.out.println(nempty);
        while (nempty == 0){
            System.out.println("Consumidor aguardando ");
            wait();
        }
        Message msg = buff[out];
        buff[out] = null;
        // out = (out + 1) % buff.length;
        nfull++;
        nempty--;
        notifyAll();
        printBuff();
        System.out.println("Consumidor consumiu thank you sir");
        return msg;
    }

    @Override
    public int nmsg() {
        throw new UnsupportedOperationException("Unimplemented method 'nmsg'");
    }

    @Override
    public int totmsg() {

        throw new UnsupportedOperationException("Unimplemented method 'totmsg'");
    }

    public void printBuff() {
        System.out.println("------------ Buffer -----------");
        for (Message o : buff) {
            if (!Objects.isNull(o)) {
                System.out.println("seq: " + o.seq);
                System.out.println("prodId: " + o.prodId);
            } else {
                System.out.println("seq: empty");
                System.out.println("prodId: empty");
            }
        }

        System.out.println("---------------------------");
    }

}
