public class Consumer extends Thread {
    ProdConsBuffer prodConsBuffer;
    Message msg;

    public Consumer(ProdConsBuffer prodConsBuffer){
        this.prodConsBuffer = prodConsBuffer;
    }

    public void run(){
        try {
            msg = prodConsBuffer.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
