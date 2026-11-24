package prodcons.v1;

import java.util.Objects;

public class ProdConsBuffer implements IProdConsBuffer {
    public int nfull = 0, nempty;
    public int in, out;
    public final Message[] buff;

    public ProdConsBuffer(int bufSize) {
        buff = new Message[bufSize];
        nempty = bufSize;
    }

    @Override
    public synchronized void put(Message m) throws InterruptedException {
        System.out.println("Waiting put");
        while (nfull == buff.length)
            wait();
        buff[in] = m;
        in = (in + 1) % buff.length;
        nempty--;
        nfull++;
        notifyAll();
        System.out.println("Put message.");
        printBuff();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        System.out.println("Waiting get");
        while (nempty == 0)
            wait();
        Message msg = buff[out];
        buff[out] = null;
        // out = (out + 1) % buff.length;
        nfull--;
        nempty++;
        notifyAll();
        System.out.println("Consumed message.");
        printBuff();
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
