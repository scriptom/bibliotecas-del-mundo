package org.bibliotecasmundo.client.infrastructure.JavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.bibliotecasmundo.client.infrastructure.JavaFX.Views.Searcher;

public class ClientApplication extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Searcher");
        Searcher layout = new Searcher();
        Scene scene = new Scene(layout,1000,800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
