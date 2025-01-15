import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shine");

        StackPane root = new StackPane();
        
        Scene scene = new Scene(root, 150, 150);
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
