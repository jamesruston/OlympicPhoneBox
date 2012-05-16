package olympic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private static Configuration instance;
    private File file;
    private boolean use_xml = false;

    private Properties config_file;

    private Configuration() {
        this("config");
    }

    private Configuration(String file_name) {
        this(file_name, false);
    }

    private Configuration(String file_name, boolean use_xml) {
        if (instance != null)
            throw new RuntimeException();
        this.file = new File(file_name);
        this.use_xml = use_xml;
        this.config_file = new Properties();
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Configuration getConfiguration() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }

    public void put(String key, String value) {
        config_file.put(key, value);
    }

    public void remove(String key) {
        config_file.remove(key);
    }

    public String get(String key) {
        return config_file.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(config_file.getProperty(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(config_file.getProperty(key));
    }

    public List<String> getList(String key) {
        String property = config_file.getProperty(key);
        if (property.startsWith("[") && property.endsWith("]")) {
            property = property.substring(1, property.length() - 1).trim();
            return Arrays.asList(property.split("[\\,\\s+?]+"));
        } else {
            return new ArrayList<String>(0);
        }
    }

    public void save() throws IOException {
        if (use_xml)
            config_file.storeToXML(new FileOutputStream(file), null);
        else
            config_file.store(new FileOutputStream(file), null);
    }

    public void load() throws IOException {
        if (file.exists()) {
            if (use_xml)
                config_file.loadFromXML(new FileInputStream(file));
            else
                config_file.load(new FileInputStream(file));
            //System.out.println("config file loaded successfully.");
        } else {
            System.err.println("config file does not exist.");
            save();
        }
    }

}
