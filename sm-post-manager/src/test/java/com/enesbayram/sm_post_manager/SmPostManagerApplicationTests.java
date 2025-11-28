package com.enesbayram.sm_post_manager;

import com.enesbayram.sm_post_manager.main.SmPostManagerStarter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SmPostManagerStarter.class})
@ActiveProfiles("developer")
class SmPostManagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
