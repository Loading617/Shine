import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;

public class ShineSwing {
    private boolean recording = false;
    private ByteArrayOutputStream audioOut;
    private TargetDataLine line;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShineSwing().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Shine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(157, 209);
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();

        JMenu midletMenu = new JMenu("Midlet");
        JMenuItem loadItem = new JMenuItem("Load JAR");
        JMenuItem recentItem = new JMenuItem("Recent");
        JMenuItem recordItem = new JMenuItem("Record");
        JMenuItem screenshotItem = new JMenuItem("Screenshot");
        JMenuItem exitItem = new JMenuItem("Exit");

        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));

        loadItem.addActionListener(e -> openJarFile());
        recordItem.addActionListener(e -> toggleRecording(frame));
        screenshotItem.addActionListener(e -> takeScreenshot(frame));
        exitItem.addActionListener(e -> frame.dispose());

        midletMenu.add(loadItem);
        midletMenu.add(recentItem);
        midletMenu.add(recordItem);
        midletMenu.add(screenshotItem);
        midletMenu.addSeparator();
        midletMenu.add(exitItem);

        JMenu viewMenu = new JMenu("View");
        JMenuItem fullscreenItem = new JMenuItem("Fullscreen");
        fullscreenItem.addActionListener(e -> {
            frame.dispose();
            frame.setUndecorated(!frame.isUndecorated());
            frame.setVisible(true);
            frame.setExtendedState(frame.getExtendedState() ^ JFrame.MAXIMIZED_BOTH);
        });
        viewMenu.add(fullscreenItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem settingsItem = new JMenuItem("Preferences");
        toolsMenu.add(settingsItem);

        menuBar.add(midletMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private void openJarFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load JAR File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JAR Files", "jar"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected JAR: " + selectedFile.getAbsolutePath());
        }
    }

    private void takeScreenshot(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Screenshot");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Files", "png"));

        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
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

    private void toggleRecording(Component parent) {
        if (!recording) {
            startRecording();
        } else {
            stopRecording(parent);
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

    private void stopRecording(Component parent) {
        recording = false;
        line.stop();
        line.close();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Recording");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("WAV Files", "wav"));

        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(audioOut.toByteArray());
                System.out.println("Recording saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
