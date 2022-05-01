package de.emuseminar.restful;

public class Messreihe {
	private int messreihenId;
	private int zeitintervall;
	private String verbraucher;
	private String messgroesse;
	private Messung[] messungen;
	private String messungenString;

	public Messreihe() {}
	
	public Messreihe(int messreihenId, int zeitintervall, String verbraucher, String messgroesse) {
		super();
		this.messreihenId = messreihenId;
		this.zeitintervall = zeitintervall;
		this.verbraucher = verbraucher;
		this.messgroesse = messgroesse;
	}

	public int getMessreihenId() {
		return messreihenId;
	}

	public void setMessreihenId(int messreihenId) {
		this.messreihenId = messreihenId;
	}

	public int getZeitintervall() {
		return zeitintervall;
	}

	public void setZeitintervall(int zeitintervall) {
		this.zeitintervall = zeitintervall;
	}

	public String getVerbraucher() {
		return verbraucher;
	}

	public void setVerbraucher(String verbraucher) {
		this.verbraucher = verbraucher;
	}

	public String getMessgroesse() {
		return messgroesse;
	}

	public void setMessgroesse(String messgroesse) {
		this.messgroesse = messgroesse;
	}

	public Messung[] getMessungen() {
		return messungen;
	}

	public void setMessungen(Messung[] messungen) {
		this.messungen = messungen;
	}

	public String gibAttributeAus() {
		return (this.messreihenId + " " + this.zeitintervall + " " + this.verbraucher + " " + this.messgroesse);
	}
}
