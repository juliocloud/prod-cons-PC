package prodcons.v1;

import java.util.Objects;

public class ProdConsBuffer implements IProdConsBuffer {
    public int nfull, nempty;
    public int in, out;
    public final Message[] buff;
    public int consTime, prodTime, minProd, maxProd;

    public ProdConsBuffer(int bufSize, int consTime, int prodTime, int minProd, int maxProd) {

        this.consTime = consTime;
        this.prodTime = prodTime;
        this.minProd = minProd;
        this.maxProd = maxProd;
        
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
        System.out.println("Produtor: " + m.prodId + ". Produziu msg");
        printBuff();
        notifyAll();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        while (nempty == 0){
            System.out.println("Consumidor aguardando ");
            wait(); // aqui a thread atual solta o lock. quando alguem chama notify, ele pega o lock de novo
        }
        Message msg = buff[out];
        buff[out] = null;
        out = (out + 1) % buff.length;
        nfull++;
        nempty--;
        System.out.println("Consumidor consumiu : " + msg.seq);
        printBuff();
        notifyAll();
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
