package service;

import dao.ExchangeDao;
import model.Exchange;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class csvBackup {

    private static csvBackup instance;

    public csvBackup(){

    }

    public static csvBackup getInstance(){
        if(instance == null)
            instance = new csvBackup();
        return instance;
    }

    //public String getBackup(){
        public String make_backup() {
            // ,Buyer Remained Money,Seller Current Money
            // + "," + e.getBuyPrice() + "," +
            String NEW_LINE_SEPARATOR = "\n";
            String file_header = "buyer,Seller,instrument,type of trade,quantity\n";
            String outResult = file_header;

            try{
                
                ArrayList<Exchange> allExchanges = ExchangeDao.getInstance().getAllExchanges();

                PrintWriter pw = new PrintWriter(new File("backup.csv"));
                StringBuilder sb = new StringBuilder();

                sb.append(file_header);

                for (Exchange e : allExchanges) {

                    String line = e.getBuyerId() + "," + e.getSellerId() + "," + e.getSymbol() + "," + e.getType() + "," +
                            e.getQuantity();
                    outResult = line + NEW_LINE_SEPARATOR;            

                    System.out.println(outResult);
                    sb.append(outResult);


                }

                pw.write(sb.toString());
                pw.close();
                System.out.println("done!");

            }catch(FileNotFoundException e){
                System.out.println("Backup File Not Found!!!");
            }

            return outResult;

        }
    //}
}