package com.libertymutual.goforcode.youniversity.models;


import org.junit.Test;
import org.meanbean.test.BeanTester;

public class UserTests {

	@Test
	public void test_all_gets_and_sets() {
		new BeanTester().testBean(User.class);
	}


}
