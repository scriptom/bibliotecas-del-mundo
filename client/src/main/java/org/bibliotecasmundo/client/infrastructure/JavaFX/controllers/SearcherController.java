package org.bibliotecasmundo.client.infrastructure.JavaFX.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bibliotecasmundo.client.application.useCases.searchInLibrary;
import org.bibliotecasmundo.client.infrastructure.adapters.libraryRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class SearcherController implements Initializable {

    @FXML
    private TextField keywordsField;
    @FXML
    private ComboBox searchTypeField;
    @FXML
    private ComboBox libraryField;
    @FXML
    private TableView table;

    private searchInLibrary adapter;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Cargo la lista de types de busqueda
        ObservableList<String> types = FXCollections.observableArrayList("Title", "Author");
        searchTypeField.setItems(types);

        //Cargo la lista de bibliotecas disponibles
        ObservableList<String> librarys = FXCollections.observableArrayList("A", "B","C");
        libraryField.setItems(librarys);

        adapter = new libraryRepository();

    }


    @FXML
    private void submit(ActionEvent event) {

        if(keywordsField.getText().isEmpty()) {
            // create a alert
            showAlert(Alert.AlertType.ERROR,  "Error en la busqueda", "No se introdujo ninguna palabra clave");
            System.out.println('a');
            return;
        }

        if (searchTypeField.getValue() == null){
            showAlert(Alert.AlertType.ERROR,  "Error en la busqueda", "No se selecciono ningún tipo de busqueda");
            System.out.println('b');
            return;
        }

        
    }


    private void showAlert(Alert.AlertType alertType,  String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
