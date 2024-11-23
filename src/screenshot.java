package com.loading.shine

import org.recompile.mobile*;

import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.lang.Exception;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.File;
import java.nio.file.Paths;
import java.nio.file.Path;

public class screenshot 
{

    public static void takeScreenshot(boolean saveToHomeDirectory)
    {
        try
        {

            Date date = new Date();
            String filename = "screenshot_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
            Path filepath = Paths.get(System.getenv("HOME"), filename);

            if (saveToHomeDirectory == true) 
            {
                filename