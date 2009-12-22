package iweb2.ch4.model;

/**
 * Attribute for text or numeric values.
 */
public class Attribute {
    
    private String name;
    private Object value;
    
    public Attribute(String name, String textValue) {
        init(name, textValue);
    }
    
    public Attribute(String name, Double numericValue) {
        init(name, numericValue);
    }
    
    private void init(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getTextValue() {
        return (String)value;
    }
    
    public Double getNumericValue() {
        return (Double)value;
    }
    
    public boolean isNumeric() {
        if( value instanceof java.lang.Double ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isText() {
        if( value instanceof java.lang.String ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
	public String toString() {
        return "[name="+this.name + 
            ", value=" + value + 
            ", isText=" + this.isText() + 
            ", isNumeric=" + this.isNumeric() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Attribute other = (Attribute) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }


}
