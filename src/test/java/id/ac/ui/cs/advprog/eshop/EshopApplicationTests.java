package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main_runsWithoutThrowing_whenUsingRandomPort() {
		String[] args = new String[]{"--server.port=0"};
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> EshopApplication.main(args));
	}

	@Test
	void main_canBeCalledMultipleTimes_withDifferentRandomPorts() {
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> EshopApplication.main(new String[]{"--server.port=0"}));
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> EshopApplication.main(new String[]{"--server.port=0"}));
	}

}
