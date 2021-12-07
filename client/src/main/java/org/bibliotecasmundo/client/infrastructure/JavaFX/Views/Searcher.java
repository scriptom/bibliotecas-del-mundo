package org.bibliotecasmundo.client.infrastructure.JavaFX.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

public class Searcher extends GridPane {

    public Searcher(){
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        addUIControls(this);
    }

    private void addUIControls(GridPane gridPane) {
        // Encabezado
        Label headerLabel = new Label("Busqueda en Bliblioteca");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Label para las palabras clave
        Label keywordsLabel = new Label("Palabras clave : ");
        gridPane.add(keywordsLabel, 0,1);

        // Campo para las palabras clave
        TextField keywordsField = new TextField();
        keywordsField.setPrefHeight(50);
        keywordsField.setPrefHeight(40);
        gridPane.add(keywordsField, 1, 1);



        // ComboBox para el tipo de busqueda
        Label emailLabel = new Label("Tipo de Busqueda : ");
        gridPane.add(emailLabel, 0, 2);

        //Se crear una lista con los objetos que se pueden buscar, en este caso solo son libros o autores
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Libro", "Autor");
        // Se crear el ComboBox con los items
        ComboBox<String> objectType = new ComboBox<>(items);
        objectType.setPrefWidth(200);
        objectType.setPrefHeight(40);
        gridPane.add(objectType, 1,2);


        // Label que la biblioteca
        Label libraryLabel = new Label("Biblioteca : ");
        gridPane.add(libraryLabel, 0, 4);

        // Se crea el array con las bibliotecas
        ObservableList<String> libraryItems = FXCollections.observableArrayList();
        libraryItems.addAll("A", "B","C");
        //Se crear el comb con las bibliotecas
        ComboBox<String> libraryComb = new ComboBox<>(libraryItems);
        libraryComb.setPrefWidth(200);
        libraryComb.setPrefHeight(40);
        gridPane.add(libraryComb, 1,4);

        // Boton de busqueda
        Button submitButton = new Button("Buscar");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 5, 1, 2, 1);
        GridPane.setMargin(libraryComb, new Insets(20, 0,20,0));
        GridPane.setMargin(submitButton, new Insets(20, 0,20,20));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(keywordsField.getText().isEmpty()) {
                    // create a alert
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error en la busqueda", "No se introdujo ninguna palabra clave");
                    System.out.println('a');
                    return;
                }
                if (objectType.getValue() == null){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error en la busqueda", "No se selecciono ningún tipo de busqueda");
                    System.out.println('b');
                    return;
                }

            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
