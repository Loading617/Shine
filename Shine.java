import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import java.io.File;

public class Shine extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        MenuBar menuBar = new MenuBar();

        Menu midletMenu = new Menu("Midlet");
        MenuItem loadItem = new MenuItem("Load JAR");
        MenuItem recentItem = new MenuItem("Recent");
        MenuItem recordItem = new MenuItem("Record");
        MenuItem screenshotItem = new  MenuItem("Screenshot");
        loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        loadItem.setOnAction(e -> openJarFile(primaryStage));
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("ESC"));
        exitItem.setOnAction(e -> primaryStage.close());
        midletMenu.getItems().addAll(loadItem, recentItem, recordItem, screenshotItem, new SeparatorMenuItem(), exitItem);

        Menu viewMenu = new Menu("View");
        MenuItem fullscreenItem = new MenuItem("Fullscreen");
        fullscreenItem.setOnAction(e -> primaryStage.setFullScreen(!primaryStage.isFullScreen()));
        viewMenu.getItems().add(fullscreenItem);

        Menu toolsMenu = new Menu("Tools");
        MenuItem settingsItem = new MenuItem("Preferences");
        toolsMenu.getItems().add(settingsItem);

        menuBar.getMenus().addAll(midletMenu, viewMenu, toolsMenu);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 160, 170);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Shine");
        
        primaryStage.show();
    }

    private void openJarFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load JAR File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Selected JAR: " + selectedFile.getAbsolutePath());

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
