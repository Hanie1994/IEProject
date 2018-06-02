package cli;

import java.util.Scanner;

import service.JsonServices;

import logic.Core;

public class CLI {

	public static void main(String[] args) {
		
		Core core = new Core();
		JsonServices service = new JsonServices();
		
		Scanner sc = new Scanner(System.in);
		String line ;
		
		while(sc.hasNextLine()){
			//System.out.println();
			line = sc.nextLine();
			System.out.println(line);
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
				else if(paras[0].equals("list")){
					ans = service.getSymbolList();
					ans += "\n";
					ans += service.getSymbolInfoByName("RENA1");
					ans += "\n";
					ans += "\n";
					ans += service.getCustomerEconomicInfoById("2");
					ans += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
					ans += "\n";
					ans += service.getMarketInfo();
					ans += "\n~~~~~~~~~~~~~Active~~~~~~~~~~~~\n";
					ans += service.getCustomerActiveOrdersById("2");
					ans += "\n~~~~~~~~~~~~~Approved~~~~~~~~~~\n";
					ans += service.getCustomerApprovedOrdersById("2");
					ans += "\n~~~~~~~~~~~~~Declined~~~~~~~~~~\n";
					ans += service.getCustomerDeclinedOrdersById("2");
					ans += "\n~~~~~~~~~~~~~Credti~~~~~~~~~~~~\n";
					ans += service.getCustomerCreditById("2");
				}
				else if(paras[0].equals("filter")){
					ans = service.filterExchanges(paras[1], paras[2], paras[3], paras[4], paras[5], paras[6], paras[7]);
				}
				else if(paras[0].equals("credit")){
					
				}
				else if(paras[0].equals("symbol")){
					core.addSymbol(paras[1], paras[2]);
				}
				else if(paras[0].equals("verify")){
					ans = core.verifySymbol(paras[1]);
				}
				else if(paras[0].equals("charge")){
					ans = core.chargeSymbol(paras[1], paras[2], paras[3], Integer.valueOf(paras[4]));
				}
				else if(paras[0].equals("request")){
					ans += core.makeCreditRequest(paras[1], Integer.valueOf(paras[2]), Integer.valueOf(paras[3]));
				}
				else if(paras[0].equals("accept")){
					ans += core.acceptCreditRequest(Integer.valueOf(paras[1]));
				}
				else if(paras[0].equals("reject")){
					ans += core.rejectCreditRequest(Integer.valueOf(paras[1]));
				}
			} catch (Exception e) {
				System.out.println("\t" + e.getClass().getName());
			}
			System.out.println("\t" + ans);
			//core.log();
			//core.check();
		}
		//core.log();
		//core.check();
		sc.close();
	}

}
