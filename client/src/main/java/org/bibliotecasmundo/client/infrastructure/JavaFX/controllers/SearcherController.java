package org.bibliotecasmundo.client.infrastructure.JavaFX.controllers;

import com.google.common.collect.ImmutableMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bibliotecasmundo.client.application.useCases.searchInLibrary;
import org.bibliotecasmundo.client.application.xml.mapper.xmlMapper;
import org.bibliotecasmundo.client.application.xml.modelos.libraryInfo;
import org.bibliotecasmundo.client.domain.book;
import org.bibliotecasmundo.client.infrastructure.adapters.libraryRepository;
import org.bibliotecasmundo.shared.application.query.LibraryQueryLanguage;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;


import java.net.URL;
import java.util.Collections;
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

    private xmlMapper xmlMapp;

    private libraryInfo[] libraries;

    private LibraryQueryLanguage queryBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Descomentar cuando se haga la funcion de RMI
        //adapter = new libraryRepository();
        adapter = new libraryRepository();
        xmlMapp = new xmlMapper(getClass().getClassLoader().getResource("xml/Bibliotecas.xml").getPath());

        //Cargo la lista de types de busqueda
        ObservableList<String> types = FXCollections.observableArrayList("Title", "Author");
        searchTypeField.setItems(types);

        //Cargo las librerias con sus respectivos nombre, puerto y direccion
        libraries = xmlMapp.readFile();
        //Como no puedo mostrar el nombre facilmente, hago un array con nombres y lo paso
        String[] names = new String[libraries.length + 1];
            names[libraries.length] = "Local";
        for(int i = 0; i < libraries.length;i++){
            names[i] = libraries[i].getNombre();
        }

        //Cargo la lista de bibliotecas disponibles
        ObservableList<String> librarys = FXCollections.observableArrayList(names);;
        libraryField.setItems(librarys);

        setTableColumn();

        //Inicializo el traductor de querys
        queryBuilder = (LibraryQueryLanguage) LibraryQueryLanguage.buildFromConfiguration("Biblioteca Ingles", AppConfig.createFromMap(ImmutableMap.<String, String>builder()
                .put(AppConfig.QUERY_TOKENS_TITLE, "Get Title")
                .put(AppConfig.QUERY_TOKENS_AUTHOR, "Get Author")
                .build()));
    }

    private void setTableColumn(){
        //Creo la columna de la tabla
        TableColumn<book, String> column1 = new TableColumn<>("Nombre");
        column1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(1));

        table.getColumns().add(column1);
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

        String keyword = keywordsField.getText();

        String searchType = searchTypeField.getValue().toString();
        libraryInfo libreriaObjetivo = searchLibrary(libraryField.getValue().toString());

        String commonLeng = "Get " + searchType + " " + keyword;
        Query query = queryBuilder.translateFromCommonLanguage(commonLeng);

        //TODO: en data debe almacenarse la respuesta, una vez este implementada
//        String data = queryBuilder.translateToCommonLanguage(adapter.searchQuery(query));
        String data = "Title Jose";
        String[] result = data.split("\n");

        book[] books = new book[result.length];
        for(int i = 0 ; i < result.length ; i++){
            books[i] = new book(result[i].replace("Title ",""));
        }

        if (table.getItems() != null){
            table.getItems().clear();
        }
        table.getItems().addAll(books);
    }

    private libraryInfo searchLibrary (String nombre){
        for(int i = 0; i < libraries.length; i++){
            if (nombre == libraries[i].getNombre())
                return libraries[i];
        }
        //TODO: Revisar esto cuando es local
        return new libraryInfo("local","127.0.0.1","5000");
    }


    private void showAlert(Alert.AlertType alertType,  String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
