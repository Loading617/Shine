#include "j2me responsive key"

private static int currentKey = -1;

public static void keyPressed(int keyCode) {
    currentKey = keyCode;
}

public static int getCurrentKey() {
    return currentKey;
}

public static void resetCurrentKey() {
    currentKey = -1;
}

// Example usage

public void update() {
    int key = getCurrentKey();

    if (key == KEY_UP) {
        // Handle up key press
    } else if (key == KEY_DOWN) {
        // Handle down key press
    } else if (key == KEY_LEFT) {
        // Handle left key press
    } else if (key == KEY_RIGHT) {
        // Handle right key press
    }

    resetCurrentKey();
}
