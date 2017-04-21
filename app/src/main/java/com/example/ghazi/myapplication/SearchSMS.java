package com.example.ghazi.myapplication;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ghazi on 24/07/2016.
 */
public class SearchSMS {


    private List<View> viewSMSFiltre = new ArrayList<View>();


    private Date d1 = null;
    private Date d2 = null;




    public SearchSMS(LinearLayout layout, List<View> listViewCalls, Date d1, Date d2) {

        this.d1 = d1;
        this.d2 = d2;
        //Search by date

        for (View v : listViewCalls) {


        SMSView smsView = (SMSView) v;
        String dateString = smsView.getRep2().split("à")[0].trim();
            Date date = construireDate(dateString);

            if(appartientDates(date))
                viewSMSFiltre.add(smsView);


        }



        layout.removeAllViews();

        for(View view : viewSMSFiltre){

            layout.addView(view);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMargins(10,10,10,10);
            view.setLayoutParams(layoutParams);


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




}
