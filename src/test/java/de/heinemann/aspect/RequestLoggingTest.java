package de.heinemann.aspect;

import org.junit.Ignore;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import de.heinemann.controller.ControllerTest;

@Ignore
@DatabaseTearDown("../reset.xml")
public class RequestLoggingTest extends ControllerTest {

	@Test
	@DatabaseSetup("requestlogging/getUsersWithoutException/prepared.xml")
	@ExpectedDatabase(value = "requestlogging/getUsersWithoutException/expected.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void getUsersWithoutException() throws Exception {
		get("/users");
	}
	
}
