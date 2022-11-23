/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig;
import java.util.ArrayList;
import model.*;
import view.view;

/**
 *
 * @author Lemo
 */
public class SIG {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       FileOperations FO=new FileOperations();
       String csvFileHeader = "InvoiceHeader.csv";
       String csvFileLine = "InvoiceLine.csv";
       ArrayList<InvoiceHeader> arrHeaders1=new ArrayList<>();
       arrHeaders1=FO.readFile(csvFileHeader,csvFileLine);
       view v=new view();
       v.view1(arrHeaders1);
        
}
    }
