package com.example.ghazi.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ghazi on 19/07/2016.
 */
public class AppelRefuserView extends LinearLayout {

    private TextView title1 = null;
    private TextView title2 = null;
    private TextView title4 = null;
    private TextView title5 = null;
    private TextView title6 = null;
    private TextView title7 = null;
    private TextView title8 = null;

    private TextView appel = null; //date
    private TextView numero = null;
    private TextView duree = null;
    private TextView signalCopure = null;

    private TextView rssi = null;
    private TextView ecio = null;
    private TextView bitError = null;

    private TextView drop = null;

    public AppelRefuserView(Context context) {
        super(context);

        title1 = new TextView(context); title1.setTextColor(Color.rgb(255,0,0));
        title2 = new TextView(context); title2.setTextColor(Color.rgb(255,0,0));
        title4 = new TextView(context); title4.setTextColor(Color.rgb(255,0,0));
        title5 = new TextView(context); title5.setTextColor(Color.rgb(255,0,0));
        title6 = new TextView(context); title6.setTextColor(Color.rgb(255,0,0));
        title7 = new TextView(context); title7.setTextColor(Color.rgb(255,0,0));
        title8 = new TextView(context); title8.setTextColor(Color.rgb(255,0,0));


        title1.setText("Appel réçue(Rejeter) le ");  appel = new TextView(context);
        title2.setText("Numéro : ");   numero = new TextView(context);
        title4.setText("Durée d'appel : ");   duree = new TextView(context);
        title5.setText("Signal au copure : ");   signalCopure = new TextView(context);
        title6.setText("GSM RSSI : "); rssi = new TextView(context);
        title7.setText("GSM ECIO : "); ecio = new TextView(context);
        title8.setText("GSM Bit Error Rate : "); bitError = new TextView(context);


        drop = new TextView(context);drop.setTextColor(Color.BLACK);
        drop.setTypeface(null, Typeface.BOLD);




        this.setOrientation(VERTICAL);







        LinearLayout l1 = new LinearLayout(context);
        l1.setOrientation(HORIZONTAL);
        l1.addView(title1); l1.addView(appel);


        LinearLayout l2 = new LinearLayout(context);
        l2.setOrientation(HORIZONTAL);
        l2.addView(title2); l2.addView(numero);





        LinearLayout l4 = new LinearLayout(context);
        l4.setOrientation(HORIZONTAL);
        l4.addView(title4); l4.addView(duree);



        LinearLayout l5 = new LinearLayout(context);
        l5.setOrientation(HORIZONTAL);
        l5.addView(title5); l5.addView(signalCopure);


        LinearLayout l6 = new LinearLayout(context);
        l6.setOrientation(HORIZONTAL);
        l6.addView(title6); l6.addView(rssi);


        LinearLayout l7 = new LinearLayout(context);
        l7.setOrientation(HORIZONTAL);
        l7.addView(title7); l7.addView(ecio);


        LinearLayout l8 = new LinearLayout(context);
        l8.setOrientation(HORIZONTAL);
        l8.addView(title8); l8.addView(bitError);





        this.addView(drop);
        this.addView(l1);
        this.addView(l2);
        this.addView(l4);
        this.addView(l5);
        this.addView(l6);
        this.addView(l7);
        this.addView(l8);






        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15,10,0,0);
        l1.setLayoutParams(layoutParams1);
        l2.setLayoutParams(layoutParams1);
        l4.setLayoutParams(layoutParams1);

        l5.setLayoutParams(layoutParams1);
        l6.setLayoutParams(layoutParams1);
        l7.setLayoutParams(layoutParams1);
        drop.setLayoutParams(layoutParams1);
        drop.setGravity(Gravity.CENTER);


        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15,10,0,10);
        l8.setLayoutParams(layoutParams2);


    }







//Setters
    public void setAppel(String app) {
        appel.setText(app);
    }

    public void setNumero(String num) {
        numero.setText(num);
    }

    public void setDuree(String dur) {
        duree.setText(dur);
    }

    public void setSignalCopure(String signal) {
        signalCopure.setText(signal);
    }

    public void setRssi(String rss) {
        this.rssi.setText(rss);
    }

    public void setEcio(String ec) {
        this.ecio.setText(ec);
    }

    public void setBitError(String  bit) {
        this.bitError.setText(bit);
    }

    public void setDrop(String drop) {
        this.drop.setText(drop);
    }



    //Getters


    public String getAppel() {
        return appel.getText().toString();
    }

    public String getDuree() {
        return duree.getText().toString();
    }
}
