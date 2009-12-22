package iweb2.ch5.classification.dtree;

public class AttributeUtils {
    
    public static String toString(Object o) {
        String result = null;
        if( o instanceof Double) {
            result = String.valueOf(o);
        }
        else if( o instanceof String) {
            result = (String)o;
        }
        else if ( o instanceof Integer) {
            result = String.valueOf(o);
        }
        
        return result;
    }
    
    public static Double toDouble(Object o) {
        Double result = null;
        if( o instanceof Double) {
            result = (Double) o;
        }
        else if( o instanceof String) {
            result = Double.parseDouble((String)o);
        }
        else if ( o instanceof Integer) {
            result = new Double((Integer)o);
        }
        
        return result;
    }

}
