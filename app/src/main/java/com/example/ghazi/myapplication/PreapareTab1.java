package com.example.ghazi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 20/07/2016.
 */
public class PreapareTab1 {

    private List<View> viewsCall = new ArrayList<View>();

    MainActivity mainActivity = null;
    Context context = null;



    public PreapareTab1(MainActivity activity,Context context) {
        this.mainActivity = activity;
        this.context = context;
    }




    public void putViews(LinearLayout tab1,String PATH_CALLS, File callsFile){


        tab1.removeAllViews();

        try {


            FileInputStream fis = new FileInputStream(PATH_CALLS);
            byte[] texteBytes = new byte[(int) callsFile.length()];
            fis.read(texteBytes);

            String texte = new String(texteBytes);


            if(!texte.contains("\nDrop") && !texte.startsWith("Drop")){


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,20,0,0);
                layoutParams.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setText("Pas de drop call");
                textView.setTextSize(20);textView.setTextColor(Color.BLUE);
                textView.setLayoutParams(layoutParams);


                tab1.addView(textView);



            }


            String[] parts = texte.split("--------------------");



            for(int i=1;i<parts.length;i++){   //tjrs debut 1


                String[] lignes = parts[i].split("\n");

            if(lignes[1].startsWith("Drop")) {

                if (lignes[3].endsWith("2G")) {

                    Appel2g appel2g = new Appel2g(context);

                    appel2g.setDrop(lignes[1].split("le ")[1]);
                    appel2g.setNumero(lignes[2].split(":")[1]);
                    appel2g.setTechnology(lignes[3].split(":")[1]);
                    appel2g.setRxLev(lignes[4].split(":")[1]);
                    appel2g.setRxQual(lignes[5].split(":")[1]);
                    appel2g.setLongitude(lignes[6].split(":")[1]);
                    appel2g.setLatitude(lignes[7].split(":")[1]);

                    viewsCall.add(appel2g);

                } else if (lignes[3].endsWith("3G")) {


                    Appel3g appel3g = new Appel3g(context);

                    appel3g.setDrop(lignes[1].split("le ")[1]);
                    appel3g.setNumero(lignes[2].split(":")[1]);
                    appel3g.setTechnology(lignes[3].split(":")[1]);
                    appel3g.setRscp(lignes[5].split(":")[1]);
                    appel3g.setRssi(lignes[4].split(":")[1]);
                    appel3g.setEcio(lignes[6].split(":")[1]);
                    appel3g.setLongitude(lignes[7].split(":")[1]);
                    appel3g.setLatitude(lignes[8].split(":")[1]);


                    viewsCall.add(appel3g);


                }

            }

            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        boolean b = true;
        for (View view : viewsCall) {

            if (b) {
                view.setBackgroundColor(Color.rgb(255, 240, 240));
                b = false;
            }else{
                b = true;
            }

            tab1.addView(view);

        }

        mainActivity.setViewCalls(viewsCall);


    }







}
