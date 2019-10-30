package com.anant.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Util {
	
    //takes months in format MM/YYYY and returns months (inclusive) between from and to in same format.
    public static List<String> monthYearListFromTo(String startMonthYear, String endMonthYear){
    	List<String> arrayOfMonthYearStrings = new ArrayList<String>();

    	 String date1 = startMonthYear;
         String date2 = endMonthYear;
         
//    	 String date1 = "05/2019";
//         String date2 = "07/2022";

         DateFormat formater = new SimpleDateFormat("MM/yyyy");

         Calendar beginCalendar = Calendar.getInstance();
         Calendar finishCalendar = Calendar.getInstance();

         try {
             beginCalendar.setTime(formater.parse(date1));
             finishCalendar.setTime(formater.parse(date2));
         } catch (ParseException e) {
             e.printStackTrace();
         }

         while (beginCalendar.before(finishCalendar)) {
             // add one month to date per loop
             String date = formater.format(beginCalendar.getTime()).toUpperCase();
             //System.out.println(date);
             arrayOfMonthYearStrings.add(date);
             beginCalendar.add(Calendar.MONTH, 1);
         }
         String date = formater.format(beginCalendar.getTime()).toUpperCase();
         beginCalendar.add(Calendar.MONTH, 1);
         arrayOfMonthYearStrings.add(date);
         
    	return arrayOfMonthYearStrings;//arrayOfMonthYearStrings;
    }
    
    public static void main(String[] args){
    	Util.monthYearListFromTo("05/2019", "06/2019");
    }

}
