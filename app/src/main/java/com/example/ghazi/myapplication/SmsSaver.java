package com.example.ghazi.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ghazi on 15/07/2016.
 */
public class SmsSaver {

private Context context = null;
    private String result = null;
    private static int nbrSmsSaver = 0;


    public SmsSaver(Context context,String result) {
        this.result = result;
        this.context = context;
        nbrSmsSaver++;


       }



    public void saveSms(){

    if(nbrSmsSaver == 1) {
       saveTraceInFile(result);
    }else if(nbrSmsSaver == 3){

        nbrSmsSaver = 0;

    }

    }





    private void saveTraceInFile(String result){

        Setting setting = new Setting(context);
        Info info = new Info(context);

        if(setting.getApplicationActive()){

        final String PATH = context.getFilesDir().getAbsolutePath();
        final String PATH_SMS = PATH+"/sms.txt";
        final String separateur = "--------------------\n";
        final byte[] separateurBytes = separateur.getBytes();
        final String finSep = "********************\n";
        final byte[] finSepBytes = finSep.getBytes();

        File file = new File(PATH_SMS);


        //verifier que la trace est nouvelle
        boolean existe = false;
        try {
            FileInputStream fis = new FileInputStream(PATH_SMS);

            byte[] texteBytes = new byte[(int) file.length()];
            fis.read(texteBytes);

            String texte = new String(texteBytes);

            if(!texte.equals("")) {

                String[] parts = texte.split("--------------------");


                for (int i = 1; i < parts.length; i++) {


                    String[] lignes = parts[i].split("\n");


                    if (lignes[2].equals(result.split("\n")[1])) {

                        existe = true;
                        break;

                    }


                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




//save if not exist

if(!existe){


    info.setNbrSMS(info.getNbrSMS()+1);


        try {

            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath(),true);
            fos.write(separateurBytes);

            fos.write(result.getBytes());

            fos.write(finSepBytes);

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }}}



}
