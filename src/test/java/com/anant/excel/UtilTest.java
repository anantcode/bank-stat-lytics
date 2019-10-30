package com.anant.excel;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilTest {

	@Test
	public void test1() {
		List<String> expected = Arrays.asList("05/2019","06/2019","07/2019");
		List<String> actual = Util.monthYearArrayFromTo("05/2019", "07/2019");
		assertThat(actual, is(expected));
	}
	
//	@Test
//	public void test2() {
//		String[] expected2 = {"05/2019","06/2019","07/2019", "08/2019", "09/2019", "10/2019", "11/2019", "12/2019", "01/2020", "02/2020"};
//		assertArrayEquals(expected2, Util.monthYearArrayFromTo("05/2019", "02/2020"));
//	}
//	
//	@Test
//	public void test3() {
//		String[] expected3 = {"09/2019","10/2019","11/2019"};
//		assertArrayEquals(expected3, Util.monthYearArrayFromTo("09/2019", "11/2019"));
//	}
//
//	@Test
//	public void test4() {
//		String[] expected4 = {"05/2019","06/2019","07/2019", "08/2019", "09/2019", "10/2019", "11/2019", "12/2019", "01/2020", "02/2020", "03/2020", "04/2020", "05/2020", "06/2020", "07/2020", "08/2020", "09/2020", "10/2020", "11/2020", "12/2020", "01/2021", "02/2021"};
//		assertArrayEquals(expected4, Util.monthYearArrayFromTo("05/2019", "02/2021"));
//	}
}
