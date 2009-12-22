package iweb2.ch5.usecase.fraud.util;

import iweb2.ch5.usecase.fraud.data.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataUtils {
    
    private static Random rnd = new Random();
    
    static int randomInt(int n) {
        return DataUtils.randomInt(0, n);
    }

    static int randomInt(int min, int max) {
        return min + rnd.nextInt(max - min);
    }
    
    private static Random txnAmountRnd = new Random();

    public static double nextTxnAmount(double mean, double std) {
        double amt = 0.0;
        do {
            // deriving gaussian with our custom std and mean from Standard Normal Distribution.
            amt = txnAmountRnd.nextGaussian() * std + mean; 
        }
        while(amt <= 0.0);
        
        BigDecimal db = new BigDecimal(amt);
        db = db.setScale(2, BigDecimal.ROUND_HALF_UP);
        return db.doubleValue(); 
    }
 
    static String[] loadTxnDescriptions(String filename) {
        List<String> descriptions = new ArrayList<String>();
        try {
            FileInputStream fin = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = null;
            while( (line = reader.readLine()) != null) {
                if( line.trim().length() > 0 ) {
                    descriptions.add(line);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(
                "Failed to load descriptions from file: '" + filename + "' ", 
                e);
        }
        
        return descriptions.toArray(new String[descriptions.size()]); 
    }
    
    static void saveTransactions(String filename, List<Transaction> txns) {
        try {
            FileWriter fout = new FileWriter(filename);
            BufferedWriter writer = new BufferedWriter(fout);
            for(Transaction txn : txns) {
                writer.write(txn.toExternalString());
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(
                "Failed to load descriptions from file: '" + filename + "' ", 
                e);
        }
    }

    public static List<Transaction> loadTransactions(String filename) {
        List<Transaction> txns = new ArrayList<Transaction>();
        try {
            FileReader fReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fReader);
            String line = null;
            while( (line = reader.readLine()) != null) {
                if( line.trim().length() > 0 ) {
                    Transaction txn = new Transaction();
                    txn.loadFromExternalString(line);
                    txns.add(txn);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(
                "Failed to load transactions from file: '" + filename + "' ", 
                e);
        }
        
        return txns; 
    }
    
}
