package cn.com.quartzTest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties ;
    static {
        properties = new Properties();
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("quartzJob.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getValue(String key){
        String propertyValue = properties.getProperty(key);
        return propertyValue ;
    }
}
