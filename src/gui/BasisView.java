package gui;

import business.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BasisView {

	private BasisModel basisModel;
	private BasisControl basisControl;
	
	private Pane pane = new Pane();
	private Label lblMessreihenId = new Label("MessreihenId");
	private Label lblLaufendeNummer = new Label("lfd. Nr. der Messung");
	private TextField txtMessreihenId = new TextField();
	private TextField txtLaufendeNummer = new TextField();
	private TextField txtAnzeige = new TextField();
	private Button btnLeseMessungenAusDB = new Button("Messungen aus DB lesen");
	private Button btnHoleMessungVonEMU = new Button("Messung aus EMU aufnehmen");
	private Button btnStarteMessreihe = new Button("Start: Messreihe aufnehmen");
	private Button btnStoppeMessreihe = new Button("Stopp: Messreihe aufnehmen");
	
	 
	public BasisView(BasisControl basisControl, Stage stage, BasisModel basisModel){
	    Scene scene = new Scene(this.pane, 510, 240);
	    stage.setScene(scene);
	    stage.setTitle("EMU-Anwendung");
		this.basisControl = basisControl;
		this.basisModel = basisModel;
	    this.initKomponenten();
	    this.initListener();
	}
	
	private void initKomponenten(){
		// Labels
		lblMessreihenId.setLayoutX(10);
		lblMessreihenId.setLayoutY(30);
		lblLaufendeNummer.setLayoutX(10);
		lblLaufendeNummer.setLayoutY(70);
		pane.getChildren().addAll(lblMessreihenId, lblLaufendeNummer); 
		// Textfelder
		txtMessreihenId.setLayoutX(140);
		txtMessreihenId.setLayoutY(30);
		txtLaufendeNummer.setLayoutX(140);
		txtLaufendeNummer.setLayoutY(70);
		txtAnzeige.setLayoutX(10);
		txtAnzeige.setLayoutY(110);
		txtAnzeige.setPrefWidth(480);
       	pane.getChildren().addAll(txtMessreihenId, txtLaufendeNummer, txtAnzeige);
     	// Buttons
       	btnLeseMessungenAusDB.setLayoutX(310);
       	btnLeseMessungenAusDB.setLayoutY(30);
       	btnLeseMessungenAusDB.setPrefWidth(180);
       	btnHoleMessungVonEMU.setLayoutX(310);
    	btnHoleMessungVonEMU.setLayoutY(70);
    	btnHoleMessungVonEMU.setPrefWidth(180);
        btnStarteMessreihe.setLayoutX(310);
        btnStarteMessreihe.setLayoutY(150);
        btnStarteMessreihe.setPrefWidth(180);
        btnStoppeMessreihe.setLayoutX(310);
        btnStoppeMessreihe.setLayoutY(190);
        btnStoppeMessreihe.setPrefWidth(180);
        pane.getChildren().addAll(
        	btnLeseMessungenAusDB, btnHoleMessungVonEMU,
        	btnStarteMessreihe, btnStoppeMessreihe); 
	} 
	 
	private void initListener(){
		this.btnLeseMessungenAusDB.setOnAction(new EventHandler<ActionEvent>(){
			@Override
		    public void handle(ActionEvent e) {
				Messung[] ergMessungen 
				    = basisControl.leseMessungenAusDb(txtMessreihenId.getText());
				String erg = "";
				for(int i = 0; i < ergMessungen.length; i++){
					erg = erg + ergMessungen[i].gibAttributeAus() + " / ";
				}
				txtAnzeige.setText(erg);
			}
		});
		this.btnHoleMessungVonEMU.setOnAction(aEvent -> {
			Messung messung = this.basisControl.holeMessungVonEMU(txtMessreihenId.getText(), txtLaufendeNummer.getText());
			txtAnzeige.setText(messung.gibAttributeAus());
		});
		
		this.btnStarteMessreihe.setOnAction(aEvent -> {
			this.basisControl.starteMessreiheAufnehmen(txtMessreihenId.getText(), txtLaufendeNummer.getText());
		});
		
		this.btnStoppeMessreihe.setOnAction(aEvent -> {
			this.basisControl.stopMessreiheAufnehmen();
		});
	}
	 
	void zeigeFehlermeldungAn(String meldung){
	   	Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Fehlermeldung");
	    alert.setContentText(meldung);
	    alert.showAndWait();
	}
	  
}

