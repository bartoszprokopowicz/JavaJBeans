package bean;

import javafx.util.Pair;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.DefaultTableModel;

public class KalendarzBean extends JPanel implements Serializable {

    private static final long serialVersionUID = 1L;

    private DefaultTableModel model;
    private Calendar cal = new GregorianCalendar();
    
    private String title = "Calendar"; //simple
    private JTextArea memoArea; //bound
    private Dimension yearFromTo = new Dimension(0, 3000);//constrained property
    private JLabel label;

    private int column, row, value;
    
    ArrayList<Pair<GregorianCalendar,String>> memo;
    

    private PropertyChangeSupport propertyChange = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);
    protected transient ChangeEvent changeEvent = null;
    protected EventListenerList listenerList = new EventListenerList();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        //this.setTitle(title);
    }
    
    public Dimension getMemoAreaSize() {
        Dimension temp = new Dimension(memoArea.getColumns(), memoArea.getRows());
        return temp;
    }

    public void setMemoAreaSize(Dimension newSize) {
        Dimension oldMemoAreaSize = getMemoAreaSize();
        memoArea.setColumns(newSize.width);
        memoArea.setRows(newSize.height);
        propertyChange.firePropertyChange("memoArea", oldMemoAreaSize, newSize);
    }
    
    public Dimension getYearFromTo() {
        return yearFromTo;
    }

    public void setYearFromTo(Dimension newYears) {
        Dimension oldYearFromTo = yearFromTo;
        yearFromTo = newYears;
        propertyChange.firePropertyChange("yearFromTo", oldYearFromTo, newYears);
    }
    
    

    public KalendarzBean() {

        this.setLayout(new BorderLayout());

        memo = new ArrayList<>();
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton b1 = new JButton("<-");
        b1.addActionListener((ActionEvent ae) -> {
            cal.add(Calendar.MONTH, -1);
            if (cal.get(Calendar.YEAR) > yearFromTo.width && cal.get(Calendar.YEAR) < yearFromTo.height) {
                updateMonth();
            }else
                cal.add(Calendar.MONTH, +1);
        });

        JButton b2 = new JButton("->");
        b2.addActionListener((ActionEvent ae) -> {
            cal.add(Calendar.MONTH, +1);
            if (cal.get(Calendar.YEAR) > yearFromTo.width && cal.get(Calendar.YEAR) < yearFromTo.height) {
                updateMonth();
            }else
                cal.add(Calendar.MONTH, -1);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(b1,BorderLayout.WEST);
        panel.add(label,BorderLayout.CENTER);
        panel.add(b2,BorderLayout.EAST);


        String [] columns = {"Nd","Pon","Wt","Śr","Czw","Pt","Sb"};
        model = new DefaultTableModel(null, columns);
        JTable table = new JTable(model){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable src = (JTable) e.getSource();
                row = src.getSelectedRow();
                column = src.getSelectedColumn();
                value = (int) src.getValueAt(row,column);
                memoArea.setText(null);
                memoArea.setText(getMemo(value));
                System.out.println("elo");
            }
        });
        
        memoArea = new JTextArea(10,10);
        memoArea.setLineWrap(true);
        memoArea.setWrapStyleWord(true);

        JScrollPane pane = new JScrollPane(table);
        JScrollPane memoSP = new JScrollPane(memoArea);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        //memoSP.setSize(100,100);
        panel2.add(memoSP,BorderLayout.CENTER);

        JButton add = new JButton("Zmień zawartość");

        add.addActionListener(e -> {
            setMemo();
        });

        panel2.add(add, BorderLayout.NORTH);

        this.add(panel,BorderLayout.NORTH);
        this.add(pane,BorderLayout.CENTER);
        this.add(panel2, BorderLayout.SOUTH);
        //this.add(new Button(),FlowLayout.RIGHT);

        this.setSize(this.getPreferredSize());
        this.updateMonth();
    }

    private void setMemo() {
        int day = (int) model.getValueAt(row,column);
        GregorianCalendar temp = new GregorianCalendar();
        temp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), day);
        memo.add(new Pair<>(temp, memoArea.getText()));
    }

    private void updateMonth() {

        cal.set(Calendar.DAY_OF_MONTH, 1);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        int year = cal.get(Calendar.YEAR);
        label.setText(month + " " + year);

        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

        model.setRowCount(0);
        if (weeks <= 4) {
            weeks = weeks + 2;
        }
        if(weeks == 5){
            weeks = weeks + 1;
        }
        model.setRowCount(weeks);

        int i = startDay - 1;
        for (int day = 1; day <= numberOfDays; day++) {
            model.setValueAt(day, i / 7, i % 7);
            i = i + 1;
        }
        
    }

    private String getMemo(int day){
        GregorianCalendar temp = new GregorianCalendar();
        temp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), day);
        for(Pair<GregorianCalendar, String> date: memo){
            if(date.getKey().get(Calendar.YEAR) == temp.get(Calendar.YEAR))
                if(date.getKey().get(Calendar.MONTH) == temp.get(Calendar.MONTH))
                    if(date.getKey().get(Calendar.DATE) == temp.get(Calendar.DATE))
                        return date.getValue();
        }
        return null;
    }
}
