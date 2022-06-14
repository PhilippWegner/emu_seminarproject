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
	
	// 4.2.1) [STANDARD]
	@Test
	void constructorTest01() {
		this.messreihe = new Messreihe(1, 20, "LED", "Leistung");
		Assertions.assertAll(() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
				() -> Assert.assertEquals(20, this.messreihe.getZeitintervall()),
				() -> Assert.assertEquals("LED", this.messreihe.getVerbraucher()),
				() -> Assert.assertEquals("Leistung", this.messreihe.getMessgroesse()));
	}


	// 4.2.2) [ERWEITERUNGSPUNKT]
	@ParameterizedTest
	@ValueSource(strings = { "Leistung", "Arbeit" })
	void constructorTest02(String messgroesse) {
		this.messreihe = new Messreihe(1, 20, "LED", messgroesse);
		Assertions.assertAll(() -> Assert.assertEquals(1, this.messreihe.getMessreihenId()),
				() -> Assert.assertEquals(20, this.messreihe.getZeitintervall()),
				() -> Assert.assertEquals("LED", this.messreihe.getVerbraucher()),
				() -> Assert.assertEquals(messgroesse, this.messreihe.getMessgroesse()));
	}
	
	
	// 4.2.3)
	@ParameterizedTest
	@MethodSource("aequivalenzklassenTestParameter")
	void aequivalenzklassenTest(int messreihenId, int zeitintervall, String verbraucher, String messgroesse, String exceptionMessage) {
		if(exceptionMessage != null) {
			Exception exc = Assert.assertThrows(IllegalArgumentException.class, () -> new Messreihe(messreihenId, zeitintervall, verbraucher, messgroesse));
			Assert.assertTrue(exc.getMessage().contains(exceptionMessage));			
		} else {
			messreihe = new Messreihe(messreihenId, zeitintervall, verbraucher, messgroesse);
			Assert.assertTrue(messreihe != null);
		}
	}
	
	/*
	 * 
	 * --------------------------------------------------------------------------------------------
	 * U1 [messreihenId]	:= 	{..., -2, -1, 0, 1, 2, ...}
	 * U2 [zeitintervall]	:= 	{..., 12, 13, 14} x {15, 16, 17, ...}
	 * U3 [verbraucher]		:= 	{""} x {null} x {"AaBbCc..."}
	 * U4 [messgroesse]		:= 	{"Arbeit", "Leistung"} x {"AaBbCc..."}
	 * --------------------------------------------------------------------------------------------
	 * 01:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {""} 			x {"Arbeit", "Leistung"}	| => UNGÜLTIGER
	 * 02:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {null} 		x {"Arbeit", "Leistung"}	| => UNGÜLTIGER
	 * 03:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {"AaBbCc..."} x {"Arbeit", "Leistung"}	| => UNGÜLTIGER  	<<<
	 * --------------------------------------------------------------------------------------------
	 * 04:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {""} 			x {"Arbeit", "Leistung"}	| => UNGÜLTIGER		<<<
	 * 05:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {null} 		x {"Arbeit", "Leistung"}	| => UNGÜLTIGER		<<<
	 * 06:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {"AaBbCc..."} x {"Arbeit", "Leistung"}	| => GÜLTIGER		<<<
	 * --------------------------------------------------------------------------------------------
	 * 07:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {""} 			x {"AaBbCc..."}				| => UNGÜLTIGER
	 * 08:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {null} 		x {"AaBbCc..."}				| => UNGÜLTIGER
	 * 09:	{.., -2, -1, 0, 1, 2, ...} 	x {..., 12, 13, 14} x {"AaBbCc..."} x {"AaBbCc..."}				| => UNGÜLTIGER
	 * --------------------------------------------------------------------------------------------
	 * 10:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {""} 			x {"AaBbCc..."}				| => UNGÜLTIGER
	 * 11:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {null} 		x {"AaBbCc..."}				| => UNGÜLTIGER
	 * 12:	{.., -2, -1, 0, 1, 2, ...} 	x {15, 16, 17, ..} 	x {"AaBbCc..."} x {"AaBbCc..."}				| => UNGÜLTIGER		<<<
	 * --------------------------------------------------------------------------------------------
	 * 
	 * --------------------------------------------------------------------------------------------
	 * VERSCHMELZEN VON 	01,02,03,07,08,09 	ZU 		03 		--> U2 [zeitintervall] 	{..., 12, 13, 14} 	| => Exception
	 * VERSCHMELZEN VON 	04,05				ZU		04,05 	--> U3 [verbraucher] 	{""} x {null} 		| => Exception
	 * VERSCHMELZEN VON 	06					ZU		06 												  	| => KEINE Exception
	 * VERSCHMELZEN VON		10,11,12			ZU		12 		--> U3 [messgroesse] 	{"AaBbCc..."} 		| => Exception
	 * 
	 * --------------------------------------------------------------------------------------------
	 * 
	 * 03:	{.., -2, -1, 0, 1, 2, ..} 	x {..., 12, 13, 14} x {"AaBbCc..."} x {"Arbeit", "Leistung"}	| => UNGÜLTIGER
	 * 04:	{.., -2, -1, 0, 1, 2, ..} 	x {15, 16, 17, ..} 	x {""} 			x {"Arbeit", "Leistung"}	| => UNGÜLTIGER
	 * 05:	{.., -2, -1, 0, 1, 2, ..} 	x {15, 16, 17, ..} 	x {null} 		x {"Arbeit", "Leistung"}	| => UNGÜLTIGER
	 * 06:	{.., -2, -1, 0, 1, 2, ..} 	x {15, 16, 17, ..} 	x {"AaBbCc..."} x {"Arbeit", "Leistung"}	| => GÜLTIGER
	 * 12:	{.., -2, -1, 0, 1, 2, ..} 	x {15, 16, 17, ..} 	x {"AaBbCc..."} x {"AaBbCc..."}				| => UNGÜLTIGER
	 * --------------------------------------------------------------------------------------------
	 * 
	 */
	
	// 4.2.2
	private static Stream<Arguments> aequivalenzklassenTestParameter() {
		return Arrays.stream(new Arguments[] {
				//03 => UNGÜLTIGER
				Arguments.of(0, 14, 	"Laptop", 	"Arbeit", 		"Das Zeitintervall darf nicht kleiner als 15 sein!"),
				//04 => UNGÜLTIGER
				Arguments.of(0, 15, 	"", 		"Arbeit", 		"Der Verbraucher darf nicht leer sein!"),
				//05 => UNGÜLTIGER
				Arguments.of(0, 15, 	null, 		"Arbeit", 		"Der Verbraucher darf nicht leer sein!"),
				//06 => GÜLTIGER
				Arguments.of(0, 15, 	"Laptop", 	"Arbeit", 		null),
				//12 => UNGÜLTIGER
				Arguments.of(0, 15, 	"Laptop", 	"Irgendwas", 	"Die Messgröße ist nicht zulässig!")});	
	}
	
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
