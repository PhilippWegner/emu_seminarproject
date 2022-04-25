package business.emu;

import java.sql.SQLException;

import business.Messung;
import business.db.DbAktionen;
import net.sf.yad2xx.FTDIException;

public class ThreadTimer extends Thread {
	private EmuCheckConnection ecc;
	private int zeitIntervall = 5000;
	private int messreihenId;
	private int laufendeNummer;
	private boolean istAufnahmeGestart = false;
	private DbAktionen dbAktionen = new DbAktionen();

	public ThreadTimer(int messreihenId, int laufendeNummer) {
		this.messreihenId = messreihenId;
		this.laufendeNummer = laufendeNummer;
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

		        messung = new Messung(this.laufendeNummer, ecc.gibErgebnisAus());
		        this.laufendeNummer++;
		        
		        this.speichereMessungInDb(this.messreihenId, messung);
		        sleep(zeitIntervall);
		        
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

