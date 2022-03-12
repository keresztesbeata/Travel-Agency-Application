package presentation.views;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.controller.ViewLoaderFactory;

import java.io.IOException;

public class MainView extends Application {
    private ViewLoaderFactory viewLoaderFactory = new ViewLoaderFactory();

    @Override
    public void start(Stage primaryStage) throws IOException {
        viewLoaderFactory.openMainView();
    }
}
