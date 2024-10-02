#include "restore j2me save data"

int main(int argc, char *argv[]) {
    SaveGameData saveData = restoreSaveGameData();

    printf("Score: %d\n", saveData.score);
    printf("High Score: %d\n", saveData.highScore);
    printf("Name: %s\n", saveData.name);

    return 0;
}

// Example implementation of restoreSaveGameData()

SaveGameData restoreSaveGameData() {
    SaveGameData saveData;

    // Read save data from file or memory
    // Populate saveData with the saved data

    return saveData;
}