package com.anant.excel;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        List<MyRow> lines = ReadExcelFileToList.readExcelData("C:\\Users\\anantpan\\Downloads\\Anant Per Fin\\anant consolidated 14-19oct.xlsx");
        //printTxnLines(lines);     
        //findINFT(lines);        
        //findOtherBankATMWithdrawals(lines);
        //findDebitCardTransactionsVPSIPS(lines);
        //findGeneric(lines, "Amazon");
        
        //printForOneMonthYear(lines, "08/2019");
        
        printForMonthYearRange(lines, "01/2014", "12/2019");
        
    }

    private static void printForMonthYearRange(List<MyRow> lines,
			String fromMonthYear, String toMonthYear) {
    	
    	List<String> monthYearList = Util.monthYearListFromTo(fromMonthYear, toMonthYear);
    	System.out.println("\nMonths List: "+monthYearList);
    	
    	for(String monthYear: monthYearList){
    		printForOneMonthYear(lines, monthYear);
    	}
    	
	}

    
	private static void printForOneMonthYear(List<MyRow> lines, String monthYearString) {
    	double creditSumOfMonth = 0.0;
    	double debitSumOfMonth = 0.0;
    	for(MyRow row : lines){	
    		String transactionDate = row.getTransactionDate();
			if(transactionDate.endsWith(monthYearString)){
				System.out.println(row);
				creditSumOfMonth+=row.getCreditAmount();
				debitSumOfMonth+=row.getDebitAmount();
			}			
		}
    	System.out.println("Total Credit of month "+monthYearString+":\t"+creditSumOfMonth);
    	System.out.println("Total Debit of month "+monthYearString+":\t"+debitSumOfMonth);
    	System.out.println("\n");
	}

	private static void findDebitCardTransactionsVPSIPS(List<MyRow> lines) {
		for(MyRow row : lines){	
			String remarks = row.getRemarks();
			if(remarks.contains("VPS") || remarks.contains("IPS")){
				System.out.println(row);
			}			
		}
	}

	// VAT or MAT or NFS
	private static void findOtherBankATMWithdrawals(List<MyRow> lines) {
		for(MyRow row : lines){	
			String remarks = row.getRemarks();
			if(remarks.contains("VAT/") || remarks.contains("MAT/") || remarks.contains("NFS/")){
				System.out.println(row);
			}			
		}
	}

	private static void printTxnLines(List<MyRow> lines) {
		for(MyRow row : lines){
			System.out.println(row);
		}
	}
	
	private static void findINFT(List<MyRow> lines){
		for(MyRow row : lines){			
			if(row.getRemarks().contains("INFT")){
				System.out.println(row);
			}			
		}
	}
	
	private static void findGeneric(List<MyRow> lines, String string){
		double totalDebit = 0.0;
		for(MyRow row : lines){			
			if(row.getRemarks().contains(string)){
				System.out.println(row);
				totalDebit+= row.getDebitAmount();
			}			
		}
		System.out.println("Total Debit of above: "+ totalDebit);
		
	}
	
}
