package de.emuseminar.emu_seminarproject_client.business.emu;

import java.sql.SQLException;

import de.emuseminar.emu_seminarproject_client.business.BasisModel;
import de.emuseminar.emu_seminarproject_client.business.Messung;
import net.sf.yad2xx.FTDIException;

public class ThreadTimer extends Thread {
	private EmuCheckConnection ecc;
	private int zeitIntervall;
	private int messreihenId;
	private String messgroesse;
	private static int laufendeNummer; // static nur, falls laufendenNummer instanzübergreifend verwendet werden soll
										// (laufendenNummer + Messgroesse sind Primaerschluessel!
	private boolean istAufnahmeGestart = false;
	private BasisModel basisModel;

	public ThreadTimer(int messreihenId, int zeitIntervall, String messgroesse) throws FTDIException, InterruptedException {
		this.basisModel = BasisModel.getInstance();
		this.messreihenId = messreihenId;
		this.zeitIntervall = zeitIntervall;
		this.messgroesse = messgroesse;
		laufendeNummer = 1;
		this.ecc = new EmuCheckConnection();
		Thread.sleep(1000);
	}

	public void starteMessreihe() throws FTDIException, InterruptedException {
		this.ecc.connect();
		Thread.sleep(1000);
		this.ecc.sendProgrammingMode();
		Thread.sleep(1000);
		this.istAufnahmeGestart = true;
		this.start();
	}

	public void stoppeMessreihe() throws FTDIException {
		this.istAufnahmeGestart = false;
		this.ecc.disconnect();
	}

	private void speichereMessungInDb(int messreihenId, Messung messung) throws ClassNotFoundException, SQLException {
		this.basisModel.speichereMessungInDb(messreihenId, messung);
	}

	public void run() {
		while (this.istAufnahmeGestart) {
			Messung messung;
			try {
				if (this.messgroesse.contains("Leistung")) {
					this.ecc.sendRequest(MESSWERT.Leistung);
				} else if (this.messgroesse.contains("Scheinleistung")) {
					this.ecc.sendRequest(MESSWERT.Scheinleistung);
				} else if (this.messgroesse.contains("Induktive Blindleistung")) {
					this.ecc.sendRequest(MESSWERT.Induktive_Blindleistung);
				} else if (this.messgroesse.contains("Kapazitive Blindleistung")) {
					this.ecc.sendRequest(MESSWERT.Kapazitive_Blindleistung);
				} else if (this.messgroesse.contains("Arbeit")) {
					this.ecc.sendRequest(MESSWERT.Arbeit);
				} else if (this.messgroesse.contains("Strom")) {
					this.ecc.sendRequest(MESSWERT.Strom);
				} else if (this.messgroesse.contains("Spannung")) {
					this.ecc.sendRequest(MESSWERT.Spannung);
				} else {
					this.ecc.sendRequest(MESSWERT.Leistung);
				}
				Thread.sleep(1000);

				messung = new Messung(laufendeNummer, this.ecc.gibErgebnisAus());
				laufendeNummer++;

				this.speichereMessungInDb(this.messreihenId, messung);

				sleep(this.zeitIntervall * 1000); // Mal 1000 wegen ms

			} catch (FTDIException ftdiExc) {
				System.out.println("FTDIException");
				ftdiExc.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
