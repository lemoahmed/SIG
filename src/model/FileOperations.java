/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lemo
 */
public class FileOperations {
    public static final String delimiter = ",";
    
    public  ArrayList<InvoiceHeader> readFile(String headerName,String lineName) {
        ArrayList<InvoiceHeader> listOfHeader=new ArrayList<>();
        boolean readNext=true;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            File headerFile = new File(headerName);
            File lineFile = new File(lineName);
            FileReader headerReader = new FileReader(headerFile);
            FileReader lineReader = new FileReader(lineFile);
            BufferedReader headerBuffer = new BufferedReader(headerReader);
            BufferedReader lineBuffer = new BufferedReader(lineReader);
            String headerLine = " ";
            String lineLine = " ";
            String[] tempheader;
            String[] templine;
            while ((headerLine = headerBuffer.readLine()) != null) 
            {
                tempheader = headerLine.split(delimiter);
                InvoiceHeader invh =new InvoiceHeader(Integer.parseInt(tempheader[0]), formatter.parse(tempheader[1]), tempheader[2]);
                ArrayList<InvoiceLine> listOfLine=new ArrayList<>();
                if (readNext==true){
                lineLine = lineBuffer.readLine();
                }
                while (lineLine != null && lineLine.split(delimiter)[0].equals(Integer.toString(invh.getInvoiceNum())) ) 
                {
                    templine=lineLine.split(delimiter);
                    InvoiceLine invl=new InvoiceLine(Integer.parseInt(templine[0]),templine[1] ,Integer.parseInt(templine[2]) , Integer.parseInt(templine[3]));
                    listOfLine.add(invl);
                    lineLine = lineBuffer.readLine();
                    readNext=false;
                }
                invh.setArr(listOfLine); 
                listOfHeader.add(invh);
            }
            headerBuffer.close();
            lineBuffer.close();
            }
            catch(IOException ioe) {
            ioe.printStackTrace();
            }   catch (ParseException ex) {
                    Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
        for(InvoiceHeader H:listOfHeader)
        {
            
            
            System.out.println(Integer.toString(H.getInvoiceNum()));
            System.out.println("{");
            System.out.println("Date ("+formatter.format(H.getInvoiceDate())+"), "+H.getCustomerName());
            for(InvoiceLine L:H.getArr())
            {
                System.out.println(L.getItemName()+", "+Integer.toString(L.getItemPrice())+", "+Integer.toString(L.getCount()));
            }
            System.out.println("}");
        }
        
        
        
            return listOfHeader;
  }
    public void writeFile(ArrayList<InvoiceHeader> arrHeaders) 
    {
        FileWriter fstream = null;
        FileWriter fstream2 = null;
        DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String loc = "lemo.csv";
            String loc2 = "lemo2.csv";
            fstream = new FileWriter(loc, false);
            fstream2 = new FileWriter(loc2, false);
            BufferedWriter out = new BufferedWriter(fstream);
            BufferedWriter out2 = new BufferedWriter(fstream2);
            for(InvoiceHeader header:arrHeaders)
            {
                String[]s={Integer.toString(header.getInvoiceNum()),date_format.format(header.getInvoiceDate()),header.getCustomerName()};
                out.write(s[0]);
                out.write(",");
                out.write(s[1]);
                out.write(",");
                out.write(s[2]);
                out.newLine();
                for(InvoiceLine line:header.getArr())
                {
                    String[]s2={Integer.toString(line.getInvoiceNum()),line.getItemName(),Integer.toString(line.getItemPrice()),Integer.toString(line.getCount())};
                    out2.write(s2[0]);
                    out2.write(",");
                    out2.write(s2[1]);
                    out2.write(",");
                    out2.write(s2[2]);
                    out2.write(",");
                    out2.write(s2[3]);
                    out2.newLine();
                }
            } 
            out.close();
            out2.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
                fstream2.close();
            } catch (IOException ex) {
                Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}
