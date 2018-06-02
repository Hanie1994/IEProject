package service;

import logic.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SampleDataLoader {
    private static SampleDataLoader instance;

    public static SampleDataLoader getInstance(){
        if(instance == null)
            instance = new SampleDataLoader();
        return instance;
    }
    public String load(InputStream input){

        Core core = Core.getInstance();
        String line ;
        //BufferedReader br = new BufferedReader(input);

        /*try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            if (is != null) {
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n" );
                }
            }
        }finally {
            try { is.close(); } catch (Throwable ignore) {}
        }*/


        String resp = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while ((line = reader.readLine()) != null){
                String[] paras = line.split("\\s+");
                String ans = "";
                try {
                    if(paras[0].equals("add")){
                        core.addCustomer(paras[1], paras[2], paras[3], paras[4], paras[5]);
                        ans = "user created" ;
                    }
                    else if(paras[0].equals("deposit")){
                        core.depositCustomer(paras[1], Integer.parseInt(paras[2]));
                        ans = "deposit OK" ;
                    }
                    else if(paras[0].equals("withdraw")){
                        core.withdrawCustomer(paras[1], Integer.parseInt(paras[2]));
                        ans = "withdraw OK" ;
                    }
                    else if(paras[0].equals("sell")){
                        ans = core.sellOrder(paras[1], paras[2], paras[3], Integer.parseInt(paras[4]), paras[5]);
                        //"sell OK" ;
                    }
                    else if(paras[0].equals("buy")){
                        ans = core.buyOrder(paras[1], paras[2], paras[3], Integer.parseInt(paras[4]), paras[5]);
                        //ans = "buy OK" ;
                    }

                } catch (Exception e) {
                    resp += "\t" + e.getClass().getName() + "\n";
                }
                //System.out.println("\t" + ans);
                resp += ans + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}