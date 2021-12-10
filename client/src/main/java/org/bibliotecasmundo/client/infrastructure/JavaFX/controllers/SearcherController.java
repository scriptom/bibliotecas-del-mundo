package org.bibliotecasmundo.client.infrastructure.JavaFX.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.bibliotecasmundo.client.application.useCases.SearchInLibraryUseCase;
import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.client.infrastructure.model.SimpleBook;
import org.bibliotecasmundo.shared.application.query.Language;
import org.bibliotecasmundo.shared.application.query.Query;
import org.bibliotecasmundo.shared.domain.book.Book;
import org.bibliotecasmundo.shared.infrastructure.query.QueryFactory;
import org.bibliotecasmundo.shared.infrastructure.tools.EntityMapper;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearcherController implements Initializable, EntityMapper<SimpleBook, Book> {
    private final static String TITLE_TYPE = "Title";
    private final static String AUTHOR_TYPE = "Author";
    @FXML
    private TextField keywordsField;
    @FXML
    private ComboBox<String> searchTypeField;
    @FXML
    private ComboBox<Library> libraryField;
    @FXML
    private TableView<SimpleBook> table;

    private final String filename;

    private final SearchInLibraryUseCase searcher;

    private final XmlUnmarshaller<List<Library>> unmarshaller;

    private final Language language;

    public SearcherController(String filename, SearchInLibraryUseCase searcher, XmlUnmarshaller<List<Library>> unmarshaller, Language language) {
        this.filename = filename;
        this.searcher = searcher;
        this.unmarshaller = unmarshaller;
        this.language = language;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Cargo la lista de types de busqueda
        ObservableList<String> types = FXCollections.observableArrayList(TITLE_TYPE, AUTHOR_TYPE);
        searchTypeField.setItems(types);

        // Extract XML body from filename
        try {
            String xmlBody = readLibraryXmlFile();
            //Cargo las librerías con sus respectivos nombre, puerto y dirección
            List<Library> libraries = unmarshaller.fromXml(xmlBody);
            //Cargo la lista de bibliotecas disponibles
            libraryField.setItems(FXCollections.observableArrayList(libraries));
            // Set cell factory for library combo box
            libraryField.setCellFactory(param -> new LibraryListCell());
            libraryField.setButtonCell(new LibraryListCell());
            libraryField.setConverter(new LibraryStringConverter(libraries));
        } catch (IOException e) {
            e.printStackTrace();
        }


        initTableColumns();
    }

    private String readLibraryXmlFile() throws IOException {
        InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private void initTableColumns() {
        //Creo la columna de la tabla
        TableColumn<SimpleBook, String> column1 = new TableColumn<>("Nombre");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column1.prefWidthProperty().bind(table.widthProperty().multiply(0.5));

        TableColumn<SimpleBook, String> column2 = new TableColumn<>("Autor");
        column2.setCellValueFactory(new PropertyValueFactory<>("author"));
        column2.prefWidthProperty().bind(table.widthProperty().multiply(0.5));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
    }

    @FXML
    private void submit(ActionEvent event) {
        if (keywordsField.getText().isEmpty()) {
            // create a alert
            showAlert(Alert.AlertType.ERROR, "Error en la busqueda", "No se introdujo ninguna palabra clave");
            return;
        }

        if (searchTypeField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error en la busqueda", "No se selecciono ningún tipo de búsqueda");
            return;
        }

        String keyword = keywordsField.getText();
        String searchType = searchTypeField.getValue();
        Library targetLibrary = libraryField.getValue();
        Query query = null;
        if (TITLE_TYPE.equals(searchType)) {
            query = QueryFactory.createTitleQuery(language, keyword);
        } else if (AUTHOR_TYPE.equals(searchType)) {
            query = QueryFactory.createAuthorQuery(language, keyword);
        }

        if (query != null) {
            List<Book> books = searcher.queryInLibrary(query, targetLibrary);
            List<SimpleBook> simpleBooks = mapList(books);
            table.getItems().clear();
            table.getItems().addAll(simpleBooks);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public SimpleBook map(Book source) {
        return new SimpleBook(source.getTitle().getValue(), source.getAuthor().getValue());
    }

    private static class LibraryListCell extends ListCell<Library> {
        @Override
        protected void updateItem(Library item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
            } else {
                setText(item.getName().getValue());
            }
        }
    }

    private static class LibraryStringConverter extends StringConverter<Library> {
        private final List<Library> libraries;

        public LibraryStringConverter(List<Library> libraries) {
            this.libraries = libraries;
        }

        @Override
        public String toString(Library object) {
            return object != null ? object.getName().getValue() : "";
        }

        @Override
        public Library fromString(String string) {
            return libraries.stream().filter(l -> l.getName().getValue().equals(string))
                    .findFirst().orElseThrow(IllegalArgumentException::new);
        }
    }
}
