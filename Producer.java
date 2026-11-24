public class Producer extends Thread {

    ProdConsBuffer prodConsBuffer;

    public Producer(ProdConsBuffer prodConsBuffer) {
        this.prodConsBuffer = prodConsBuffer;
    }

    public void run() {
        try {
            prodConsBuffer.put(new Message(1, this.getId()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
