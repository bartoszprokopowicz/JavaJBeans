/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.Customizer;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author barte
 */
public class KalendarzBeanCustomizer extends JPanel implements Customizer{
    
    private static final long serialVersionUID = 1L;
    
    private KalendarzBean bean;
    private PropertyChangeSupport propertyChange;
    
    public KalendarzBeanCustomizer()
    {
        setLayout(new FlowLayout());
        JButton b1 = new JButton("Zmiana tytulu");
        JButton b2 = new JButton("Zmiana rozmiaru pola tekstu");
        JButton b3 = new JButton("Zmiana ram czasowych od do");
        
        JTextField txt = new JTextField();
        txt.setColumns(10);
        
        b1.addActionListener((ActionEvent ae) -> {
            String oldText = bean.getTitle();
            bean.setTitle(txt.getText());
            firePropertyChange("title", oldText, txt.getText());
        });
        
        this.add(txt);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        
    }
    
    @Override
    public void setObject(Object bean) {
        this.bean = (KalendarzBean) bean;
        propertyChange = new PropertyChangeSupport(this.bean);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
