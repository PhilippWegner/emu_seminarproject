package de.emuseminar.emu_seminarproject_client.gui;

import java.sql.SQLException;
import de.emuseminar.emu_seminarproject_client.business.BasisModel;
import de.emuseminar.emu_seminarproject_client.business.Messreihe;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.yad2xx.FTDIException;

public class BasisControl {
	@FXML
	private TextField txtMessreihenId;
	@FXML
	private TextField txtZeitintervall;
	@FXML
	private TextField txtVerbraucher;
	@FXML
	private ComboBox<String> cbMessgroesse;
	@FXML
	private Button btnMessreiheStoppen;
	@FXML
	private Button btnMessreiheStarten;
	@FXML
	private TableView<Messreihe> tableView;
	@FXML
	private TableColumn<Messreihe, Integer> clmIdentnummer;
	@FXML
	private TableColumn<Messreihe, Integer> clmZeitIntervall;
	@FXML
	private TableColumn<Messreihe, String> clmVerbraucher;
	@FXML
	private TableColumn<Messreihe, String> clmMessgroesse;
	@FXML
	private TableColumn<Messreihe, String> clmMessungen;

	private BasisModel basisModel;
	private ObservableList<Messreihe> messreihen;

	public BasisControl() {
		this.basisModel = BasisModel.getInstance();
	}

	@SuppressWarnings("unchecked") // prop
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		try {
			this.leseMessreihenInklusiveMessungenAusDb();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.clmIdentnummer.setCellValueFactory(new PropertyValueFactory<Messreihe, Integer>("messreihenId"));
		this.clmZeitIntervall.setCellValueFactory(new PropertyValueFactory<Messreihe, Integer>("zeitintervall"));
		this.clmVerbraucher.setCellValueFactory(new PropertyValueFactory<Messreihe, String>("verbraucher"));
		this.clmMessgroesse.setCellValueFactory(new PropertyValueFactory<Messreihe, String>("messgroesse"));
		// CREDITS:
		// https://stackoverflow.com/questions/43148635/how-to-show-objects-in-tablecolumn
		this.clmMessungen.setCellValueFactory(Messreihe -> {
			@SuppressWarnings("rawtypes") // prop
			SimpleObjectProperty prop = new SimpleObjectProperty();
			prop.setValue(Messreihe.getValue().gibMessungAus());
			return prop;
		});

		this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (tableView.getSelectionModel().getSelectedItem() != null) {
					btnMessreiheStarten.setDisable(false);

					Messreihe selectedRow = tableView.getSelectionModel().getSelectedItem();
					int anzahlMessungenZuMessreihe = selectedRow.getMessungen().length;

					if (anzahlMessungenZuMessreihe == 0) {
						btnMessreiheStarten.setDisable(false);
					} else {
						btnMessreiheStarten.setDisable(true);
					}
				}
			}
		});
	}

	@FXML
	public void speichereMessreiheInDB() {
		if(this.txtMessreihenId.getText().isEmpty()
				|| this.txtZeitintervall.getText().isBlank()
				|| this.txtZeitintervall.getText().isEmpty()
				|| this.txtVerbraucher.getText().isEmpty()
				|| this.cbMessgroesse.getValue().isEmpty()) {
			System.out.println("Felder nicht befuellt.");
			return;
		}
			
		int identNummerMessreihe = Integer.parseInt(this.txtMessreihenId.getText().trim());
		int zeitIntervallSekunden = Integer.parseInt(this.txtZeitintervall.getText().trim());
		String verbraucher = this.txtVerbraucher.getText().trim();
		String messgroesse = this.cbMessgroesse.getValue().trim();
		
		Messreihe messreihe = new Messreihe(identNummerMessreihe, zeitIntervallSekunden, verbraucher, messgroesse);
		try {
			this.basisModel.speichereMessreiheInDb(messreihe);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.leseMessreihenInklusiveMessungenAusDb();

		// Zurücksetzen der Textfelder
		this.txtMessreihenId.setText("");
		this.txtZeitintervall.setText("");
		this.txtVerbraucher.setText("");
//		this.cbMessgroesse.setValue("");
	}

	@FXML
	public void stoppeMessreihe() throws FTDIException {
		this.btnMessreiheStarten.setDisable(true);
		this.btnMessreiheStoppen.setDisable(true);
		
		System.out.println("stoppeMessreihe");
		this.basisModel.stoppeMessreihe();
	}

	@FXML
	public void starteMessreihe() throws FTDIException, InterruptedException {
		this.btnMessreiheStoppen.setDisable(false);
		this.btnMessreiheStarten.setDisable(true);
		
		Messreihe selectedRow = this.tableView.getSelectionModel().getSelectedItem();
		int messreihenId = selectedRow.getMessreihenId();
		int zeitIntervall = selectedRow.getZeitintervall();
		String messgroesse = selectedRow.getMessgroesse();

		System.out.println("starteMessreihe");
		System.out.println(messreihenId + " - " + zeitIntervall + " - " + messgroesse);
		this.basisModel.starteMessreihe(messreihenId, zeitIntervall, messgroesse);
	}

	@FXML
	public void leseMessreihenInklusiveMessungenAusDb() {
		System.out.println("leseMessreihenInklusiveMessungenAusDb");
		try {
			this.basisModel.leseMessreihenInklusiveMessungenAusDb();
			this.messreihen = this.basisModel.getMessreihen();
			this.tableView.getItems().setAll(this.messreihen);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
