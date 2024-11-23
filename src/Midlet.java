package javax.microedition.midlet;

import java.util.HashMap;

import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.pki.*;
import javax.microedition.rms.*;

public abstract class MIDlet
{

    public static HashMap<String, String> properties;

    private Display display = new Display();

    protected MIDlet()
    {
        System.out.println("Create new MIDlet");
    }


    public final int checkPermission(String permission)
    {
        // 0 - denied; 1 - granted; -1 unknown
        System.out.println("checkPermission:" + permission);
        return -1;
    }

    protected abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;

    public String getAppProperty(String key)
    {
        return Properties.get(key);
    }
