package de.emuseminar.emu_seminarproject_client.gui;

import de.emuseminar.emu_seminarproject_client.business.Messreihe;
import de.emuseminar.emu_seminarproject_client.business.Messung;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class DiagrammControl {
	@FXML
	private LineChart<Number, Number> lineChartDiagramm;
	@FXML
	private NumberAxis xAchse;
	@FXML
	private NumberAxis yAchse;

	private Series<Number, Number> kurvenDiagramm;

	public void diagrammBefuellen(Messreihe messreihe) {
		for (Messung messung : messreihe.getMessungen()) {
			this.kurvenDiagramm.getData()
					.add(new XYChart.Data<Number, Number>(messung.getLaufendeNummer(), messung.getWert()));
		}
		this.lineChartDiagramm.setTitle(messreihe.getVerbraucher());
		this.xAchse.setLabel("Laufende Nummber");
		this.yAchse.setLabel(messreihe.getMessgroesse());
		this.kurvenDiagramm.setName("Kurvendiagramm");
	}

	public Series<Number, Number> getSeries(Messung[] messungen) {
		for (Messung messung : messungen) {
			this.kurvenDiagramm.getData()
					.add(new XYChart.Data<Number, Number>(messung.getLaufendeNummer(), messung.getWert()));
		}
		return this.kurvenDiagramm;
	}

	@FXML
	public void initialize() {
		this.lineChartDiagramm.setTitle("Diagramm");
		this.xAchse.setLabel("X-Achse");
		this.yAchse.setLabel("Y-Achse");
		this.kurvenDiagramm = new XYChart.Series<Number, Number>();
		this.kurvenDiagramm.setName("Kurvendiagramm");
		this.lineChartDiagramm.getData().add(this.kurvenDiagramm);
	}
}
