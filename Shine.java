import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        MenuBar menuBar = new MenuBar();

        Menu midletMenu = new Menu("Midlet");
        Menu toolsMenu = new Menu("Tools");
        Menu viewMenu = new Menu("View");

        MenuItem midletItem = new MenuItem("Load Jar");
        MenuItem recentItem = new MenuItem("Recent");
        MenuItem recordItem = new MenuItem("Record");
        MenuItem screenshotItem = new MenuItem("Screenshot");
        MenuItem exitItem = new MenuItem("Exit");
        
        exitItem.setOnAction(e -> primaryStage.close());

        midletMenu.getItems().addAll(midletItem, recentItem, recordItem, screenshotItem, new SeparatorMenuItem(), exitItem);

        menuBar.getMenus().addAll(midletMenu, toolsMenu, viewMenu);

        VBox root = new VBox(menuBar);
        Scene scene = new Scene(root, 160, 170);
        
    primaryStage.setTitle("Shine");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
        
 private void openJarFile(Stage stage) {
     FileChooser fileChooser = new FileChooser();
     fileChooser.setTitle(Load JAR File);
     fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));
        
     File selectedFile = fileChooser.showOpenDialog(stage);
     if (selectedFile != null) {
         System.out.println("Selected JAR: " + selectedFile.getAbsolutePath());
     }
 }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
