import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class Shine extends Application {
    private boolean recording = false;
    private ByteArrayOutputStream audioOut;
    private TargetDataLine line;

    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu midletMenu = new Menu("Midlet");
        MenuItem loadItem = new MenuItem("Load JAR");
        MenuItem recentItem = new MenuItem("Recent");
        MenuItem recordItem = new MenuItem("Record");
        MenuItem screenshotItem = new MenuItem("Screenshot");
        loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        loadItem.setOnAction(e -> openJarFile(primaryStage));
        
        recordItem.setOnAction(e -> toggleRecording());
        screenshotItem.setOnAction(e -> takeScreenshot(primaryStage));

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

        Scene scene = new Scene(root, 157, 209);
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

    private void takeScreenshot(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Screenshot");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                Robot robot = new Robot();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture = robot.createScreenCapture(screenRect);
                ImageIO.write(capture, "png", file);
                System.out.println("Screenshot saved: " + file.getAbsolutePath());
            } catch (AWTException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void toggleRecording() {
        if (!recording) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line not supported");
                    return;
                }
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                
                audioOut = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                recording = true;
                System.out.println("Recording started...");
                
                while (recording) {
                    int bytesRead = line.read(buffer, 0, buffer.length);
                    audioOut.write(buffer, 0, bytesRead);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void stopRecording() {
        recording = false;
        line.stop();
        line.close();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Recording");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV Files", "*.wav"));
        File file = fileChooser.showSaveDialog(null);
        
        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(audioOut.toByteArray());
                System.out.println("Recording saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
