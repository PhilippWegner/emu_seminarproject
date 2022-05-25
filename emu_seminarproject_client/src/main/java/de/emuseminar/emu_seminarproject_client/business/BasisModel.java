package de.emuseminar.emu_seminarproject_client.business;

import javafx.collections.*;
import net.sf.yad2xx.FTDIException;

import java.sql.*;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import de.emuseminar.emu_seminarproject_client.business.emu.ThreadTimer;

public final class BasisModel {

	private static BasisModel basisModel;
	private ThreadTimer threadTimer = null;
	private static final String REST_URI = "http://localhost:8080/emu_seminarproject_server/webapi/serviceEmu";
	private Client client = ClientBuilder.newClient();

	public static BasisModel getInstance() {
		if (basisModel == null) {
			basisModel = new BasisModel();
		}
		return basisModel;
	}

	private BasisModel() {
	}

	// wird zukuenftig noch instanziiert
	private ObservableList<Messreihe> messreihen = FXCollections.observableArrayList();

	public ObservableList<Messreihe> getMessreihen() {
		return messreihen;
	}

	public void speichereMessungInDb(int messreihenId, Messung messung) throws SQLException {
		System.out.println("Posting Messung");
		Response response = client.target(REST_URI + "/messung").path(String.valueOf(messreihenId)).request(MediaType.TEXT_PLAIN).post(Entity.entity(messung, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 201) {
			System.err.println("Fehler: clientResponse.STATUS " + response.getStatus());
			throw new SQLException("Fehler beim Eintragen in die Datenbank!"); // Ausstieg bei Fehler!
		}
	}

	public void leseMessreihenInklusiveMessungenAusDb() throws ClassNotFoundException, SQLException {
		System.out.println("Get Messreihe");
		this.messreihen.clear();
		Messreihe[] messreihen = client.target(REST_URI + "/messreihen").request(MediaType.APPLICATION_JSON).get(Messreihe[].class);
		
		// Neue Messreihenliste aufbauen
		for (Messreihe messreihe : messreihen) {
			this.messreihen.add(messreihe);
		}
	}

	public void speichereMessreiheInDb(Messreihe messreihe) throws ClassNotFoundException, SQLException {
		System.out.println("Posting Messreihe");
		Response response = client.target(REST_URI + "/messreihe").path(String.valueOf(messreihe.getMessreihenId())).request(MediaType.TEXT_PLAIN).post(Entity.entity(messreihe, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() != 201) {
			System.err.println("Fehler: clientResponse.STATUS " + response.getStatus());
			throw new SQLException("Fehler beim Eintragen in die Datenbank!");
		}
	}


	public void starteMessreihe(int messreihenId, int zeitIntervall, String messgroesse) throws FTDIException, InterruptedException {
		this.threadTimer = new ThreadTimer(messreihenId, zeitIntervall, messgroesse); // Damit der erste Eintrag nie 0																// wird!
		this.threadTimer.starteMessreihe();

	}

	public void stoppeMessreihe() throws FTDIException {
		this.threadTimer.stoppeMessreihe();
	}
}
