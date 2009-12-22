package iweb2.ch6.usecase.credit.util;

public class AttributeInfo {
    private String name;
    private int minValue;
    private int maxValue;
    
    public AttributeInfo(String name, int min, int max) {
        this.name = name;
        this.minValue = min;
        this.maxValue = max;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getMinValue() {
        return minValue;
    }
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
    public int getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
    
}
