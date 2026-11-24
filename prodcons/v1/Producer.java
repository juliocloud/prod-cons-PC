package prodcons.v1;

import java.util.Random;

public class Producer extends Thread {

    ProdConsBuffer prodConsBuffer;
    public static int seqNumber = 0;

    public Producer(ProdConsBuffer prodConsBuffer) {
        this.prodConsBuffer = prodConsBuffer;
    }

    public void run() {
        int maxProd = prodConsBuffer.maxProd;
        int minProd = prodConsBuffer.minProd;
        int n = new Random().nextInt(maxProd - minProd) - minProd;

        try {
            for (int i = 0; i < n; i++) {
                prodConsBuffer.put(new Message(seqNumber++, this.getId()));
                Thread.sleep(prodConsBuffer.prodTime);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
