import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shine");
        
        MenuBar menubar = new MenuBar();
        
        Menu midletMenu = new Menu("Midlet");
        Menu toolsMenu = new Menu("Tools");
        Menu viewMenu = new Menu("View");
        
        MenuItem newItem = new MenuItem("Load jar");
        MenuItem newItem = new MenuItem("Recent");
        MenuItem newItem = new MenuItem("Exit");
        
        exitItem.setOnAction(e -> primaryStage.close());
        
        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem(), exitItem);
        
        menuBar.getMenus().addAll(midletMenu, recentMenu, viewMenu);
        
        VBox root = new VBox(menubar);
        Scene Scene = new Scene(root, 240, 320);
        
        Scene scene = new Scene(root, 240, 320);
        primaryStage.setScene(scene);
        
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
