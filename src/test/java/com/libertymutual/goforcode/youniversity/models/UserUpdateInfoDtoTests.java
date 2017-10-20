package com.libertymutual.goforcode.youniversity.models;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class UserUpdateInfoDtoTests {

	@Test
	public void checking_to_make_sure_my_getters_and_setters_work() {
		BeanTester tester = new BeanTester();
		Configuration configuration = new ConfigurationBuilder()
				.ignoreProperty("user")
				.build();
		tester.testBean(SchoolList.class, configuration);

	}


}
