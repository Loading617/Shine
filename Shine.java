import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        
        
        MenuBar menuBar = new MenuBar();

        Menu midletMenu = new Menu("Midlet");
        Menu toolsMenu = new Menu("Tools");
        Menu viewMenu = new Menu("View");

        MenuItem midletItem = new MenuItem("Load Jar/Jad File");
        MenuItem recentItem = new MenuItem("Recent");
        MenuItem recordItem = new MenuItem("Record");
        MenuItem screenshotItem = new MenuItem("Screenshot");
        MenuItem exitItem = new MenuItem("Exit");
        
        
        exitItem.setOnAction(e -> primaryStage.close());

        midletMenu.getItems().addAll(midletItem, recentItem, recordItem, screenshotItem, new SeparatorMenuItem(), exitItem);

        menuBar.getMenus().addAll(midletMenu, toolsMenu, viewMenu);

        VBox root = new VBox(menuBar);
        Scene scene = new Scene(root, 240, 320);

        primaryStage.setTitle("Shine");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
public class SystemThemeDetector {
    public static boolean isDarkMode() {
        try {
            Process process = Runtime.getRuntime()
                    .exec("reg query HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize /v AppsUseLightTheme");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().endsWith("0")) { 
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false; 
    }
}
    public static void main(String[] args) {
        launch(args);
    }
}
