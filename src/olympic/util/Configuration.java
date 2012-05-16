package olympic.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
public class Configuration {

    private static final Gson gson = new GsonBuilder().create();
    private static final Configuration instance;

    public int width;
    public int height;
    public int screen_count;
    public int interval;
    public String[] filters;

    static {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream("config"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        instance = gson.fromJson(reader, Configuration.class);
    }

    public synchronized static Configuration config() {
        return instance;
    }

}
