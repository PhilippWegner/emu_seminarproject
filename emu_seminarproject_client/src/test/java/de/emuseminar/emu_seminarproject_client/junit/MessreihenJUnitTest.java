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
	// 4.2.1)
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
	// 4.2.2)
	@ParameterizedTest
	@ValueSource(strings = { "Leistung", "Arbeit" })
	void constructorTest02(String messgroesse) {
		this.messreihe = new Messreihe(1, 15, "LED", messgroesse);
		Assertions.assertAll(() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
				() -> Assert.assertEquals(15, this.messreihe.getZeitintervall()),
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
	// 4.2.3)
	@ParameterizedTest
	@MethodSource("aequivalenzklassenTestParameter")
	void aequivalenzklassenTest(int messreihenId, int zeitintervall, String verbraucher, String messgroesse,
			String exceptionMessage) {
		Exception exc = Assert.assertThrows(IllegalArgumentException.class,
				() -> new Messreihe(messreihenId, zeitintervall, verbraucher, messgroesse));
		Assert.assertTrue(exc.getMessage().contains(exceptionMessage));

	}
	
	/*
	 * U1 	:= 	{..., -2, -1, 0, 1, 2, ...}
	 * U2 	:= 	{..., 12, 13, 14} x {15, 16, 17, ...}
	 * U3 	:= 	{""} x {null} x {"AaBbCc..."}
	 * U4 	:= 	{"Arbeit", "Leistung"} x {"AaBbCc..."}
	 * 
	 * 01:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {""} 			x {"Arbeit", "Leistung"}	<<<
	 * 02:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {null} 		x {"Arbeit", "Leistung"}	<<<
	 * 03:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {"AaBbCc..."} x {"Arbeit", "Leistung"}	<<<
	 * --------------------------------------------------------------------------------------------
	 * 04:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {""} 			x {"Arbeit", "Leistung"}	<<<
	 * 05:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {null} 		x {"Arbeit", "Leistung"}	<<<
	 * 06:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {"AaBbCc..."} x {"Arbeit", "Leistung"}	<<<
	 * --------------------------------------------------------------------------------------------
	 * 07:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {""} 			x {"AaBbCc..."}	<<<
	 * 08:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {null} 		x {"AaBbCc..."}	<<<
	 * 09:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {"AaBbCc..."} x {"AaBbCc..."}	<<<
	 * --------------------------------------------------------------------------------------------
	 * 10:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {""} 			x {"AaBbCc..."}	<<<
	 * 11:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {null} 		x {"AaBbCc..."}	<<<
	 * 12:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ...} x {"AaBbCc..."} x {"AaBbCc..."}	<<<
	 * --------------------------------------------------------------------------------------------
	 * 
	 * 
	 * 
	 */
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
	
	// 6.2.1)
	// if(zeitintervall >= 15 && zeitintervall <= 3600):
	@Test
	void constructorTest03() {
		// Nutzung des neuen Messreihen-Konstruktors
		this.messreihe = new Messreihe(1, 20);
		Assertions.assertAll(
			() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
			() -> Assert.assertEquals(20, this.messreihe.getZeitintervall()),
			() -> Assert.assertEquals(null, this.messreihe.getVerbraucher()),
			() -> Assert.assertEquals(null, this.messreihe.getMessgroesse())
		);
	}
	
	// 6.2.3)
	// else if(zeitintervall < 15)
	@Test
	void constructorTest04() {
		// Nutzung des neuen Messreihen-Konstruktors
		Exception exc = Assert.assertThrows(
			IllegalArgumentException.class, 
			() -> new Messreihe(1, 14)
		);
		Assert.assertTrue(exc.getMessage().contains("Das Zeitintervall muss mindestens 15 Sekunden sein!"));
	}
	
	// 6.2.3)
	// else (zeitintervall > 3600)
	@Test
	void constructorTest05() {
		// Nutzung des neuen Messreihen-Konstruktors
		Exception exc = Assert.assertThrows(
			IllegalArgumentException.class, 
			() -> new Messreihe(1, 3601)
		);
		Assert.assertTrue(exc.getMessage().contains("Das Zeitintervall darf höchstens 3600 Sekunden lang sein!"));
	}

}
