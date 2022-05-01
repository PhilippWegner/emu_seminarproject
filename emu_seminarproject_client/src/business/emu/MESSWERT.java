package business.emu;

public enum MESSWERT {

	Leistung("1.7.1", new byte[] { 1, 82, 49, 2, 49, 46, 55, 46, 49, 40, 41, 3 }, "W"),
	Scheinleistung("9.7.1", new byte[] { 1, 82, 49, 2, 57, 46, 55, 46, 49, 40, 41, 3 }, "VA"),
	/*
	 * induktive Verbraucher (Drosselspule, Transformator, Asynchronmotor) Aufbau
	 * von magnetischem Feld. Die Spannung eilt dem Strom voraus -->
	 * Phasenverschiebungswinkel > 0 !!! Beispiele: Laptop, MP3-Player/Radio,
	 * Schneidegeraet
	 */
	Induktive_Blindleistung("5.7.1", new byte[] { 1, 82, 49, 2, 51, 46, 55, 46, 49, 40, 41, 3 }, "VAR"),
	/*
	 * kapazitive Verbraucher (Kondensatormotoren, Erdkabel) Aufbau von elektrischem
	 * Feld. Der Strom eilt der Spannung voraus --> Phasenverschiebungswinkel < 0
	 * !!! Beispiele: Monitor, Ventilator, Halogenlampe, Laptop, Pumpe
	 */
	Kapazitive_Blindleistung("8.7.1", new byte[] { 1, 82, 49, 2, 52, 46, 55, 46, 49, 40, 41, 3 }, "VAR"),
	Leistungsfaktor("13.7", new byte[] { 1, 82, 49, 2, 49, 51, 46, 55, 40, 41, 3 }, "cos Phi"),
	Seriennummer("96.1.0", new byte[] { 1, 82, 49, 2, 57, 54, 46, 49, 46, 48, 40, 41, 3 }, "Seriennummer"),
	Text("128.128", new byte[] { 1, 82, 49, 2, 49, 50, 56, 46, 49, 50, 56, 40, 41, 3 }, "Text"),
	Arbeit("1.8.1", new byte[] { 1, 82, 49, 2, 49, 46, 56, 46, 49, 40, 41, 3 }, "Arbeit"),
	Strom("11.7", new byte[] { 1, 82, 49, 2, 49, 49, 46, 55, 40, 41, 3 }, "Strom"),
	Spannung("12.7", new byte[] { 1, 82, 49, 2, 49, 50, 46, 55, 40, 41, 3 }, "Spannung");

	private byte[] request;
	private String obis, einheit;

	private MESSWERT(String obis, byte[] request, String einheit) {
		this.obis = obis;
		this.request = request;
		this.einheit = einheit;
	}

	public byte[] getRequest() {
		return request;
	}

	public String getObis() {
		return obis;
	}

	public String getEinheit() {
		return einheit;
	}
}
