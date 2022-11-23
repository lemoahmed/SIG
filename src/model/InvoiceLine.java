/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lemo
 */
public class InvoiceLine extends InvoiceHeader{
    private int invoiceNum;
    private String itemName;
    private int itemPrice;
    private int count;
    private float total;
    
    public InvoiceLine()
    {
        
    }
    public InvoiceLine(int invoiceNum ,String itemName,int itemPrice,int count)
    {
        this.invoiceNum=invoiceNum;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.count=count;
    
    }
    public float calcTotalItemPrice()
    {
        setTotal(getItemPrice() * getCount());
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
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemPrice
     */
    public int getItemPrice() {
        return itemPrice;
    }

    /**
     * @param itemPrice the itemPrice to set
     */
    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
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