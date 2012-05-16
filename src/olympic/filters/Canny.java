package olympic.filters;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static olympic.util.Configuration.config;

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
public class Canny implements ImageFilter {

    private final int t1, t2;

    public Canny() {
        this(50, 50);
    }

    public Canny(int t1, int t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public IplImage process(IplImage image) {
        image = cvCropMiddle(image, config().width * config().screen_count * 2, config().height * 2);
        IplImage smooth = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 3);
        IplImage gray = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        IplImage canny = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        cvSmooth(image, smooth, CV_GAUSSIAN, 11);
        cvSmooth(smooth, smooth, CV_GAUSSIAN, 11);
        cvCvtColor(smooth, gray, CV_RGB2GRAY);
        cvCanny(gray, canny, t1, t2, 3);
        cvFlip(canny, canny, 180);
        return canny;
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
