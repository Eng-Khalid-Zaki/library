package org.example.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Teacher extends User{

    public Teacher(int id, String name, int maxBooksAllowed) throws IOException {
        super(id, name, maxBooksAllowed);
        if(maxBooksAllowed <= 0) {
            setMaxBooksAllowed(getDefaultMaxBooksAllowed());
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/configs.properties")) {
            props.load(fis);
        }
        return props;
    }

    private static int getDefaultMaxBooksAllowed() throws IOException {
        Properties props = loadProperties();
        return Integer.parseInt(props.getProperty("defaultMaxBooksLimit"));

    }
}
