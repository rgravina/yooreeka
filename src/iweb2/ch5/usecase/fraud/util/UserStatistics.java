package iweb2.ch5.usecase.fraud.util;

import iweb2.ch5.usecase.fraud.data.TransactionLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Holds user-specific statistics that are calculated from training data.
 */
public class UserStatistics implements java.io.Serializable {
    
    private static final long serialVersionUID = -7537387975282866317L;
    
    private int userId;
    private double txnAmtMin;
    private double txnAmtMax;
    private Map<String, String[]> descriptionTokensMap; 
    private TransactionLocation locationCentroid;
    private double locationMinX;
    private double locationMaxX;
    private double locationMinY;
    private double locationMaxY;
    
    public UserStatistics() {
        descriptionTokensMap = new HashMap<String, String[]>();
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getTxnAmtMin() {
        return txnAmtMin;
    }

    public void setTxnAmtMin(Double txnAmountMin) {
        this.txnAmtMin = txnAmountMin;
    }

    public Double getTxnAmtMax() {
        return txnAmtMax;
    }

    public void setTxnAmtMax(Double txnAmountMax) {
        this.txnAmtMax = txnAmountMax;
    }

    public Set<String> getDescriptions() {
        return descriptionTokensMap.keySet();
    }

    public void setDescriptions(Set<String> descriptions) {
        descriptionTokensMap.clear();
        for(String d : descriptions) {
            this.descriptionTokensMap.put(d, null);
        }
    }

    public void setDescriptionTokens(String d, String[] tokens) {
        this.descriptionTokensMap.put(d, tokens);
    }
    
    public String[] getDescriptionTokens(String d) {
        return this.descriptionTokensMap.get(d);
    }
    
    public TransactionLocation getLocationCentroid() {
        return locationCentroid;
    }

    public void setLocationCentroid(TransactionLocation locationCentroid) {
        this.locationCentroid = locationCentroid;
    }

    public double getLocationMinX() {
        return locationMinX;
    }

    public void setLocationMinX(double locationMinX) {
        this.locationMinX = locationMinX;
    }

    public double getLocationMaxX() {
        return locationMaxX;
    }

    public void setLocationMaxX(double locationMaxX) {
        this.locationMaxX = locationMaxX;
    }

    public double getLocationMinY() {
        return locationMinY;
    }

    public void setLocationMinY(double locationMinY) {
        this.locationMinY = locationMinY;
    }

    public double getLocationMaxY() {
        return locationMaxY;
    }

    public void setLocationMaxY(double locationMaxY) {
        this.locationMaxY = locationMaxY;
    }
    
    
    @Override
	public String toString() {
        return "[userId="+userId+
            ", txnAmtMin=" + txnAmtMin +
            ", txnAmtMax=" + txnAmtMax +
            ", locationMinX=" + locationMinX +
            ", locationMaxX=" + locationMaxX +
            ", locationMinY=" + locationMinY +
            ", locationMaxY=" + locationMaxY +            
            ", descriptions=" + descriptionTokensMap.keySet().toString() +
            ", locationCentroid=" + locationCentroid.toString() +
            "]";
    }
    
}
