package iweb2.ch4.utils;

import iweb2.ch4.model.Attribute;

/*
 * Utility methods to simplify operations on attributes.
 */
public class Attributes {
    
    public static Attribute[] createAttributes(String[] attrValues) {
        int n = attrValues.length;
        Attribute[] attrs = new Attribute[n];
        for(int i = 0; i < n; i++) {
            String attrName = "a-" + i;
            Attribute a = new Attribute(attrName, attrValues[i]);
            attrs[i] = a;
        }
        return attrs;
    }

    public static Attribute[] createAttributes(double[] attrValues) {
        int n = attrValues.length;
        Attribute[] attrs = new Attribute[n];
        for(int i = 0; i < n; i++) {
            String attrName = "a-" + i;
            Attribute a = new Attribute(attrName, attrValues[i]);
            attrs[i] = a;
        }
        return attrs;
    }
    
    public static boolean allText(Attribute[] attributes) {
        boolean allText = true;
        for(Attribute a : attributes) {
            if( a.isText() == false ) {
                allText = false;
                break;
            }
        }
        return allText;
    }

    public static boolean allNumeric(Attribute[] attributes) {
        boolean allNumeric = true;
        for(Attribute a : attributes) {
            if( a.isNumeric() == false ) {
                allNumeric = false;
                break;
            }
        }
        return allNumeric;
    }
    
    public static Attribute[] createAttributes(String[] names, String[] values) {
        int n = names.length;
        Attribute[] attributes = new Attribute[n];
        for(int i = 0; i < n; i++) {
            attributes[i] = new Attribute(names[i], values[i]);
        }
        return attributes;
    }
    
    public static Attribute[] createAttributes(String[] names, double[] values) {
        int n = names.length;
        Attribute[] attributes = new Attribute[n];
        for(int i = 0; i < n; i++) {
            attributes[i] = new Attribute(names[i], values[i]);
        }
        return attributes;
    }
    
    public static String[] getNames(Attribute[] attributes) {
        int n = attributes.length;
        String[] names = new String[n];
        for(int i = 0; i < n; i++) {
            Attribute a = attributes[i]; 
            names[i] = a.getName();
        }
        return names;
    }
    
    public static String[] getTextValues(Attribute[] attributes) {
        int n = attributes.length;
        String[] values = new String[n];
        for(int i = 0; i < n; i++) {
            Attribute a = attributes[i]; 
            if(a.isText()) {
                values[i] = a.getTextValue();
            }
            else {
                throw new RuntimeException("Non-text attribute encountered. " + 
                        "Attribute: " + a.toString());
            }
        }
        return values;
    }
    
    public static double[] getNumericValues(Attribute[] attributes) {
        int n = attributes.length;
        double[] values = new double[n];
        for(int i = 0; i < n; i++) {
            Attribute a = attributes[i]; 
            if(a.isNumeric()) {
                values[i] = a.getNumericValue();
            }
            else {
                throw new RuntimeException("Non-numeric attribute encountered. " + 
                        "Attribute: " + a.toString());
            }
        }
        return values;
    }
    
}
