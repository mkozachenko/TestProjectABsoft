package Common;

import java.io.*;
import java.util.*;

public class Utility extends BaseClass{

    public void setEnvironment(Map<String, String> properties){
        String fileName = "environment.properties";
        File envFile = new File(fileName);
        Properties allureProps = getProperties(fileName);
        try{
            FileOutputStream out = new FileOutputStream(envFile);
            for (Map.Entry<String, String> entry : properties.entrySet())
            {
                allureProps.setProperty(entry.getKey(),entry.getValue());
            }
            allureProps.store(new FileOutputStream(envFile), null);
            out.close();
        } catch (IOException ex) {
            System.err.println("Невозможно сохранить файл "+fileName+"\n");
            ex.printStackTrace();
        }
    }

    private Properties getProperties(String file){
        File path = new File(file);
        Properties properties = new Properties();
        try{
            FileInputStream propsFile = new FileInputStream(file);
            properties.load(propsFile);
            propsFile.close();
        } catch (IOException ex) {
            System.err.println("Невозможно прочитать файл "+path.getAbsolutePath()+"\n");
            ex.printStackTrace();
        }
        return properties;
    }

    public Object getRandomElement(List list){
        Object retValue = null;
        if (list.size() > 0){
            retValue = list.get(new Random().nextInt(list.size()));
        }
        return retValue;
    }
}
