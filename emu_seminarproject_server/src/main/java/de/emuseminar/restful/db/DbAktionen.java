package de.emuseminar.restful.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import de.emuseminar.restful.Messreihe;
import de.emuseminar.restful.Messung;

public class DbAktionen {
	Statement statement;
	Connection con;

	public Messung[] leseMessungen(int messreihenId) throws SQLException {
		ResultSet ergebnis;
		ergebnis = this.statement.executeQuery("SELECT * FROM Messung WHERE MessreihenId = " + messreihenId);
		Vector<Messung> messungen = new Vector<Messung>();
		while (ergebnis.next()) {
			messungen.add(
					new Messung(Integer.parseInt(ergebnis.getString(1)), Double.parseDouble(ergebnis.getString(2))));
		}
		ergebnis.close();
		return messungen.toArray(new Messung[0]);
	}

	public void fuegeMessungEin(int messreihenId, Messung messung) throws SQLException {
		String insertMessungStatement = "INSERT INTO Messung " + "(LaufendeNummer, Wert, MessreihenId) " + "VALUES("
				+ messung.getLaufendeNummer() + ", " + messung.getWert() + ", " + messreihenId + ")";
		System.out.println(insertMessungStatement);
		this.statement.executeUpdate(insertMessungStatement);
	}

	public Messreihe[] leseMessreihenInklusiveMessungen() throws SQLException {
		ResultSet ergebnis;
		ergebnis = this.statement.executeQuery("SELECT * FROM Messreihe");
		ArrayList<Messreihe> messreihen = new ArrayList<Messreihe>();
		while (ergebnis.next()) {
			messreihen.add(new Messreihe(Integer.parseInt(ergebnis.getString(1)),
					Integer.parseInt(ergebnis.getString(2)), ergebnis.getString(3), ergebnis.getString(4)));
		}
		for (int i = 0; i < messreihen.size(); i++) {
			messreihen.get(i).setMessungen(this.leseMessungen(messreihen.get(i).getMessreihenId()));
		}
		ergebnis.close();
		return messreihen.toArray(new Messreihe[0]);
	}

	public void fuegeMessreiheEin(Messreihe messreihe) throws SQLException {
		String insertMessreiheStatement = "INSERT INTO messreihe "
				+ "(MessreihenId, Zeitintervall, Verbraucher, Messgroesse) " + "VALUES(" + messreihe.getMessreihenId()
				+ ", " + messreihe.getZeitintervall() + ", '" + messreihe.getVerbraucher() + "', '"
				+ messreihe.getMessgroesse() + "')";
		System.out.println(insertMessreiheStatement);
		this.statement.executeUpdate(insertMessreiheStatement);
	}

	public void connectDb() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emu_datenbank?"
				+ "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC", "root", null);
		this.statement = this.con.createStatement();
	}

	public void closeDb() throws SQLException {
		this.con.close();
	}
}
