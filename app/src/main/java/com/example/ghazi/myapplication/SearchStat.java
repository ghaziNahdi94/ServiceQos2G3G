package com.example.ghazi.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ghazi on 17/09/2016.
 */
public class SearchStat {

    private List<View> viewCallsFiltre = new ArrayList<View>();


    private Date d1 = null;
    private Date d2 = null;

    private int allCalls = 0;
    private int droppedCalls = 0;

    public SearchStat(Context context,Date d1, Date d2) {
        this.d1 = d1;
        this.d2 = d2;


        String PATH = context.getFilesDir().getAbsolutePath();
        String PATH_CALLS = PATH + "/calls.txt";
        File callsFile = new File(PATH_CALLS);

        try {
            FileInputStream fis = new FileInputStream(PATH_CALLS);
            byte[] texteBytes = new byte[(int) callsFile.length()];

            fis.read(texteBytes);

            String texte = new String(texteBytes);



            String[] parts = texte.split("--------------------");



            for (int i = 1; i < parts.length; i++) {


                String[] lignes = parts[i].split("\n");




          Date date = construireDate(lignes[1].split("le ")[1].split("à")[0].trim());

               if(appartientDates(date)){

                   allCalls++;

                   if(lignes[1].startsWith("Drop"))
                       droppedCalls++;

               }


            }




            }catch(IOException e){
                e.printStackTrace();
            }

        }



    private Date construireDate(String date){

        Date d = new Date();
        d.setTime(0);
        d.setDate(Integer.parseInt(date.split("/")[0]));
        d.setMonth(Integer.parseInt(date.split("/")[1]));
        d.setYear(Integer.parseInt(date.split("/")[2]));

        return d;
    }




    private boolean appartientDates(Date date){



        if(d1.compareTo(d2) == 0){

            if(date.compareTo(d1) == 0) {
                return true;
            }else {
                return false;
            }

        }else{



            if((date.compareTo(d1) == 1 && date.compareTo(d2) == -1) || date.compareTo(d1) == 0 || date.compareTo(d2) == 0) {
                return true;
            } else {
                return false;
            }



        }



    }

    public int getAllCalls() {
        return allCalls;
    }

    public int getDroppedCalls() {
        return droppedCalls;
    }
}
