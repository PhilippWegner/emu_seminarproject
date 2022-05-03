package business;

import javafx.collections.*;

import java.io.IOException;
import java.sql.*;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import business.emu.ThreadTimer;

public final class BasisModel {

	private static BasisModel basisModel;
	private ThreadTimer threadTimer = null;
	private static final String REST_URI = "http://localhost:8080/emu_seminarproject_server/webapi/serviceEmu";
	private Client client = Client.create();

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

	public void speichereMessungInDb(int messreihenId, Messung messung) throws ClassNotFoundException, SQLException {
		System.out.println("Posting Messung");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String output;
			output = objectMapper.writeValueAsString(messung);

			WebResource webResource = client.resource(REST_URI + "/messung/" + messreihenId);
			ClientResponse clientResponse = webResource.type("application/json").post(ClientResponse.class, output);
			if (clientResponse.getStatus() != 200) {
				System.err.println("Fehler: clientResponse.STATUS " + clientResponse.getStatus());
				System.err.println("Fehler: Entity " + clientResponse.getEntity(String.class));
				return; // Ausstieg bei Fehler!
			}

			System.out.println(clientResponse.getEntity(String.class));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void leseMessreihenInklusiveMessungenAusDb() throws ClassNotFoundException, SQLException {
		System.out.println("Get Messreihe");
		this.messreihen.clear();
		try {
			WebResource webResource = client.resource(REST_URI + "/messreihen");
			Builder builder = webResource.accept("application/json").header("content-type", MediaType.APPLICATION_JSON);
			ClientResponse clientResponse = builder.get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.err.println("Fehler: clientResponse.STATUS " + clientResponse.getStatus());
				System.err.println("Fehler: Entity " + clientResponse.getEntity(String.class));
				return; // Ausstieg bei Fehler!
			}
			// Wenn es in die Console geschrieben wird, wird die Variable aufgelöst???
			// System.out.println("clientResponse:" +
			// clientResponse.getEntity(String.class));

			ObjectMapper objectMapper = new ObjectMapper();
			Messreihe[] messreihen;
			messreihen = objectMapper.readValue(clientResponse.getEntity(String.class), Messreihe[].class);
			System.out.println("messreihen");
			System.out.println("MESSREIHE: " + messreihen.toString());

			// Neue Messreihenliste aufbauen
			for (Messreihe messreihe : messreihen) {
				this.messreihen.add(messreihe);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void speichereMessreiheInDb(Messreihe messreihe) throws ClassNotFoundException, SQLException {
		System.out.println("Posting Messreihe");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String output;
			output = objectMapper.writeValueAsString(messreihe);

			WebResource webResource = client.resource(REST_URI + "/messreihe/").path("" + messreihe.getMessreihenId());
			System.out.println("output:" + output);
			ClientResponse clientResponse = webResource.accept("application/json").type("application/json")
					.post(ClientResponse.class, output);
			if (clientResponse.getStatus() != 200) {
				System.err.println("Fehler: clientResponse.STATUS " + clientResponse.getStatus());
				System.err.println("Fehler: Entity " + clientResponse.getEntity(String.class));
				return; // Ausstieg bei Fehler!
			}

			System.out.println(clientResponse.getEntity(String.class));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDaten() {
		return "in getDaten";
	}

	public void starteMessreihe(int messreihenId, int zeitIntervall, String messgroesse) {
		this.threadTimer = new ThreadTimer(messreihenId, zeitIntervall, messgroesse); // Damit der erste Eintrag nie 0
																						// wird!
		this.threadTimer.starteMessreihe();

	}

	public void stoppeMessreihe() {
		this.threadTimer.stoppeMessreihe();
	}
}
