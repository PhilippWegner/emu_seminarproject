package gui;

import business.*;
import business.emu.EmuCheckConnection;
import business.emu.MESSWERT;

import java.sql.SQLException;
import javafx.stage.Stage;
import net.sf.yad2xx.FTDIException;

public class BasisControl {

	private BasisModel basisModel;
	private BasisView basisView;
	private boolean istMessungGestart =	false;
	private static int counter = 0;
	  
	public BasisControl(Stage primaryStage){
		this.basisModel = BasisModel.getInstance();
		this.basisView = new BasisView(this, primaryStage, this.basisModel);
		primaryStage.show();
	}
		
	public Messung[] leseMessungenAusDb(String messreihenId){
		Messung[] ergebnis = null;
		int idMessreihe = -1;
		try{
 			idMessreihe = Integer.parseInt(messreihenId);
 		}
 		catch(NumberFormatException nfExc){
 			basisView.zeigeFehlermeldungAn( 
 				"Das Format der eingegebenen MessreihenId ist nicht korrekt.");
 		}
 		try{
 			ergebnis = this.basisModel.leseMessungenAusDb(idMessreihe);
 		}
 		catch(ClassNotFoundException cnfExc){
 			basisView.zeigeFehlermeldungAn( 
 				"Fehler bei der Verbindungerstellung zur Datenbank.");
 		}
 		catch(SQLException sqlExc){
 			basisView.zeigeFehlermeldungAn( 
 				"Fehler beim Zugriff auf die Datenbank.");
 			sqlExc.printStackTrace();
 		}
 		return ergebnis;
	} 
 	  
	public Messung holeMessungVonEMU(String messreihenId, String laufendeNummer){
 		Messung ergebnis = null;
 		int messId = -1;
		messId = Integer.parseInt(messreihenId);
		int lfdNr = Integer.parseInt(laufendeNummer);

		// Dummy-Messung-Objekt, muss ersetzt werden !!!
 		// ergebnis = new Messung(lfdNr, 0.345);
		
		try {
            EmuCheckConnection ecc = new EmuCheckConnection();
            ecc.connect();
            Thread.sleep(1000);
            ecc.sendProgrammingMode();
            Thread.sleep(1000);
            ecc.sendRequest(MESSWERT.Leistung);
            Thread.sleep(1000);
            ecc.disconnect();

            ergebnis = new Messung(lfdNr, ecc.gibErgebnisAus());

        } catch (FTDIException ftdiExc) {
            System.out.println("FTDIException");
            ftdiExc.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 		
 		this.speichereMessungInDb(messId, ergebnis);
 		return ergebnis;
 	}
	
  	private void speichereMessungInDb(int messreihenId, Messung messung){
 		try{
 			this.basisModel.speichereMessungInDb(messreihenId, messung);
 		}
 		catch(ClassNotFoundException cnfExc){
 			basisView.zeigeFehlermeldungAn( 
 				"Fehler bei der Verbindungerstellung zur Datenbank.");
 		}
 		catch(SQLException sqlExc){
 			basisView.zeigeFehlermeldungAn( 
 				"Fehler beim Zugriff auf die Datenbank.");
 		}
	}
  	
  	public void starteMessreiheAufnehmen(String messreihenId, String laufendeNummer) {
  		this.istMessungGestart = true;
  		this.counter = Integer.parseInt(laufendeNummer);
  		while(this.istMessungGestart) {
  			System.out.print("Messreihe gestartet!");
  			Messung ergebnis = holeMessungVonEMU(messreihenId, this.counter + "");
  			
  			System.out.println("Counter: " + counter);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.counter++;
  		}
  	}
  	
  	public void stopMessreiheAufnehmen() {
  		this.istMessungGestart = false;
  		System.out.print("Messreihe gestoppt!");
  	}

}
