package logic;

public class Constants {
	
	// order
	public final static int ORDER_DECLINED_STATUS = -1;
	public final static int ORDER_APPROVED_STATUS = 1 ;
	public final static int ORDER_LIMITED_STATUS = -2 ;
	public final static int ORDER_ACTIVE_STATUS = 0 ;
	
	public final static String ORDER_SELL = "SELL" ;
	public final static String ORDER_BUY = "BUY" ;
	
	public final static String ORDER_GTC_TYPE = "GTC" ;
	public final static String ORDER_IOC_TYPE = "IOC" ;
	public final static String ORDER_MPO_TYPE = "MPO" ;
	public final static String ORDER_ADD_TYPE = "ADD" ;
	
	// credit request
	public final static int CREDIT_ACCEPTED_STATUS = 1;
	public final static int CREDIT_PENDING_STATUS = 0;
	public final static int CREDIT_REJECTED_STATUS = -1;
	
	public static final int CREDIT_DEPOSIT_TYPE = 1;
	public static final int CREDIT_WITHDRAW_TYPE = -1;
	
	// symbol
	public static final int SYMBOL_PENDING_STATUS = 0 ;
	public static final int SYMBOL_VERIFIED_STSTUS = 1;
}
