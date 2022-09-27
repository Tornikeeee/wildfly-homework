package com.tadamia.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfManager {

    private static final String FILE_LOCATION = "config.properties";
    private volatile static ConfManager _singleton = null;
    private static URL url = null;
    protected long lastModified;

    private BufferedReader bufferedReader;

    private ConfManager(URLConnection conn) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("rectangles.json");
        assert in != null;
        bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//        this.lastModified = conn.getLastModified();
//
//        try (InputStream in = conn.getInputStream()){
//            Properties props = new Properties();
//            props.load(conn.getInputStream());
//            lgg.info("Source reloaded");
//            fillSetting(props);
//        }
    }

    public static ConfManager getConfiguration() throws IOException {
//        if (url == null) {
//            url = ConfManager.class.getClassLoader().getResource(FILE_LOCATION);
//        }
//        if (url == null) {
//            throw new IOException("Source file not found");
//        }
//
//        URLConnection conn = url.openConnection();
//
//        long lastModified = conn.getLastModified();
//        if (_singleton == null || lastModified > _singleton.lastModified) {
//            synchronized (FILE_LOCATION) {
//                if (_singleton == null || lastModified > _singleton.lastModified) {
//                    _singleton = new ConfManager(conn);
//                }
//            }
//        }

        return _singleton;
    }

    private void fillSetting(Properties props) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("rectangles.json");
        assert in != null;
        bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}

