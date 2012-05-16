package olympic.filters;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;

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
public class Motion implements ImageFilter {

    private IplImage prevImage, diff, image;
    private final int threshold;

    public Motion() {
        this(10);
    }

    public Motion(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public IplImage process(IplImage frame) {
        //smooth the image
        cvSmooth(frame, frame, CV_GAUSSIAN, 9, 9, 2, 2);
        if (image == null) {
            image = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
            //convert the initial image to grayscale
            cvCvtColor(frame, image, CV_RGB2GRAY);
        } else {
            //prevImage = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
            //set the previous image to the frame before
            prevImage = image;
            image = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
            //grayscale the new image
            cvCvtColor(frame, image, CV_RGB2GRAY);
        }

        if (diff == null) {
            diff = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
        }

        if (prevImage != null) {
            // perform ABS difference
            cvAbsDiff(image, prevImage, diff);
            // do some threshold for wipe away useless details
            cvThreshold(diff, diff, threshold, 255, CV_THRESH_BINARY);
        }
        cvFlip(diff, diff, 180);
        return diff;
    }
}
