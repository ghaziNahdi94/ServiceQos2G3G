package com.example.ghazi.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ghazi on 20/07/2016.
 */
public class SMSView extends LinearLayout {

    private TextView title1 = null;
    private TextView title2 = null;
    private TextView title3 = null;

    private TextView rep1 = null;
    private TextView rep2 = null;
    private TextView rep3 = null;

    public SMSView(Context context) {
        super(context);

        title1 = new TextView(context); title1.setTextColor(Color.RED);
        title2 = new TextView(context); title2.setTextColor(Color.RED);
        title3 = new TextView(context); title3.setTextColor(Color.RED);


        rep1 = new TextView(context); rep1.setTextColor(Color.BLACK);
        rep2 = new TextView(context); rep2.setTextColor(Color.BLACK);
        rep3 = new TextView(context); rep3.setTextColor(Color.BLACK);



        this.setOrientation(VERTICAL);


        LinearLayout l1 = new LinearLayout(context);
        l1.setOrientation(HORIZONTAL);
        l1.addView(title1); l1.addView(rep1);

        LinearLayout l2 = new LinearLayout(context);
        l2.setOrientation(HORIZONTAL);
        l2.addView(title2); l2.addView(rep2);


        LinearLayout l3 = new LinearLayout(context);
        l3.setOrientation(HORIZONTAL);
        l3.addView(title3); l3.addView(rep3);



        this.addView(l1);
        this.addView(l2);
        this.addView(l3);





        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15,10,0,0);
        l1.setLayoutParams(layoutParams1);
        l2.setLayoutParams(layoutParams1);



        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) l1.getLayoutParams();
        layoutParams1.setMargins(15,10,0,10);
        l3.setLayoutParams(layoutParams2);

    }


    //Setters
    public void setRep1(String rep1) {
        this.rep1.setText(rep1);
    }

    public void setRep2(String rep2) {
        this.rep2.setText(rep2);
    }

    public void setRep3(String  rep3) {
        this.rep3.setText(rep3);
    }


    public void setTitle1(String title1) {
        this.title1.setText(title1);
    }

    public void setTitle2(String title2) {
        this.title2.setText(title2);
    }

    public void setTitle3(String title3) {
        this.title3.setText(title3);
    }


    //Getters


    public String getRep2() {
        return rep2.getText().toString();
    }
}
