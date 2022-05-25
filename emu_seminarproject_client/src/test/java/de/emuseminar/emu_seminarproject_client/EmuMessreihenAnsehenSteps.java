package de.emuseminar.emu_seminarproject_client;

//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import de.emuseminar.emu_seminarproject_client.business.BasisModel;
import de.emuseminar.emu_seminarproject_client.business.Messreihe;
import de.emuseminar.emu_seminarproject_client.business.Messung;
import javafx.collections.ObservableList;

public class EmuMessreihenAnsehenSteps {
	private BasisModel basisModel = BasisModel.getInstance();
	private Messreihe messreihe;
	private Exception exc;
	
	// Szenario 01
	@Given("Eine Messreihe ohne Messungen")
	public void givenEineMessreiheOhneMessungen() {
		try {
			this.basisModel.leseMessreihenInklusiveMessungenAusDb();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
		}

		ObservableList<Messreihe> messreihen = this.basisModel.getMessreihen();
		for (int i = 0; i < messreihen.size(); i++) {
			if (messreihen.get(i).getMessungen().length == 0) {
				this.messreihe = messreihen.get(i);
				break;
			}
		}
	}

	@When("Es werden $anzahlMessungen Messungen zur leeren Messreihe hinzugefuegt")
	public void whenEsWerdenanzahlMessungenMessungenZurLeerenMessreiheHinzugefuegt(int anzahlMessungen) {
		for (int laufendeNummer = 1; laufendeNummer <= anzahlMessungen; laufendeNummer++) {
			try {
				this.basisModel.speichereMessungInDb(this.messreihe.getMessreihenId(), new Messung(laufendeNummer, 42));
			} catch (SQLException e) {}
		}
	}

	@Then("Sind $anzahlMessungen Messungen in der Messreihe enthalten")
	public void thenSindanzahlMessungenMessungenInDerMessreiheEnthalten(int anzahlMessungen) {
		try {
			this.basisModel.leseMessreihenInklusiveMessungenAusDb();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObservableList<Messreihe> tempMessreihen = this.basisModel.getMessreihen();
		for(Messreihe mr : tempMessreihen) {
			if(mr.getMessreihenId() == this.messreihe.getMessreihenId()) {
				this.messreihe = mr;
			}
		}
		assertEquals(anzahlMessungen, this.messreihe.getMessungen().length);
	}

	// Szenario 02
	@Given("Eine Messreihe mit Messungen")
	public void givenEineMessreiheMitMessungen() {
		try {
			this.basisModel.leseMessreihenInklusiveMessungenAusDb();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
		}

		ObservableList<Messreihe> messreihen = this.basisModel.getMessreihen();
		for (int i = 0; i < messreihen.size(); i++) {
			if (messreihen.get(i).getMessungen().length > 0) {
				this.messreihe = messreihen.get(i);
				break;
			}
		}
	}

	@When("Es wird $anzahlMessungen Messung zur gefuellten Messreihe hinzugefuegt")
	public void whenEsWirdanzahlMessungenMessungenZurGefuelltenMessreiheHinzugefuegt(int anzahlMessungen) {
		for (int laufendeNummer = 1; laufendeNummer <= anzahlMessungen; laufendeNummer++) {
			try {
				this.basisModel.speichereMessungInDb(this.messreihe.getMessreihenId(), new Messung(laufendeNummer, 42));
			} catch (SQLException e) {
				this.exc = e;
			}
		}
	}

	@Then("Ueberpruefen ob eine Exception geworfen wurde")
	public void thenUeberpruefenObEineExceptionGeworfenWurde() {
		assertEquals(this.exc.getMessage(), "Fehler beim Eintragen in die Datenbank!");
	}
}
