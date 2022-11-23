/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Lemo
 */
public class InvoiceHeader {
    
    private int invoiceNum;
    private Date invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> arr;
    private float total=0;
    
    public InvoiceHeader()
    {
        
    }
    
    public InvoiceHeader(int invoiceNum,Date invoiceDate,String customerName,ArrayList<InvoiceLine> arr)
    {
        this.arr=arr;
        this.customerName=customerName;
        this.invoiceDate=invoiceDate;
        this.invoiceNum=invoiceNum;
    }
    public InvoiceHeader(int invoiceNum,Date invoiceDate,String customerName)
    {
        
        this.customerName=customerName;
        this.invoiceDate=invoiceDate;
        this.invoiceNum=invoiceNum;
    }
    
    public float calcTotalInvoice()
    {
        for(InvoiceLine item :getArr())
        {
            setTotal(getTotal() + item.calcTotalItemPrice());
        }
        return getTotal();
    }

    /**
     * @return the invoiceNum
     */
    public int getInvoiceNum() {
        return invoiceNum;
    }

    /**
     * @param invoiceNum the invoiceNum to set
     */
    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    /**
     * @return the invoiceDate
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the arr
     */
    public ArrayList<InvoiceLine> getArr() {
        return arr;
    }

    /**
     * @param arr the arr to set
     */
    public void setArr(ArrayList<InvoiceLine> arr) {
        this.arr = arr;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }
}
