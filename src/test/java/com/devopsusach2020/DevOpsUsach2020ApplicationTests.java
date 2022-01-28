package com.devopsusach2020;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

@SpringBootTest
class DevOpsUsach2020ApplicationTests {

	@Test
	void contextLoads() {
		String correcto="Hola";
		Assertions.assertThat(correcto).isEqualTo("Hola");

	}
	@Test
	void contextLoads1() {
		String correcto="Hola";
		Assertions.assertThat(correcto).isEqualTo("Hola");

	}

}
