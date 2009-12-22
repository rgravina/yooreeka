package iweb2.ch5.usecase.fraud.data;


public class Transaction implements java.io.Serializable {
   
	private static final long serialVersionUID = -4537757080789309552L;

	private String description;
    
    private TransactionLocation location;
    
    private double amount;
    
    private boolean fraud;
    
    private int userId;
    
    private long txnId;
    
    public Transaction() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionLocation getLocation() {
        return location;
    }

    public void setLocation(TransactionLocation location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public boolean isFraud() {
        return fraud;
    }

    public void setFraud(boolean fraud) {
        this.fraud = fraud;
    }

    public String toExternalString() {
        return userId +
        ":" + txnId +
        ":" + description + 
        ":" + amount + 
        ":" + location.getX() + 
        ":" + location.getY() + 
        ":" + fraud;
    }
    
    @Override
	public String toString() {
        return toExternalString();
    }

    public void loadFromExternalString(String text) {

        String[] values = text.split(":");
        
        userId = Integer.parseInt(values[0]);
        txnId = Long.parseLong(values[1]);
        description = values[2];
        amount = Double.parseDouble(values[3]);
        double x = Double.parseDouble(values[4]);
        double y = Double.parseDouble(values[5]);
        location = new TransactionLocation(x, y);
        fraud = Boolean.parseBoolean(values[6]);
    }
    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTxnId() {
        return txnId;
    }

    public void setTxnId(long txnId) {
        this.txnId = txnId;
    }
    
    
}
