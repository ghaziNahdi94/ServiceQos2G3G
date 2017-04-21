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
public class Appel2g extends LinearLayout {

    private TextView title1 = null;
    private TextView title2 = null;
    private TextView title3 = null;
    private TextView title4 = null;
    private TextView title5 = null;
    private TextView title6 = null;
    private TextView title7 = null;


    private TextView drop = null;
    private TextView numero = null;
    private TextView technology = null;
    private TextView rxLev = null;
    private TextView rxQual = null;
    private TextView longitude = null;
    private TextView latitude = null;

    public Appel2g(Context context) {
        super(context);

        title1 = new TextView(context);
        title1.setTextColor(Color.rgb(255, 0, 0));
        title2 = new TextView(context);
        title2.setTextColor(Color.rgb(255, 0, 0));
        title3 = new TextView(context);
        title3.setTextColor(Color.rgb(255, 0, 0));
        title4 = new TextView(context);
        title4.setTextColor(Color.rgb(255, 0, 0));
        title5 = new TextView(context);
        title5.setTextColor(Color.rgb(255, 0, 0));
        title6 = new TextView(context);
        title6.setTextColor(Color.rgb(255, 0, 0));
        title7 = new TextView(context);
        title7.setTextColor(Color.rgb(255, 0, 0));







        title1.setText("Drop call le ");
        drop = new TextView(context);
        title2.setText("Numéro : ");
        numero = new TextView(context);
        title3.setText("Technologie : ");
        technology = new TextView(context);
        title4.setText("Rx Lev : ");
        rxLev = new TextView(context);
        title5.setText("Rx Qual : ");
        rxQual = new TextView(context);
        title6.setText("Longitude : ");
        longitude = new TextView(context);
        title7.setText("Latitude : ");
        latitude = new TextView(context);




        this.setOrientation(VERTICAL);







        LinearLayout l1 = new LinearLayout(context);
        l1.setOrientation(HORIZONTAL);
        l1.addView(title1);
        l1.addView(drop);


        LinearLayout l2 = new LinearLayout(context);
        l2.setOrientation(HORIZONTAL);
        l2.addView(title2);
        l2.addView(numero);

        LinearLayout l3 = new LinearLayout(context);
        l3.setOrientation(HORIZONTAL);
        l3.addView(title3);
        l3.addView(technology);

        LinearLayout l4 = new LinearLayout(context);
        l4.setOrientation(HORIZONTAL);
        l4.addView(title4);
        l4.addView(rxLev);


        LinearLayout l5 = new LinearLayout(context);
        l5.setOrientation(HORIZONTAL);
        l5.addView(title5);
        l5.addView(rxQual);


        LinearLayout l6 = new LinearLayout(context);
        l6.setOrientation(HORIZONTAL);
        l6.addView(title6);
        l6.addView(longitude);

        LinearLayout l7 = new LinearLayout(context);
        l7.setOrientation(HORIZONTAL);
        l7.addView(title7);
        l7.addView(latitude);




        this.addView(l1);
        this.addView(l2);
        this.addView(l3);
        this.addView(l4);
        this.addView(l5);
        this.addView(l6);
        this.addView(l7);






        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15, 10, 0, 0);
        l1.setLayoutParams(layoutParams1);
        l2.setLayoutParams(layoutParams1);
        l3.setLayoutParams(layoutParams1);
        l4.setLayoutParams(layoutParams1);
        l5.setLayoutParams(layoutParams1);
        l6.setLayoutParams(layoutParams1);


        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15, 10, 0, 10);
        l7.setLayoutParams(layoutParams2);





    }

//Setters
public void setDrop(String drop) {
    this.drop.setText(drop);
}

    public void setNumero(String numero) {
        this.numero.setText(numero);
    }

    public void setTechnology(String technology){this.technology.setText(technology);}

    public void setRxLev(String rxLev) {
        this.rxLev.setText(rxLev);
    }

    public void setRxQual(String rxQual) {
        this.rxQual.setText(rxQual);
    }

    public void setLongitude(String longitude) {
        this.longitude.setText(longitude);
    }

    public void setLatitude(String latitude) {
        this.latitude.setText(latitude);
    }


    //Getters
    public String getDrop(){return this.drop.getText().toString();}



}
