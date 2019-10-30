package com.anant.excel;

public class MyRow {
	private String slno;
	private String valueDate;
	private String transactionDate;
	private String chequeNo;
	private String remarks;
	private double debitAmount;
	private double creditAmount;
	private double balance;
	private String accountNo;
	
	public MyRow(String slno, String valueDate, String transactionDate,
			String chequeNo, String remarks, double debitAmount,
			double creditAmount, double balance, String accountNo) {
		super();
		this.slno = slno;
		this.valueDate = valueDate;
		this.transactionDate = transactionDate;
		this.chequeNo = chequeNo;
		this.remarks = remarks;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.balance = balance;
		this.accountNo = accountNo;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Override
	public String toString(){
		return (accountNo+ "\t"+ slno + "\t"+transactionDate +"\t\tD " + debitAmount+ "\t\tC " + creditAmount+ "\t\t\t"+remarks+"\t\t\tBal "+balance);
	}
	
}
