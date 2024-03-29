package it.paola.texteditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
     try{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Editor.fxml"));
         BorderPane root = loader.load();
         primaryStage.setTitle("Editor");
         Controller ctrl = loader.getController();
         ctrl.init(primaryStage);
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
     } catch (Exception e){
         e.printStackTrace();
     }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
