package com.enesbayram.sm_security_manager;

import com.enesbayram.sm_security_manager.main.SmSecurityManagerStarter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SmSecurityManagerStarter.class})
@ActiveProfiles("developer")
class SmSecurityManagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
