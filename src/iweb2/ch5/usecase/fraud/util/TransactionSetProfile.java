package iweb2.ch5.usecase.fraud.util;

/**
 * Configuration properties to control generation of user transactions. 
 */
public class TransactionSetProfile {

    /*
     * Identifies Credit Card User.
     */
    private int userId;
    
    private int nTxns;
    
    /*
     * Mean value for transaction amount.
     */
    private double txnAmtMean;
    
    /*
     * Standard deviation for transaction amount.
     */
    private double txnAmtStd;
    
    /*
     * Location coordinates.
     */
    private int locationMinX;
    private int locationMaxX;
    private int locationMinY;
    private int locationMaxY;
    
    /*
     * Descriptions that will be used for valid transactions.
     */
    private String[] txnDescriptions;

    private boolean isFraud;

    
    public TransactionSetProfile() {
        // empty
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationMinX() {
        return locationMinX;
    }

    public int getLocationMaxX() {
        return locationMaxX;
    }

    public int getLocationMinY() {
        return locationMinY;
    }

    public int getLocationMaxY() {
        return locationMaxY;
    }
    
    public String[] getTxnDescriptions() {
        return txnDescriptions;
    }

    public void setTxnDescriptions(String[] txnDescriptions) {
        this.txnDescriptions = txnDescriptions;
    }

    public void setLocations(int minX, int minY, int maxX, int maxY) {
        this.locationMinX = minX;
        this.locationMinY = minY;
        this.locationMaxX = maxX;
        this.locationMaxY = maxY;        
    }

    public double getTxnAmtMean() {
        return txnAmtMean;
    }

    public void setTxnAmtMean(double txnAmtMean) {
        this.txnAmtMean = txnAmtMean;
    }

    public double getTxnAmtStd() {
        return txnAmtStd;
    }

    public void setTxnAmtStd(double txnAmtStd) {
        this.txnAmtStd = txnAmtStd;
    }

    public int getNTxns() {
        return nTxns;
    }

    public void setNTxns(int txns) {
        nTxns = txns;
    }

    public boolean isFraud() {
        return isFraud;
    }

    public void setFraud(boolean isFraud) {
        this.isFraud = isFraud;
    }

    
}
