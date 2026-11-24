public class Message {
    public final int seq;
    public final long prodId;

    public Message (int seq, long prodId){
        this.seq = seq;
        this.prodId = prodId;
    }
}
