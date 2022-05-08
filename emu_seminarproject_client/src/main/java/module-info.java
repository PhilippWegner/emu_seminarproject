module de.emuseminar.emu_seminarproject_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    requires java.sql;
	requires commons.cli;
	
	requires jakarta.ws.rs;
	requires jakarta.xml.bind;
	

    opens de.emuseminar.emu_seminarproject_client to javafx.fxml, javafx.control, javafx.graphics;
    exports de.emuseminar.emu_seminarproject_client;
    opens de.emuseminar.emu_seminarproject_client.gui to javafx.fxml, javafx.control, javafx.graphics;
    exports de.emuseminar.emu_seminarproject_client.gui;
    exports de.emuseminar.emu_seminarproject_client.business;
}
