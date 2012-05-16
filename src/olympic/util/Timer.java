package olympic.util;

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
public class Timer {

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;
    private long wait;

    /**
     * create a new timer instance
     *
     * @param millis time to check in milliseconds
     */
    public Timer(long millis) {
        this.wait = millis;
        start();
    }

    /**
     * start the timer countdown
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * stop the timer
     */
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }


    /**
     * return the elapsed time
     *
     * @return the current elapsed time
     */
    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    /**
     * check to see if the timer has completed
     *
     * @return if the timer has completed
     */
    public boolean up() {
        return getElapsedTime() > wait;
    }

    /**
     * resets the timer by stopping the current timer and starting again
     */
    public void restart() {
        stop();
        start();
    }

}