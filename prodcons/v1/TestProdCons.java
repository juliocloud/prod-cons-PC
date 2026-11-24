package prodcons.v1;

import java.io.IOException;
import java.util.Properties;

public class TestProdCons {
    public int nProd, nCons;
    public Properties properties;
    public int buffSize;
    public ProdConsBuffer prodConsBuffer;
    public int consTime, prodTime, minProd, maxProd;

    public TestProdCons() throws IOException {
        properties = new Properties();
        properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("prodcons/options.xml"));
        nProd = Integer.parseInt(properties.getProperty("nProd"));
        nCons = Integer.parseInt(properties.getProperty("nCons"));
        buffSize = Integer.parseInt(properties.getProperty("bufSz"));
        consTime = Integer.parseInt(properties.getProperty("consTime"));
        prodTime = Integer.parseInt(properties.getProperty("prodTime"));
        minProd = Integer.parseInt(properties.getProperty("minProd"));
        maxProd = Integer.parseInt(properties.getProperty("maxProd"));
        prodConsBuffer = new ProdConsBuffer(buffSize, consTime, prodTime, minProd, maxProd);
    }

    public void run() throws InterruptedException {
        System.out.println("Creating consumers producers");
        Consumer[] consumers = new Consumer[nCons];
        Producer[] producers = new Producer[nProd];

        for (int i = 0; i < nCons; i++) {
            Consumer c = consumers[i] = new Consumer(prodConsBuffer);
            c.start();
        }

        for (int i = 0; i < nProd; i++) {
            Producer p = producers[i] = new Producer(prodConsBuffer);
            p.start();
        }

        for (int i = 0; i < nProd; i++) {
            producers[i].join();
        }

        for(int i = 0; i < nCons; i++){
            consumers[i].join();
        }
    }
}
