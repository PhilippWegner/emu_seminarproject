package gui;

import business.*;
import java.sql.SQLException;
import javafx.stage.Stage;

public class BasisControl {

	private BasisModel basisModel;
	private BasisView basisView;
	  
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
 		ergebnis = new Messung(lfdNr, 0.345);
 		
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

}
