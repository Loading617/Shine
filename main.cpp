#include <CtrlLib/CtrlLib.h>
using namespace Upp;

struct Shine : TopWindow {
    MenuBar menu;
    
    void FileMenu(Bar& bar) {
       bar.Add("Open JAR/JAD...", [=] { PromptOK(""); });
       bar.Add("Open Folder...", [=] { PromptOK(""); });
       bar.Add("Load Last Game", [=] { PromptOK(""); });
       bar.Add("Save State", [=] { PromptOK(""); });
       bar.Add("Load State", [=] { PromptOK(""); });
       bar.Add("Recent Games", [=] { PromptOK(""); });
       bar.Add("Exit", [=] { Exit(); });
    }
       
    void EmuMenu(Bar& bar) {
       bar.Add("Start", [=] { PromptOK(""); });
       bar.Add("Pause", [=] { PromptOK(""); });
       bar.Add("Stop", [=] { PromptOK(""); });
       bar.Add("Reset", [=] { PromptOK(""); });
       bar.Add("Fast Forward", [=] { PromptOK (""); });
       bar.Add("Slow Motion", [=] { PromptOK(""); });
       bar.Add("Frame Limiter", [=] { PromptOK(""); });
       bar.Add("Auto Save State", [=] { PromptOK(""); });
    }
    
    void ViewMenu(Bar& bar) {
       bar.Add("Window Size", [=] { PromptOK(""); });
       bar.Add("Show KeyPad", [=] { PromptOK(""); });
       bar.Add("Color Mode", [=] { PromptOK(""); });
       bar.Add("Dark Mode", [=] { PromptOK(""); });
       bar.Add("Fullscreen", [=] { PromptOK(""); });
    }
    
    void ControlsMenu(Bar& bar) {
       bar.Add("Key Mapping...", [=] { PromptOK(""); });
       bar.Add("Reset to Default", [=] { PromptOK(""); });
       bar.Add("Import Mapping...", [=] { PromptOK(""); });
       bar.Add("Export Mapping...", [=] { PromptOK(""); });
    }
    
    void OptionsMenu(Bar& bar) {
       bar.Add("Enable Sound", [=] { PromptOK(""); });
       bar.Add("Volume", [=] { PromptOK(""); });
       bar.Add("Emulated Device", [=] { PromptOK(""); });
       bar.Add("Screen Resoulution", [=] { PromptOK(""); });
    }
    
    void ToolsMenu(Bar& bar) {
       bar.Add("Debugger", [=] { PromptOK(""); });
       bar.Add("Take Screenshot", [=] { PromptOK(""); });
       bar.Add("Record Gameplay", [=] { PromptOK(""); });
       bar.Add("Log Console", [=] { PromptOK(""); });
       bar.Add("Memory Viewer", [=] { PromptOK(""); });
    }
    
    void HelpMenu(Bar& bar) {
       bar.Add("User Guide", [=] { PromptOK(""); });
       bar.Add("Check for Updates", [=] { PromptOK(""); });
       bar.Add("About Shine", [=] { PromptOK(""); });
       bar.Add("Report a Bug", [=] { PromptOK(""); });
       bar.Add("Discord / Community", [=] { PromptOK(""); });
    }
    
    void MainMenu(Bar& bar) {
        bar.Sub("File", [=](Bar& bar) { FileMenu(bar); });
        bar.Sub("Emu", [=](Bar& bar) { EmuMenu(bar); });
        bar.Sub("View", [=](Bar& bar) { ViewMenu(bar); });
        bar.Sub("Controls", [=](Bar& bar) { ControlsMenu(bar); });
        bar.Sub("Options", [=](Bar& bar) { OptionsMenu(bar); });
        bar.Sub("Tools", [=](Bar& bar) { ToolsMenu(bar); });
        bar.Sub("Help", [=](Bar& bar) { HelpMenu(bar); });
    }
    
    Shine() {
        Title("Shine").Sizeable().MinimizeBox().MaximizeBox();
        AddFrame(menu);
        menu.Set([=](Bar& bar) { MainMenu(bar); });
    }
    
};
    
    GUI_APP_MAIN
    {
           Shine app;
           app.SetRect(0, 0, 240, 320);
           app.Run();
    }