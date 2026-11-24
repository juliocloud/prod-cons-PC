package prodcons.v1;

public class Consumer extends Thread {
    ProdConsBuffer prodConsBuffer;
    Message msg;

    public Consumer(ProdConsBuffer prodConsBuffer) {
        this.prodConsBuffer = prodConsBuffer;
    }

    public void run() {
        try {
            while(true){
                msg = prodConsBuffer.get();
                Thread.sleep(prodConsBuffer.consTime);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
