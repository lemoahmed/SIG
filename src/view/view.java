/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.*;

/**
 *
 * @author Lemo
 */
public class view extends JFrame{
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    JFrame mainFrame;
    JPanel leftPanel;
    JPanel rightPanel;
    JTable leftTable;
    JLabel invNumLable;
    JTextField invNumValue;
    JLabel dateLable;
    JTextField dateValue;
    JLabel customerNameLable;
    JTextField customerNameValue;
    JLabel totalLable;
    JTextField totalValue;
    JTable rightTable;
    JButton showButton;
    JButton addInvoiceButton;
    JButton deleteInvoiceButton;
    JButton saveButton;
    JButton cancelButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem save;
    JMenuItem load;
    DefaultTableModel model ; 
    public void view1( ArrayList<InvoiceHeader> arrHeaders1)
    {
        mainFrame=new JFrame("SIG");
        mainFrame.setSize(1000, 900);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1,2));
        leftPanel=new JPanel();
        rightPanel=new JPanel();
        menuBar=new JMenuBar();
        save=new JMenuItem("save");
        load=new JMenuItem("load");
        menu=new JMenu("file");
        menu.add(save);
        menu.addSeparator();
        menu.add(load);
        menuBar.add(menu);
        model = new DefaultTableModel();
        model.addColumn("invoiceNum");
        model.addColumn("invoiceDate");
        model.addColumn("customerName");
        model.addColumn("invoice total");
        leftTable=new JTable(model){
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {                
                return false;               
            };
        };
        for(InvoiceHeader header:arrHeaders1){
            model.addRow(new Object[]{header.getInvoiceNum(), header.getInvoiceDate(),header.getCustomerName(),header.calcTotalInvoice()});
        }
        leftTable.setRowSelectionAllowed(true);
        leftTable.setRowSelectionInterval(0, 0);
        showButton=new JButton("show details");
        showButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showDetails(arrHeaders1);
            }
        });
        this.showDetails(arrHeaders1);
        addInvoiceButton=new JButton("Add New Invoice");
        addInvoiceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addNewInvoice(arrHeaders1);
            }
        });
        deleteInvoiceButton=new JButton("Delete Invoice");
        deleteInvoiceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               int chooice=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this invoice?", "Delete...",JOptionPane.YES_NO_OPTION);
               if(chooice==JOptionPane.YES_OPTION)
               {
                   removeInvoice(arrHeaders1);
               }
            }
        });
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               FileOperations FO=new FileOperations();
               FO.writeFile(arrHeaders1);
               JOptionPane.showMessageDialog(null, "File saved successfully",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               mainFrame.setVisible(false);
               FileOperations FO=new FileOperations();
               ArrayList<InvoiceHeader> arrHead;
               arrHead=FO.readFile("lemo.csv","lemo2.csv");
               view1(arrHead);
               JOptionPane.showMessageDialog(null, "File loaded",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
               
            }
        });
        model.getColumnName(WIDTH);
        leftTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        leftTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        leftPanel.add(leftTable);
        leftPanel.add(showButton);
        leftPanel.add(addInvoiceButton);
        leftPanel.add(deleteInvoiceButton);
        leftPanel.setPreferredSize(new Dimension(10, 10));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(leftPanel);
        mainFrame.add(rightPanel);
        
        mainFrame.setVisible(true);

    }
    public void showDetails(ArrayList<InvoiceHeader> arrHeaders1)
    {

        invNumLable=new JLabel("Invoice Number");
        invNumValue=new JTextField(leftTable.getValueAt(leftTable.getSelectedRow(), 0).toString());
        invNumValue.setEditable(false);
        dateLable=new JLabel("Invoice Date");
        dateValue=new JTextField(formatter.format(arrHeaders1.get(leftTable.getSelectedRow()).getInvoiceDate()));
        customerNameLable=new JLabel("Customer Name");
        customerNameValue=new JTextField(arrHeaders1.get(leftTable.getSelectedRow()).getCustomerName(),10);
        totalLable=new JLabel("Total");
        totalValue=new JTextField(leftTable.getValueAt(leftTable.getSelectedRow(), 3).toString());
        totalValue.setEditable(false);
        DefaultTableModel model2 = new DefaultTableModel();
        rightTable=new JTable(model2){
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int row, int column) {                
                return false;               
            };
        };
        
        model2.addColumn("invoiceNum");
        model2.addColumn("item name");
        model2.addColumn("item price");
        model2.addColumn("count");
        model2.addColumn("item total");
        for(InvoiceLine line:arrHeaders1.get(leftTable.getSelectedRow()).getArr())
        {
            model2.addRow(new Object[]{line.getInvoiceNum(),line.getItemName(),line.getItemPrice(),line.getCount(),line.calcTotalItemPrice()});
        }
        saveButton=new JButton("save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int chooice=JOptionPane.showConfirmDialog(null, "Are you sure you want to save changes?", "Save chages...",JOptionPane.YES_NO_OPTION);
                if(chooice==JOptionPane.YES_OPTION)
                {
                    saveChanges(arrHeaders1);
                }
            }
        });
        cancelButton=new JButton("cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int chooice=JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel changes?", "cancel chages...",JOptionPane.YES_NO_OPTION);
                if(chooice==JOptionPane.YES_OPTION)
                {
                    cancelChanges(arrHeaders1);
                }
            }
        });
        rightPanel.removeAll();
        rightPanel.add(invNumLable);
        rightPanel.add(invNumValue);
        rightPanel.add(dateLable);
        rightPanel.add(dateValue);
        rightPanel.add(customerNameLable);
        rightPanel.add(customerNameValue);
        rightPanel.add(totalLable);
        rightPanel.add(totalValue);
        rightPanel.add(rightTable);
        rightPanel.add(saveButton);
        rightPanel.add(cancelButton);
        
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }
    public void addNewInvoice(ArrayList<InvoiceHeader> arrHeaders1)
    {
                    
        JFrame addInvoiceForm=new JFrame("Add Invoice");
        JPanel p=new JPanel(new FlowLayout());
        addInvoiceForm.setSize(500, 400);
        JLabel enterInvNum=new JLabel("Enter Invoive Num");
        JTextField invNum=new JTextField("0", 10);
        JLabel enterInvDate=new JLabel("Enter Invoive Date");
        JTextField invDate=new JTextField(10);
        JLabel enterCustomername=new JLabel("Enter Customer Name");
        JTextField customerName=new JTextField(10);
        JLabel itemsInfo=new JLabel("ITEMS-------------------------------------------------------------------------------------------------");
        JLabel enterItemName=new JLabel("Enter Item Name");
        JTextField ItemName=new JTextField(10);
        JLabel enterItemPrice=new JLabel("Enter Item Price");
        JTextField ItemPrice=new JTextField("0", 10);
        JLabel enterItemCount=new JLabel("Enter Item Count");
        JTextField ItemCount=new JTextField("0", 10);
        JButton ok=new JButton("ok");
        ok.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int chooice=JOptionPane.showConfirmDialog(null, "Are you sure you want to add this invoice?", "Add...",JOptionPane.YES_NO_OPTION);
            if(chooice==JOptionPane.YES_OPTION){
            ArrayList<InvoiceLine> arrNewLine=new ArrayList<>();
            InvoiceLine newLine=new InvoiceLine(Integer.parseInt(invNum.getText()), ItemName.getText(), Integer.parseInt(ItemPrice.getText()), Integer.parseInt(ItemCount.getText()));
            arrNewLine.add(newLine);
        try {
                InvoiceHeader addedHeader = new InvoiceHeader(Integer.parseInt(invNum.getText()), formatter.parse(invDate.getText()), customerName.getText(),arrNewLine);
                arrHeaders1.add(addedHeader);       
                model.addRow(new Object[]{addedHeader.getInvoiceNum(),addedHeader.getInvoiceDate(),addedHeader.getCustomerName(),addedHeader.calcTotalInvoice()});
            } catch (ParseException ex) {
                Logger.getLogger(view.class.getName()).log(Level.SEVERE, null, ex);
            }
                addInvoiceForm.dispatchEvent(new WindowEvent(addInvoiceForm, WindowEvent.WINDOW_CLOSING));
                JOptionPane.showMessageDialog(null, "Invoice added successfully",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
        });
        p.add(enterInvNum);
        p.add(invNum);
        p.add(enterInvDate);
        p.add(invDate);
        p.add(enterCustomername);
        p.add(customerName);
        p.add(itemsInfo);
        p.add(enterItemName);
        p.add(ItemName);
        p.add(enterItemPrice);
        p.add(ItemPrice);
        p.add(enterItemCount);
        p.add(ItemCount);
        p.add(ok);
        addInvoiceForm.add(p);
        
        addInvoiceForm.setVisible(true);
    }
    public void removeInvoice(ArrayList<InvoiceHeader> arrHeaders1)
    {
        arrHeaders1.remove(leftTable.getSelectedRow());
        model.removeRow(leftTable.getSelectedRow());
        JOptionPane.showMessageDialog(null, "Invoice deleted successfully",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void saveChanges(ArrayList<InvoiceHeader> arrHeaders1)
    {
        arrHeaders1.get(leftTable.getSelectedRow()).setCustomerName(customerNameValue.getText());
        try {
            arrHeaders1.get(leftTable.getSelectedRow()).setInvoiceDate(formatter.parse(dateValue.getText()));
            model.setValueAt(formatter.parse(dateValue.getText()), leftTable.getSelectedRow(), 1);
        } catch (ParseException ex) {
            Logger.getLogger(view.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.setValueAt(customerNameValue.getText(), leftTable.getSelectedRow(), 2);
        JOptionPane.showMessageDialog(null, "This invoice has been changed",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void cancelChanges(ArrayList<InvoiceHeader> arrHeaders1)
    {
        dateValue.setText(formatter.format(arrHeaders1.get(leftTable.getSelectedRow()).getInvoiceDate()));
        customerNameValue.setText(arrHeaders1.get(leftTable.getSelectedRow()).getCustomerName());
        JOptionPane.showMessageDialog(null, "Changes has been canceled",
                             "Info", JOptionPane.INFORMATION_MESSAGE);
    }
            
}
