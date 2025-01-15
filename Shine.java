import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Shine");
        
        primaryStage.initStyle(StageStyle.DECORATED);
        
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
