package org.bibliotecasmundo.client.infrastructure.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bibliotecasmundo.client.application.useCases.SearchInLibraryUseCase;
import org.bibliotecasmundo.client.domain.library.Library;
import org.bibliotecasmundo.client.infrastructure.JavaFX.controllers.SearcherController;
import org.bibliotecasmundo.client.infrastructure.config.ClientAppConfigConstants;
import org.bibliotecasmundo.client.infrastructure.rmi.RmiSearchInLibraryUseCase;
import org.bibliotecasmundo.client.infrastructure.xml.mapper.JAXBXmlUnmarshaller;
import org.bibliotecasmundo.client.infrastructure.xml.mapper.LibraryXmlMapper;
import org.bibliotecasmundo.client.infrastructure.xml.models.LibraryXml;
import org.bibliotecasmundo.shared.application.query.LibraryLanguage;
import org.bibliotecasmundo.shared.infrastructure.config.AppConfig;
import org.bibliotecasmundo.shared.infrastructure.tools.EntityMapper;
import org.bibliotecasmundo.shared.infrastructure.tools.xml.XmlUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class ClientApplication extends Application {
    private static final String VIEW_FILE_PATH = "/views/Searcher.fxml";
    private static final String PROPERTIES_FILE_PATH = "app.client.properties";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("Starting Client GUI Application");
        AppConfig appConfig = AppConfig.createFromPropertiesFile(PROPERTIES_FILE_PATH);
        primaryStage.setTitle(appConfig.getConfigParam(AppConfig.APP_NAME));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(VIEW_FILE_PATH));
        loader.setControllerFactory(c -> {
            SearchInLibraryUseCase searcher = new RmiSearchInLibraryUseCase();
            EntityMapper<Library, LibraryXml> entityMapper = new LibraryXmlMapper();
            XmlUnmarshaller<List<Library>> xmlUnmarshaller = new JAXBXmlUnmarshaller(entityMapper);
            return new SearcherController(
                    appConfig.getConfigParam(ClientAppConfigConstants.HOSTS_FILE_PATH),
                    searcher, xmlUnmarshaller,
                    LibraryLanguage.buildFromConfiguration(appConfig));
        });
        Parent root = loader.load();
        logger.info("Loading view");
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
