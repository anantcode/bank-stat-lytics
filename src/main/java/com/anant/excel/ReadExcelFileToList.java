/*
 * Reference used: https://www.journaldev.com/2562/apache-poi-tutorial
 * Anant
 */

package com.anant.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelFileToList {
	
	//E5 is rowIndex = 4, columnIndex = 4;  //for reference.
	
	//Transaction Date: 	Column D	columnIndex = 3
	//Transaction Remarks:	Column F	columnIndex = 5
	//Withdrawal Amount:	Column G	columnIndex = 6
	//Deposit Amount:		Column H	columnIndex = 7
	//Balance:				Cokumn I	columnIndex = 8
	
	final static int COLUMN_SLNO = 1;
	final static int COLUMN_TXN_DATE = 3;
	final static int COLUMN_TXN_REMARKS = 5;
	final static int COLUMN_DEBIT_AMOUNT = 6;
	final static int COLUMN_CREDIT_AMOUNT = 7;
	final static int COLUMN_BALANCE = 8;
	final static String LAST_ROW_INDICATOR = "Legend"; //after last transaction row, in sale column as slno, this string would be present.
	
	public static List<MyRow> readExcelData(String fileName) {
		List<MyRow> transactionList = new ArrayList<MyRow>();
		
		try {
			//Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);
			
			//Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if(fileName.toLowerCase().endsWith("xlsx")){
				workbook = new XSSFWorkbook(fis);
			}else if(fileName.toLowerCase().endsWith("xls")){
				workbook = new HSSFWorkbook(fis);
			}
			
			//Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();
			
			//loop through each of the sheets
			for(int i=0; i < numberOfSheets; i++){
				
				//Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);
				System.out.println("Reading Sheet with Name: "+workbook.getSheetName(i)); 
				String accountNo = "";
								
				//every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				
				Row tempRow = rowIterator.next();
				while(!isReachedHeaderRow(tempRow) && rowIterator.hasNext()){
					if(accountNo.equalsIgnoreCase("")){
						accountNo = tryToReadAccountNo(tempRow);
					}
					tempRow = rowIterator.next(); //skipping rows until reaching real table of transactions.
				}
				
				while (rowIterator.hasNext()) 
		        {					
					String slno = "";
					String valueDate = "";
					String transactionDate = "";
					String chequeNo = "";
					String remarks = "";
					double debitAmount = 0.0;
					double creditAmount = 0.0;
					double balance = 0.0;
					
					//Get the row object
					Row row = rowIterator.next();
					
					//if row no more has transaction lines, break
					if(isTransactionLinesEnded(row)){
						break;
					}
					
					//System.out.println("rowNo.getPhysicalNumberOfCells: "+ row.getPhysicalNumberOfCells());
					
					//Every row has columns, get the column iterator and iterate over them
					Iterator<Cell> cellIterator = row.cellIterator();
					
		            while (cellIterator.hasNext()) 
		            {
		            	//Get the Cell object
		            	Cell cell = cellIterator.next();
		            	
//		            	switch(cell.getCellType()){
//		            	case Cell.CELL_TYPE_STRING:
//		            		if(shortCode.equalsIgnoreCase("")){
//		            			shortCode = cell.getStringCellValue().trim();
//		            		}else if(name.equalsIgnoreCase("")){
//		            			//2nd column
//		            			name = cell.getStringCellValue().trim();
//		            		}else{
//		            			//random data, leave it
//		            			System.out.println("Random data::"+cell.getStringCellValue());
//		            		}
//		            		break;
//		            	case Cell.CELL_TYPE_NUMERIC:
//		            		System.out.println("Random data::"+cell.getNumericCellValue());
//		            	}
		            	
		            	try{
			            	switch(cell.getColumnIndex()){
		            		case COLUMN_SLNO:
		            			slno = cell.getStringCellValue().trim();
		            			break;
		            		case COLUMN_TXN_DATE:
		            			transactionDate = cell.getStringCellValue().trim();
		            			break;
		            		case COLUMN_TXN_REMARKS:
		            			remarks = cell.getStringCellValue().trim();
		            			break;
		            		case COLUMN_DEBIT_AMOUNT:
		            			debitAmount = cell.getNumericCellValue();
		            			//System.out.println("debit: "+ cell.getNumericCellValue());
		            			break;
		            		case COLUMN_CREDIT_AMOUNT:
		            			creditAmount = cell.getNumericCellValue();
		            			break;
		            		case COLUMN_BALANCE:
		            			balance = cell.getNumericCellValue();
		            			break;
			            	}
		            	}catch(Exception e){
		            		e.printStackTrace();
		            	}

		            	
		            } //end of cell iterator
		            //System.out.println("before creating object, accountNo: "+accountNo);
		            MyRow c = new MyRow(slno, "", transactionDate, "", remarks, debitAmount, creditAmount, balance, accountNo);
		            //System.out.println("Record: "+c);
		            transactionList.add(c);
		        } //end of rows iterator
				
				
			} //end of sheets for loop
			
			//close file input stream
			fis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionList;
	}

	//assumes that 
	// "Account Number" string is present in column 1 (i.e. B) and 
	// account number is present in Column 3 (i.e. D)
	// account number is 12 digit number in column 3 start
	private static String tryToReadAccountNo(Row row) {
		String accountNo = "";
		if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING){
			String text = row.getCell(1).getStringCellValue().trim();
			if(text.contains("Account Number")){
//				Iterator<Cell> cellIterator = row.cellIterator();
//	            while (cellIterator.hasNext()){
//	            	Cell cell = cellIterator.next();
//	            	System.out.println(cell.getStringCellValue().trim());
//	            }				
				
				Cell nextCell = row.getCell(3);
				String nextCellString = nextCell.getStringCellValue().trim();
				String acNo = nextCellString.substring(0, 12); //assumption that ac no is inital 12 characters of this
				System.out.println("a/c: "+acNo);
				accountNo = acNo;							
			}
		}
		return accountNo;
	}

	private static boolean isTransactionLinesEnded(Row row) {
		boolean txnLinesHaveEnded = false;
		txnLinesHaveEnded = row.getCell(COLUMN_SLNO).getStringCellValue().contains(LAST_ROW_INDICATOR);
		return txnLinesHaveEnded;
	}

	//checks if Header row (that has the headings of table) is reached on not.
	private static boolean isReachedHeaderRow(Row row) {
		boolean isThisHeaderRow = row.getCell(1).getStringCellValue().trim().equalsIgnoreCase("S No."); //if text in 1st column of row matches "S NO."
		//System.out.println("Some Text before txn table: "+ row.getCell(1).getStringCellValue().trim());
		return isThisHeaderRow;
	}

	public static void main(String args[]){
		List<MyRow> list = readExcelData("C:\\Users\\anantpan\\Downloads\\Anant Per Fin\\anant consolidated 14-19oct.xlsx");
		//System.out.println("MyRow List\n"+list);
	}

}