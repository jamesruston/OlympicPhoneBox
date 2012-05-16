package olympic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import olympic.filters.Canny;
import olympic.filters.ImageFilter;
import olympic.filters.Motion;
import olympic.util.Configuration;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2012 Oliver
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import static olympic.util.Configuration.config;

public class Application {

    private ArrayList<ImageFilter> imageFilters;

    public static void main(String[] args) throws Exception {
        new Application();

        /*olympic.util.Timer swap = new olympic.util.Timer(5000);
        boolean choice = false;
        ImageFilter[] filters = new ImageFilter[]{new Canny(), new Motion()};

        final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(-1);
        grabber.start();

        opencv_core.IplImage frame = grabber.grab();
        final CanvasFrame canvasFrame = new CanvasFrame("Motion");
        canvasFrame.setCanvasSize(64, 80);
        //canvasFrame.setUndecorated(true);

        final JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        f.setUndecorated(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    grabber.release();
                    grabber.stop();
                    canvasFrame.dispose();
                    f.dispose();
                    System.exit(0);
                } catch (FrameGrabber.Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        f.setSize(64, 80);
        f.add(canvasFrame.getContentPane());
        f.setVisible(true);

        while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            ImageFilter filter = (choice ? filters[0] : filters[1]);
            canvasFrame.showImage(filter.process(frame));
            if (swap.up()) {
                choice = !choice;
                swap.restart();
            }
        }*/
    }

    Application() {
        //load filters from config file
        imageFilters = new ArrayList<ImageFilter>(config().filters.length);
        //loop though the filter names
        for (String filterClass : config().filters) {
            try {
                //locate the class
                Class clazz = Class.forName(filterClass);
                //get the class constructor through reflection
                Constructor constructor = clazz.getConstructor();
                //instantiate the class through reflection and add its instance to an array list
                imageFilters.add((ImageFilter) constructor.newInstance());
            } catch (Exception e) {
                System.err.println("filter " + filterClass + " not found.");
                //go back to the start of the for-loop
                continue;
            }
            System.out.println("filter " + filterClass + " loaded.");
        }
    }
}
