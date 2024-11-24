package javax.microedition.micro;

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

    public static void initAppProperties(HashMap<String, String> initProperties)
	{
		properties = initProperties;
	}

	public final void notifyDestroyed()
	{ 
		System.out.println("MIDlet sent Destroyed Notification");
		System.exit(0);
	}

	public final void notifyPaused() { }

	protected abstract void pauseApp();

	public final boolean platformRequest(String URL) { return false; }

	public final void resumeRequest() { }

	protected abstract void startApp() throws MIDletStateChangeException;

	public Display getDisplay() { return display; }

}