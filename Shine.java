import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Canvas as J2MECanvas;

public class Shine extends Application {

    private File selectedJar;
    private Label midletInfo;
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private boolean running = false;

    @Override
    public void start(Stage primaryStage) {
        
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open JAR...");
        openItem.setOnAction(e -> openJarFile(primaryStage));
        MenuItem runItem = new MenuItem("Run J2ME App");
        runItem.setOnAction(e -> runJ2MEApp());
        runItem.setDisable(true);
        fileMenu.getItems().addAll(openItem, runItem);

        menuBar.getMenus().addAll(fileMenu);

        midletInfo = new Label("No J2ME app loaded");

        gameCanvas = new Canvas(240, 320);
        gc = gameCanvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(gameCanvas);
        root.setBottom(midletInfo);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shine");

        primaryStage.show();
    }

    private void openJarFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JAR File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedJar = file;
            extractMIDletInfo(selectedJar);
        }
    }

    private void extractMIDletInfo(File jarFile) {
        try (JarFile jar = new JarFile(jarFile)) {
            Manifest manifest = jar.getManifest();
            String name = manifest.getMainAttributes().getValue("MIDlet-Name");
            String version = manifest.getMainAttributes().getValue("MIDlet-Version");
            midletInfo.setText("Loaded: " + name + " (v" + version + ")");
        } catch (Exception e) {
            midletInfo.setText("Error loading MIDlet info");
            e.printStackTrace();
        }
    }

    private void runJ2MEApp() {
        if (selectedJar == null) {
            midletInfo.setText("No JAR file selected!");
            return;
        }

        System.out.println("Running J2ME App: " + selectedJar.getAbsolutePath());
        running = true;

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (running) {
                    updateGame();
                    renderGame();
                }
            }
        }.start();
    }

    private void updateGame() {
        
    }

    private void renderGame() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        gc.strokeRect(50, 50, 100, 100);
        gc.fillText("J2ME Game Running!", 70, 200);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
