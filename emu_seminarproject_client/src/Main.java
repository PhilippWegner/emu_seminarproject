import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("EMU-Anwendung");
			FXMLLoader loader = new FXMLLoader();
			System.out.println("LOCATION: " + getClass().getResource("gui/BasisView.fxml"));
			loader.setLocation(getClass().getResource("gui/BasisView.fxml"));

			BorderPane root = loader.load();
			Scene scene = new Scene(root, 750, 490);
			primaryStage.setScene(scene);
			primaryStage.show();
			// new BasisControl(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		launch(args);
	}

}