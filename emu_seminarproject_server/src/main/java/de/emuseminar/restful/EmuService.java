package de.emuseminar.restful;

import java.sql.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import de.emuseminar.restful.db.DbAktionen;

@Path("/serviceEmu")
public class EmuService {
	private DbAktionen dbAktionen;
	private ObjectMapper objectMapper;

	public EmuService() {
		this.dbAktionen = new DbAktionen();
		this.objectMapper = new ObjectMapper();
	}

	@GET
	@Path("/messreihen")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessreihen() throws ClassNotFoundException, SQLException, JsonProcessingException {
		String messreihenJSON = "";
		this.dbAktionen.connectDb();
		Messreihe[] messreihe = this.dbAktionen.leseMessreihenInklusiveMessungen();
		this.dbAktionen.closeDb();
		messreihenJSON = this.objectMapper.writeValueAsString(messreihe);
		return messreihenJSON;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/messreihe/{MessreihenId}/messungen")
	public String getMessungen(@PathParam("MessreihenId") String MessreihenId)
			throws ClassNotFoundException, SQLException, JsonProcessingException {
		String messungenJSON = "";
		this.dbAktionen.connectDb();
		int MessreihenIdInteger = Integer.parseInt(MessreihenId);
		Messung[] messungen = this.dbAktionen.leseMessungen(MessreihenIdInteger);
		this.dbAktionen.closeDb();
		messungenJSON = this.objectMapper.writeValueAsString(messungen);

		return messungenJSON;
	}

	@POST
	@Path("/messreihe/{MessreihenId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMessreihe(String input, @PathParam("MessreihenId") String MessreihenId)
			throws JsonMappingException, JsonProcessingException, ClassNotFoundException, SQLException {
		Messreihe messreihe = this.objectMapper.readValue(input, Messreihe.class);
		this.dbAktionen.connectDb();
		try {
			this.dbAktionen.fuegeMessreiheEin(messreihe);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).build();
		}
		this.dbAktionen.closeDb();
		String artikelEndpoint = "/webapi/messreihe/" + messreihe.getMessreihenId();
		System.out.println(artikelEndpoint);
		// Response.Status.CREATED sollte 201 sein
		return Response.status(Response.Status.CREATED).entity(artikelEndpoint).build();
	}

	@POST
	@Path("/messung/{MessreihenId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMessung(String input, @PathParam("MessreihenId") String MessreihenId)
			throws ClassNotFoundException, SQLException, JsonMappingException, JsonProcessingException {
		Messung messung = this.objectMapper.readValue(input, Messung.class);
		int MessreihenIdInteger = Integer.parseInt(MessreihenId);
		this.dbAktionen.connectDb();
		try {
			this.dbAktionen.fuegeMessungEin(MessreihenIdInteger, messung);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).build();
		}
		this.dbAktionen.closeDb();
		String artikelEndpoint = "/webapi/messung/" + MessreihenId;
		return Response.status(Response.Status.CREATED).entity(artikelEndpoint).build();
	}

}
