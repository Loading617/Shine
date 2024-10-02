#include "j2me save game data"

// Define the save game data structure

struct SaveGameData {
    int score;
    int highScore;
    char name[20];
    // Add more fields as needed
};

// Function to save the save game data to a file

void saveGameData(const SaveGameData& saveData) {
    FILE* file = fopen("save_game.dat", "wb");

    if (file) {
        fwrite(&saveData, sizeof(SaveGameData), 1, file);
        fclose(file);
    } else {
        printf("Error: Unable to open save_game.dat for writing\n");
    }
    printf("Save game data saved successfully\n");
}
}

// Function to load the save game data from a file

SaveGameData loadGameData() {
    SaveGameData saveData;
    FILE* file = fopen("save_game.dat", "rb");

    if (file) {
        fread(&saveData, sizeof(SaveGameData), 1, file);
        fclose(file);
    } else {
        printf("Warning: save_game.dat not found, creating a new one\n");
        saveData.score = 0;
        saveData.highScore = 0;
        strcpy(saveData.name, "Player");
    }

    return saveData;
}

// Example usage

int main() {
    SaveGameData saveData = loadGameData();

    printf("Current score: %d\n", saveData.score);
    printf("High score: %d\n", saveData.highScore);
    printf("Player name: %s\n", saveData.name);

    saveData.score = 100;
    saveData.highScore = std::max(saveData.highScore, saveData.score);
    strcpy(saveData.name, "New Player");

    saveGameData(saveData);

    return 0;
}
