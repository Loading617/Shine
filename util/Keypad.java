import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    public void init() {
        setTitle("Shine Keypad");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
       MainFrame myFrame = new MainFrame();
       myFrame.init();
    }

}

// KeypadButton.java

import javax.swing.JButton;

public class KeypadButton extends JButton {
    private String key;

    public KeypadButton(String key) {
        this.key = key;
        setText(key);
    }

    public String getKey() {
        return key;
    }
}

// KeypadPanel.java

import java.awt.GridLayout;
import javax.swing.JPanel;

public class KeypadPanel extends JPanel {
    private KeypadButton[] buttons;

    public KeypadPanel() {
        setLayout(new GridLayout(4, 3));

        buttons = new KeypadButton[20];
        
        buttons[0] = new KeypadButton("Left");
        buttons[1] = new KeypadButton("Right");
        buttons[2] = new KeypadButton("Up Arrow");
        buttons[3] = new KeypadButton("Down Arrow");
        buttons[4] = new KeypadButton("Left Arrow");
        buttons[5] = new KeypadButton("Right Arrow");
        buttons[6] = new KeypadButton("OK");
        buttons[7] = new KeypadButton("Left Upward");
        buttons[8] = new KeypadButton("Left Downward");
        buttons[9] = new KeypadButton("Right Upward");
        buttons[10] = new KeypadButton("Right Downward");
        buttons[11] = new KeypadButton("1");
        buttons[12] = new KeypadButton("2 ABC");
        buttons[13] = new KeypadButton("3 DEF");
        buttons[14] = new KeypadButton("4 GHI");
        buttons[15] = new KeypadButton("5 JKL");
        buttons[16] = new KeypadButton("6 MNO");
        buttons[17] = new KeypadButton("7 PQRS");
        buttons[18] = new KeypadButton("8 TUV");
        buttons[19] = new KeypadButton("9 WXYZ");
        buttons[20] = new KeypadButton("*.");
        buttons[21] = new KeypadButton("0");
        buttons[22] = new KeypadButton("#-+");