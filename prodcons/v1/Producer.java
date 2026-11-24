package prodcons.v1;
public class Producer extends Thread {

    ProdConsBuffer prodConsBuffer;
    public static int seqNumber = 0;

    public Producer(ProdConsBuffer prodConsBuffer) {
        this.prodConsBuffer = prodConsBuffer;
    }

    public void run() {
        System.out.println("Producer running!");
        try {
            prodConsBuffer.put(new Message(seqNumber++, this.getId()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
