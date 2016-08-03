import java.io.IOException;

/**
 * Created by Rosiana on 8/3/2016.
 */
public class SimilarityThread extends Thread {
    @Override
    public void run() {
        super.run();

    }
    
    public static float getSimilarity(String[] listpeserta1, String[] listpeserta2) throws IOException {
        float sim = 0;
        float simx = 0;
        float max = 0;
        float same = 0;
        for (int i = 0; i < listpeserta1.length; i++) {
            loop:
            for (int j = 0; j < listpeserta2.length; j++) {
                if (listpeserta1[i].equals(listpeserta2[j])) {
                    same += 1;
                    break loop;
                }
            }
        }
        if (listpeserta1.length >= listpeserta2.length) {
            max = listpeserta1.length;
        }
        else {
            max = listpeserta2.length;
        }
        sim = same / max;
        simx = 1 - sim;
        System.out.println("simx: " + simx);
        return simx;
    }
}
