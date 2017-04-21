package com.example.ghazi.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 14/07/2016.
 */

public class SignalListener extends PhoneStateListener {

    private Context context = null;
    private BroadCatPhoneCall broadCatPhoneCall = null;
    private final int UNKNOW_CODE = 99;
    private final int MAX_SIGNAL_DBM_VALUE = 31;


    public SignalListener(Context context,BroadCatPhoneCall broadCatPhoneCall) {
        this.context = context;
        this.broadCatPhoneCall = broadCatPhoneCall;
    }



    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);

        List<Integer> list = null;
        int signalValue = -1; //signal value !!
        int rssi = -1;
        int ecio = -1;
        int rxQual = -1;
        int rxLev = 1;

        String networktype = getNetworkType(context);

            if(signalStrength.isGsm()){





                if(signalStrength.isGsm()) {


                    signalValue = (2*signalStrength.getGsmSignalStrength()) -113;


                    if (networktype.equals("2g")) {


                        list = new ArrayList<Integer>();


                        rxQual = signalStrength.getGsmBitErrorRate();
                        rxLev = signalValue;

                        list.add(rxQual);list.add(rxLev);

                    } else {


                        list = new ArrayList<Integer>();

                        ecio = signalStrength.getCdmaEcio();
                        rssi = signalStrength.getCdmaDbm();

                        list.add(rssi+(ecio/10));list.add(rssi); list.add(ecio/10);

                    }


                }






            }



        broadCatPhoneCall.setSignalValue(list,networktype,signalValue);




        /*
        if(broadCatPhoneCall.isStateHook()){

            broadCatPhoneCall.increaseNbrSignalValue();
            broadCatPhoneCall.increaseSignalValueTotalHook();

        }
*/


    }






    private String getNetworkType(Context context){

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();


        switch (networkType){

            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2g";

            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //http://goo.gl/bhtVT
                return "3g";


            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://goo.gl/9t7yrR
                return "4g";
            default:
                return "Not found";

        }


    }



}
