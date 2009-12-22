package iweb2.ch6.usecase.credit.util;

import iweb2.ch6.usecase.credit.CreditInstance;

import java.util.HashMap;
import java.util.Map;

public class AttributeUtils {

    private static Map<String, AttributeInfo> attributeInfoMap = 
        new HashMap<String, AttributeInfo>();

    static {
        AttributeInfo ai = null;

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_AGE, 1, 10);
        attributeInfoMap.put(ai.getName(), ai);
        
        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_CAR_OWNERSHIP, 0, 1);
        attributeInfoMap.put(ai.getName(), ai);
        
        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_CREDIT_SCORE, 1, 8);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_INCOME_TYPE, 1, 10);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_JOB_CLASS, 1, 5);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_MORTGAGE_DOWN_PAYMENT, 1, 4);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_MOTOR_BICYCLE_OWNERSHIP, 0, 1);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_OTHER_PROPERTY_OWNERSHIP, 0, 1);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_CRIMINAL_RECORD, 0, 1);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_BANKRUPTCY, 0, 1);
        attributeInfoMap.put(ai.getName(), ai);

        ai = new AttributeInfo(
                CreditInstance.ATTR_NAME_RETIREMENT_ACCOUNT, 1, 8);
        attributeInfoMap.put(ai.getName(), ai);
    }

    public static double getNormalizedValue(String attrName, double value) {
        AttributeInfo ai = attributeInfoMap.get(attrName);
        return (value - ai.getMinValue()) / (ai.getMaxValue() - ai.getMinValue());
    }
}
