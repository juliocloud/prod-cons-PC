package prodcons.v1;
import java.io.IOException;
import java.util.Properties;

public class TestProdCons {
    public int nProd, nCons;
    public Properties properties;
    public int buffSize;
    public ProdConsBuffer prodConsBuffer;

    public TestProdCons() throws IOException {
        properties = new Properties();
        properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("prodcons/options.xml"));
        nProd = Integer.parseInt(properties.getProperty("nProd"));
        nCons = Integer.parseInt(properties.getProperty("nCons"));
        buffSize = Integer.parseInt(properties.getProperty("bufSz"));
        prodConsBuffer = new ProdConsBuffer(buffSize);
    }

    public void main() throws InterruptedException {
        System.out.println("Creating consumers producers");
        Consumer[] consumers = new Consumer[nCons];
        Producer[] producers = new Producer[nProd];

        for(int i = 0; i < nCons; i ++){
            Consumer c = consumers[i] = new Consumer(prodConsBuffer);
            c.start();
        }

        for(int i = 0; i < nProd; i++){
            Producer p = producers[i] = new Producer(prodConsBuffer);
            p.start();
        }

        for(int i = 0; i< 100; i++){
            Consumer c = consumers[i];
            Producer p = producers[i];

            c.join();
            p.join();
        }

    
    }
}
