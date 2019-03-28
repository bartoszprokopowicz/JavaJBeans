package bean;

import java.beans.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KalendarzBeanBeanInfo extends SimpleBeanInfo {

    private static final int PROPERTY_title = 0;
    private static final int PROPERTY_textAreaSize = 1;
    private static final int PROPERTY_dateLimit = 2;
    private static final int defaultPropertyIndex = -1;
    private static final int defaultEventIndex = -1;

    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(KalendarzBean.class, KalendarzBeanCustomizer.class);
        return beanDescriptor;
    }
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];
        try {
            properties[PROPERTY_title] = new PropertyDescriptor("title", KalendarzBean.class, "getTitle", "setTitle");
            properties[PROPERTY_textAreaSize] = new PropertyDescriptor("memoArea", KalendarzBean.class, "getMemoAreaSize", "setMemoAreaSize");
            properties[PROPERTY_dateLimit] = new PropertyDescriptor("yearFromTo", KalendarzBean.class, "getYearFromTo", "setYearFromTo");
        } catch (IntrospectionException ex) {
            Logger.getLogger(KalendarzBeanBeanInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //properties[PROPERTY_dateLimit] = new PropertyDescriptor("memoArea",Bean.KalendarzBean.class, "getMemoAreaSize","setMemoAreaSize");
        return properties;
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] eventSets = new EventSetDescriptor[0];
        return eventSets;
    }
    public MethodDescriptor[] getMethodDescriptors() {
        MethodDescriptor[] methods = new MethodDescriptor[0];
        return methods;
    }
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }
}
