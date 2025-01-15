import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Shine extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shine");

        primaryStage.setResizable(false);

        primaryStage.initStyle(StageStyle.UTILITY);

        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}