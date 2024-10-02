#include "j2me keypad"

int main() {
    Keypad k;
    int key;
    
    k.setKeypadType(Keypad_TYPE_NUMBER_PAD);
    
    while (true) {
        key = k.getKey();
        
        if (key != KEY_NO_KEY) {
            // Process the key press
            // Example: Display the pressed key on the screen
            System.out.println("Pressed key: " + key);
        }
    }
    
    return 0;