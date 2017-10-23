package com.libertymutual.goforcode.youniversity.models;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class UserTests {

	@Test
	public void test_all_gets_and_sets() {
        BeanTester tester = new BeanTester();
        Configuration configuration = new ConfigurationBuilder()
                .ignoreProperty("schoolList")
                .build();
        tester.testBean(SchoolList.class, configuration);
	}

}
