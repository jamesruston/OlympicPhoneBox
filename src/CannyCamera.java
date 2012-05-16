import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

public class CannyCamera {

    private static int width = 64, height = 80;

    public static void main(String[] args) throws Exception {
        final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(-1);
        grabber.start();

        IplImage frame = grabber.grab();

        CanvasFrame canvasFrame = new CanvasFrame("Canny");
        //canvasFrame.setUndecorated(true);
        canvasFrame.setCanvasSize(width, height);

        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        f.setUndecorated(true);
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    grabber.stop();
                    System.exit(0);
                } catch (FrameGrabber.Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        f.setSize(width, height);
        f.add(canvasFrame.getContentPane());

        JFrame slider = new JFrame("Control");
        JSlider one = new JSlider(0, 100);
        JSlider two = new JSlider(0, 100);

        JTextArea text = new JTextArea();
        slider.add(one, BorderLayout.NORTH);
        slider.add(text, BorderLayout.CENTER);
        slider.add(two, BorderLayout.SOUTH);
        slider.setSize(500, 70);
        slider.setVisible(true);

        f.setVisible(true);

        while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            //IplImage cropped = cvCreateImage(cvSize(width, height), frame.depth(), frame.nChannels());
            IplImage cropped = cvCropMiddle(frame, width, height);
            cvResize(frame, cropped);
            IplImage smooth = cvCreateImage(cvGetSize(cropped), IPL_DEPTH_8U, 3);
            IplImage gray = cvCreateImage(cvGetSize(cropped), IPL_DEPTH_8U, 1);
            IplImage canny = cvCreateImage(cvGetSize(cropped), IPL_DEPTH_8U, 1);
            cvSmooth(cropped, smooth, CV_GAUSSIAN, 11);
            cvSmooth(smooth, smooth, CV_GAUSSIAN, 11);
            cvCvtColor(smooth, gray, CV_RGB2GRAY);
            text.setText("Value 1:" + one.getValue() + "Value 2:" + two.getValue());
            cvCanny(gray, canny, one.getValue(), two.getValue(), 3);
            //cvResize(canny, cropped);*/
            //IplImage canny = cropped;
            cvFlip(canny, canny, 180);
            //cvInvert(canny,canny);
            // cvCvtScale(canny, canny, -1, 100);
            canvasFrame.showImage(canny);
        }
    }

    private static IplImage cvCrop(IplImage frame, int x, int y, int width, int height) {
        IplImage cropped = cvCreateImage(cvSize(width, height), IPL_DEPTH_8U, 3);
        cvSetImageROI(frame, cvRect(x, y, width, height));
        cvCopy(frame, cropped);
        cvResetImageROI(frame);
        return cropped;
    }

    private static IplImage cvCropMiddle(IplImage image, int width, int height) {
        return cvCrop(image, ((image.width() / 2) - (width / 2)), ((image.height() / 2) - (height / 2)), width, height);
    }
}