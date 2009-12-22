package iweb2.ch5.usecase.fraud.util;

import iweb2.ch5.usecase.fraud.data.Transaction;
import iweb2.ch5.usecase.fraud.data.TransactionLocation;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    
    public DataGenerator() {
        // default value
        this.setNextTxnId(1);
    }
    
    
    
    public List<Transaction> generateTxns(TransactionSetProfile[] allUsers) {
        List<Transaction> allTransactions = new ArrayList<Transaction>();
        for(int i = 0, n = allUsers.length; i < n; i++) {
            TransactionSetProfile user = allUsers[i];
            
            for(int j = 0; j < user.getNTxns(); j++) {
                allTransactions.add(generateTxn(user));
            }

        }
        return allTransactions;
    }
    
    private long nextTxnId = 0;

    public void setNextTxnId(long nextTxnId) {
        this.nextTxnId = nextTxnId;
    }
    
    private long generateNextUniqueTxnId() {
        return nextTxnId++;
    }
    
    private Transaction generateTxn(TransactionSetProfile userParams) {
        Transaction e = new Transaction();

        e.setUserId(userParams.getUserId());
        e.setTxnId(generateNextUniqueTxnId());

        // Txn Amount
        double amt = generateAmt(userParams);
        e.setAmount(amt);
        
        // Txn Description
        String txnDescription = generateDescription(userParams); 
        e.setDescription(txnDescription);

        // Txn Location
        TransactionLocation location = generateLocation(userParams);
        e.setLocation(location);
        
        // Txn fraud flag
        e.setFraud(userParams.isFraud());
        
        return e;
    }
    
    private double generateAmt(TransactionSetProfile user) {
        return DataUtils.nextTxnAmount(user.getTxnAmtMean(), user.getTxnAmtStd()); 
    }
    
    private String generateDescription(TransactionSetProfile userParams) {
        int txnDescriptionId;
        String[] txnDescriptions;
        txnDescriptions = userParams.getTxnDescriptions();
        txnDescriptionId = DataUtils.randomInt(txnDescriptions.length);        
        return txnDescriptions[txnDescriptionId];
    }
    
    private TransactionLocation generateLocation(TransactionSetProfile userParams) {

        int minX = userParams.getLocationMinX();
        int maxX = userParams.getLocationMaxX();
        int minY = userParams.getLocationMinY();
        int maxY = userParams.getLocationMaxY();
        
        int x = DataUtils.randomInt(minX, maxX);
        int y = DataUtils.randomInt(minY, maxY);

        return new TransactionLocation(x, y);
    }
}
