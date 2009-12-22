package iweb2.ch5.usecase.fraud.data;


import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.usecase.fraud.util.UserStatisticsCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDataset implements java.io.Serializable {

	private static final long serialVersionUID = 3061645520644719411L;
	
	private Map<Integer, List<Transaction>> txnsByUserIdMap;
    private Map<String, Transaction> txnsByTxnIdMap;
    private Integer maxUserId;
    private TransactionInstanceBuilder instanceBuilder;
    
    public TransactionDataset(List<Transaction> txnsList) {
        this.txnsByUserIdMap = new HashMap<Integer, List<Transaction>>();
        this.txnsByTxnIdMap = new HashMap<String, Transaction>(txnsList.size());
        
        for(Transaction e : txnsList) {

            txnsByTxnIdMap.put(String.valueOf(e.getTxnId()), e);
            
            Integer userId = e.getUserId();
            List<Transaction> userTxns = txnsByUserIdMap.get(userId);
            if( userTxns == null ) {
                userTxns = new ArrayList<Transaction>();
                txnsByUserIdMap.put(userId, userTxns);
            }
            
            if( maxUserId == null || e.getUserId() > maxUserId ) {
                maxUserId = e.getUserId();
            }
            
            userTxns.add(e);
        }
        
        instanceBuilder = new TransactionInstanceBuilder();
        
    }

    public void calculateUserStats() {
        UserStatisticsCalculator userStatsCalculator = new UserStatisticsCalculator();
        
        instanceBuilder.setUserStatisticsMap(userStatsCalculator.calculateStatistics(this));
    }
    
    public TrainingSet createTrainingDataset() {
    	return instanceBuilder.createTrainingSet(this);
    }
    
    public Integer getMaxUserId() {
        return maxUserId;
    }
    
    public List<Integer> getUsers() {
        return new ArrayList<Integer>(txnsByUserIdMap.keySet());
    }

    public List<Transaction> findUserTxns(Integer userId) {
        return new ArrayList<Transaction>(txnsByUserIdMap.get(userId));
    }
    
    public List<Transaction> getTransactions() {
        return new ArrayList<Transaction>(txnsByTxnIdMap.values());
    }
    
    public Transaction findTransactionById(String id) {
        return txnsByTxnIdMap.get(id);
    }
    
    public void printTransaction(String id) {
        Transaction e = findTransactionById(id);
        if( e != null ) {
            System.out.println(e.toString());
        }
        else {
            System.out.println("Transaction not found (txn id: '" + id + "')");
        }
    }
    
    public void printAll() {
        for(Map.Entry<String, Transaction> e : txnsByTxnIdMap.entrySet()) {
            Transaction t = e.getValue();
            System.out.println(t);
        }
    }

    public int getSize() {
        return txnsByTxnIdMap.size();
    }

	/**
	 * @return the instanceBuilder
	 */
	public TransactionInstanceBuilder getInstanceBuilder() {
		return instanceBuilder;
	}

}
