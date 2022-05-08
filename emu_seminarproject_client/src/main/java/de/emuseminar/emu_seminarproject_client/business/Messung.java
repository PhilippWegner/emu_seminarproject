package de.emuseminar.emu_seminarproject_client.business;

public class Messung {
	
	private int laufendeNummer;
	private double wert;
	
	public Messung() {}
	
	public Messung(int laufendeNummer, double wert) {
		super();
		this.laufendeNummer = laufendeNummer;
		this.wert = wert;
	}

	public int getLaufendeNummer() {
		return laufendeNummer;
	}
	
	public void setLaufendeNummer(int laufendeNummer) {
		this.laufendeNummer = laufendeNummer;
	} 
	
	public double getWert() {
		return wert;
	}
	
	public void setWert(double wert) {
		this.wert = wert;
	}
	
	public String gibAttributeAus(){
		return this.laufendeNummer + ": " + this.wert;
	}
}
