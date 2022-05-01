package business;

import javafx.collections.*;
import java.sql.*;

import business.db.DbAktionen;
import business.emu.ThreadTimer;

public final class BasisModel {
	
	private static BasisModel basisModel;
	private ThreadTimer threadTimer = null;
	
	public static BasisModel getInstance(){
		if (basisModel == null){
			basisModel = new BasisModel();
		}
		return basisModel;
	}
	
	private BasisModel(){		
	}
	
	private DbAktionen dbAktionen = new DbAktionen();
	
	// wird zukuenftig noch instanziiert
	private ObservableList<Messreihe> messreihen = FXCollections.observableArrayList();
	
	
	public ObservableList<Messreihe> getMessreihen() {
		return messreihen;
	}

	public void speichereMessungInDb(int messreihenId, Messung messung)
		throws ClassNotFoundException, SQLException{
		this.dbAktionen.connectDb();
		this.dbAktionen.fuegeMessungEin(messreihenId, messung);
		this.dbAktionen.closeDb();
	} 
	
	public void leseMessreihenInklusiveMessungenAusDb()
		throws ClassNotFoundException, SQLException{
		this.dbAktionen.connectDb();
		Messreihe[] messreihenAusDb 
		    = this.dbAktionen.leseMessreihenInklusiveMessungen(); 
		this.dbAktionen.closeDb();
		int anzahl = this.messreihen.size();
		for(int i = 0; i < anzahl; i++){
		    this.messreihen.remove(0);
		}
		for(int i = 0; i < messreihenAusDb.length; i++){
			this.messreihen.add(messreihenAusDb[i]);
		}
	}
		  
	public void speichereMessreiheInDb(Messreihe messreihe)
	  	throws ClassNotFoundException, SQLException{
	  	this.dbAktionen.connectDb();
	  	this.dbAktionen.fuegeMessreiheEin(messreihe);
	  	this.dbAktionen.closeDb();
	  	this.messreihen.add(messreihe);
	} 
	
  	public String getDaten(){
    	return "in getDaten";
	}
  	
  	public int anzahlMessungenZuMessreihe(int messreihenId) {
  		int anzahlMessungen = 0; 
		try {
			this.dbAktionen.connectDb();		
			Messung[] messungen = this.dbAktionen.leseMessungen(messreihenId);
			this.dbAktionen.closeDb();
			anzahlMessungen = messungen.length;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return anzahlMessungen;

  	}
  	
  	public void starteMessreihe(int messreihenId, int zeitIntervall) {
  		this.threadTimer = new ThreadTimer(messreihenId, zeitIntervall); // Damit der erste Eintrag nie 0 wird!
		this.threadTimer.starteMessreihe();
  		
  	}
  	
  	public void stoppeMessreihe() {
  		this.threadTimer.stoppeMessreihe();
  	}
 }
