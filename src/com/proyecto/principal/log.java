package com.proyecto.principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Bernardo Bonilla on 20/8/14.
 */
public class log {
    public static void write(String mensaje){
        File log = new File(System.getProperty("user.dir")+"/log.txt");
        java.util.Date date= new java.util.Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        try{
            if(!log.exists()){
                log.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(timeStamp.toString()+"\t" + mensaje + "\n");
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
