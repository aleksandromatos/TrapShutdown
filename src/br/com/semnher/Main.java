package br.com.semnher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.UnknownHostException;

import br.com.semnher.core.ServiceSnmp;

public class Main extends Application {

	public Scene scene;
	public Stage primaryStage;
	public AnchorPane rootLayout;

	ServiceSnmp svc_snmp = new ServiceSnmp();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("");
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.getIcons().addAll(new Image(Main.class.getResourceAsStream("remote.png")));

		initRootLayot();

	}

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		launch(args);

	}

	public void initRootLayot() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayot.fxml"));
			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(this.getClass().getResource("css/default.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			
			primaryStage.show();

		} catch (Exception e) {
			
		}

	}

//	public void desTra() {
//		svc_snmp.run();
//	}
//
//	public void travTra() {
//		svc_snmp.serviceSnmp_stop();
//	}

}
