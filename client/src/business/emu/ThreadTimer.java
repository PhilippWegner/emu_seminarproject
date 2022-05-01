package business.emu;

import java.sql.SQLException;
import business.Messung;
import business.db.DbAktionen;
import net.sf.yad2xx.FTDIException;

public class ThreadTimer extends Thread {
	private EmuCheckConnection ecc;
	private int zeitIntervall;
	private int messreihenId;
	private static int laufendeNummer; // static nur, falls laufendenNummer instanzübergreifend verwendet werden soll (laufendenNummer + Messgroesse sind Primaerschluessel!
	private boolean istAufnahmeGestart = false;
	private DbAktionen dbAktionen = new DbAktionen();

	public ThreadTimer(int messreihenId, int zeitIntervall) {
		this.messreihenId = messreihenId;
		this.zeitIntervall = zeitIntervall;
		laufendeNummer = 1;
	}

	public void starteMessreihe() {
		this.istAufnahmeGestart = true;
		this.start();
	}

	public void stoppeMessreihe() {
		this.istAufnahmeGestart = false;
	}

	private void speichereMessungInDb(int messreihenId, Messung messung) throws ClassNotFoundException, SQLException {
		this.dbAktionen.connectDb();
		this.dbAktionen.fuegeMessungEin(messreihenId, messung);
		this.dbAktionen.closeDb();
	}

	public void run() {
		while(this.istAufnahmeGestart) {
			Messung messung;
			try {
		        EmuCheckConnection ecc = new EmuCheckConnection();
		        ecc.connect();
		        Thread.sleep(1000);
		        ecc.sendProgrammingMode();
		        Thread.sleep(1000);
		        ecc.sendRequest(MESSWERT.Leistung);
		        Thread.sleep(1000);
		        ecc.disconnect();

		        messung = new Messung(laufendeNummer, ecc.gibErgebnisAus());
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

