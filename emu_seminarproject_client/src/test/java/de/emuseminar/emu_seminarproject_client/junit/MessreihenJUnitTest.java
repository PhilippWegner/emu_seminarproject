package de.emuseminar.emu_seminarproject_client.junit;


import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import de.emuseminar.emu_seminarproject_client.business.Messreihe;

class MessreihenJUnitTest {

	private Messreihe messreihe;

	@BeforeEach
	void setUp() throws Exception {
		this.messreihe = null;
	}

	@AfterEach
	void tearDown() throws Exception {
		this.messreihe = null;
	}

	/*
	 * 
	 * Äquivalenztest
	 * Fall 01:
	 * ID = 1
	 * Zeitinterval: 20
	 * Verbraucher: LED
	 * Messgroeße: Leistung
	 * (Indirekter Test der getter und setter Methoden von Klasse Messreihe)
	 * Nur 1 Testcase!
	 * 
	 */
	@Test
	void constructorTest01() {
		this.messreihe = new Messreihe(1, 20, "LED", "Leistung");
		Assertions.assertAll(() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
				() -> Assert.assertEquals(20, this.messreihe.getZeitintervall()),
				() -> Assert.assertEquals("LED", this.messreihe.getVerbraucher()),
				() -> Assert.assertEquals("Leistung", this.messreihe.getMessgroesse()));
	}

	/*
	 * 
	 * Äquivalenztest
	 * Fall 01:
	 * ID = 1
	 * Zeitintervall = 20
	 * Verbraucher = LED
	 * Messgroesse = Leistung
	 * 
	 * Fall 02:
	 * ID = 1
	 * Zeitinterval = 20
	 * Verbraucher = LED
	 * Messgroesse = Arbeit
	 * 
	 */
	@ParameterizedTest
	@ValueSource(strings = { "Leistung", "Arbeit" })
	void constructorTest02(String messgroesse) {
		this.messreihe = new Messreihe(1, 20, "LED", messgroesse);
		Assertions.assertAll(() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
				() -> Assert.assertEquals(20, this.messreihe.getZeitintervall()),
				() -> Assert.assertEquals("LED", this.messreihe.getVerbraucher()),
				() -> Assert.assertEquals(messgroesse, this.messreihe.getMessgroesse()));
	}
	
	/*
	 * 
	 * Äquivalenzklasse
	 * Fall 01:
	 * ID <= 0: 
	 * 
	 * Fall 02:
	 * Zeitintervall < 15: 
	 * 
	 * Fall 03:
	 * Verbraucher == null:
	 * 
	 * Fall 04:
	 * Alles einstellbare ist fehlerhaft:
	 * Erster Fehler wird zurueckgemeldet (ID)
	 * 
	 */
	@ParameterizedTest
	@MethodSource("aequivalenzklassenTestParameter")
	void aequivalenzklassenTest(int messreihenId, int zeitintervall, String verbraucher, String messgroesse,
			String exceptionMessage) {
		Exception exc = Assert.assertThrows(IllegalArgumentException.class,
				() -> new Messreihe(messreihenId, zeitintervall, verbraucher, messgroesse));
		Assert.assertTrue(exc.getMessage().contains(exceptionMessage));

	}

	private static Stream<Arguments> aequivalenzklassenTestParameter() {
		return Arrays.stream(new Arguments[] {
				Arguments.of(0, 15, "LED", "Leistung", "Die MessreihenID darf nicht kleiner als 1 sein!"),
				Arguments.of(1, 14, "LED", "Leistung", "Das Zeitintervall darf nicht kleiner als 15 sein!"),
				Arguments.of(1, 15, "", "Leistung", "Der Verbraucher darf nicht leer sein!"),
				Arguments.of(-10, 10, "", "Leistung", "Die MessreihenID darf nicht kleiner als 1 sein!"), });
	}

//	@Test
//	void testWithFailure() {
//		Exception exc = Assert.assertThrows(IllegalArgumentException.class, 
//				() -> new Messreihe(42, 42, "LED", "Leistung"));
//		Assert.assertTrue(exc.getMessage().contains("Exception ist nicht definiert!"));
//		
//	}

}
