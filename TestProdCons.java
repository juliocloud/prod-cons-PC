import java.io.IOException;
import java.util.Properties;

public class TestProdCons {
    public ProdConsBuffer prodConsBuffer = new ProdConsBuffer(3);

    public TestProdCons() throws IOException {
        Properties properties = new Properties();
        properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("./options.xml"));
        int nProd = Integer.parseInt(properties.getProperty("nProd"));
        int nCons = Integer.parseInt(properties.getProperty("nCons"));
    }

    public void main() throws InterruptedException {
        System.out.println("Creating consumers producers");
        Producer producer = new Producer(prodConsBuffer);
        Consumer consumer = new Consumer(prodConsBuffer);
        System.out.println("Starting threads");
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

    }
}
