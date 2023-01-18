package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ListURL {
    public static List<String> getURL( String fileName){
        List<String> listUrl = new ArrayList<String>();
        try{
            File file = new File( Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(fileName)).getFile());
            Scanner myReader = new Scanner( file);
            while( myReader.hasNextLine()){
                String url = myReader.nextLine();
                listUrl.add( url);
            }
        }catch(Exception e){
            System.out.println("An error occurred");
            e.printStackTrace();
            return null;
        }
        return listUrl;
    }
}
