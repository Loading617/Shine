import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;
import javax.swing.JMenuItem;

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

        JMenu screenSizeMenu = new JMenu("Screen Size");
        String[] screenSizes = {"128x160", "240x320", "360x640", "Custom…"};
        for (String size : screenSizes) {
            JMenuItem item = new JMenuItem(size);
            item.addActionListener(e -> setScreenSize(size));
            screenSizeMenu.add(item);
        }

        JMenu orientationMenu = new JMenu("Orientation");
        String[] orientations = {"Portrait", "Landscape"};
        for (String ori : orientations) {
            JMenuItem item = new JMenuItem(ori);
            item.addActionListener(e -> setOrientation(ori));
            orientationMenu.add(item);
        }

        JMenu zoomMenu = new JMenu("Zoom");
        String[] zooms = {"1x", "2x", "Fit to Window"};
        for (String z : zooms) {
            JMenuItem item = new JMenuItem(z);
            item.addActionListener(e -> setZoom(z));
            zoomMenu.add(item);
        }

        JCheckBoxMenuItem showKeypad = new JCheckBoxMenuItem("Show Keypad", true);
        showKeypad.addActionListener(e -> toggleKeypad(showKeypad.isSelected()));

        JMenu themeMenu = new JMenu("Theme");
        String[] themes = {"Light", "Dark"};
        for (String theme : themes) {
            JMenuItem item = new JMenuItem(theme);
            item.addActionListener(e -> setTheme(theme));
            themeMenu.add(item);
        }

        JCheckBoxMenuItem showFPS = new JCheckBoxMenuItem("Show FPS");
        showFPS.addActionListener(e -> toggleFPS(showFPS.isSelected()));

        JCheckBoxMenuItem showDebugInfo = new JCheckBoxMenuItem("Show Debug Info");
        showDebugInfo.addActionListener(e -> toggleDebugInfo(showDebugInfo.isSelected()));

        viewMenu.add(screenSizeMenu);
        viewMenu.add(orientationMenu);
        viewMenu.add(zoomMenu);
        viewMenu.addSeparator();
        viewMenu.add(showKeypad);
        viewMenu.addSeparator();
        viewMenu.add(themeMenu);
        viewMenu.addSeparator();
        viewMenu.add(showFPS);
        viewMenu.add(showDebugInfo);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem settingsItem = new JMenuItem("Preferences");
        settingsItem.addActionListener(e -> openPreferences());
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
            System.out.println("Loaded JAR: " + selectedFile.getAbsolutePath());
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
                Rectangle screenRect = new Rectangle(frame.getLocationOnScreen(), frame.getSize());
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
                    System.out.println("Audio line not supported.");
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

    private void setScreenSize(String size) {
        System.out.println("Set screen size to: " + size);
    }

    private void setOrientation(String orientation) {
        System.out.println("Set orientation to: " + orientation);
    }

    private void setZoom(String zoom) {
        System.out.println("Set zoom to: " + zoom);
    }

    private void toggleKeypad(boolean show) {
        System.out.println("Toggle keypad: " + show);
    }

    private void setTheme(String theme) {
        System.out.println("Set theme: " + theme);
    }

    private void toggleFPS(boolean show) {
        System.out.println("Toggle FPS display: " + show);
    }

    private void toggleDebugInfo(boolean show) {
        System.out.println("Toggle debug info: " + show);
    }

    private void openPreferences() {
        System.out.println("Open preferences window");
    }
}
