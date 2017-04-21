package com.example.ghazi.myapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 25/07/2016.
 */
public class SettingActivity extends ActionBarActivity{

    private ListView listeSetting = null;
    private Setting setting = null;
    private Info info = null;
    private CheckBox checkBox = null;


    private ListView listeChoix = null;
    private ListView listeChoixDur = null;


    private int choice = 1;
    private int choiceDur = 1;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,0,0)));

        listeSetting = (ListView) findViewById(R.id.listSetting);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final List<String> list = new ArrayList<String >();

        list.add("Récupérer les traces");
        list.add("Envoyer l’Email avec");
        list.add("Envoyer l’Email chaque");

        SettingAdapter settingAdapter = new SettingAdapter(this,list);


        listeSetting.setAdapter(settingAdapter);


        setting = new Setting(getFilesDir().getAbsolutePath()+"/setting.txt");
        info = new Info(getFilesDir().getAbsolutePath()+"/info.txt");

        listeSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                if(position == 0){

                    checkBox = (CheckBox) view.findViewById(R.id.activationSetting);


                    if(checkBox.isChecked()){

                        checkBox.setChecked(false);
                        setting.setApplicationActive(false);
                        info.setApplicationActive(false);






                    }else{

                                checkBox.setChecked(true);
                                setting.setApplicationActive(true);
                        info.setApplicationActive(true);


                    }

                }else if(position == 1){


                    if(setting.getEmail().equals("rien")){

                        Toast.makeText(SettingActivity.this,"Vous n'avez pas entrer un email",Toast.LENGTH_SHORT).show();
                        entrerLemail();

                    }else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        listeChoix = new ListView(SettingActivity.this);


                        ArrayAdapter arrayAdapter = new ArrayAdapter(SettingActivity.this, android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("Toutes type de connexion");
                        arrayAdapter.add("Wifi");
                        arrayAdapter.add("Internet mobile");

                        listeChoix.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listeChoix.setAdapter(arrayAdapter);


                        //paramétres

                        choice = setting.getChoiceEnvoie();

                        listeChoix.setItemChecked(choice - 1, true);

                        builder.setTitle("Envoyer l’Email avec");
                        builder.setView(listeChoix);
                        builder.setNegativeButton("Annulez", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // traitement Setting


                                setting.setChoiceEnv(choice);


                                dialog.dismiss();
                            }
                        });

                        builder.show();


                        listeChoix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                choice = position + 1;


                            }
                        });


                    }








                }else if(position == 2){


                    if(setting.getEmail().equals("rien")){

                        Toast.makeText(SettingActivity.this,"Vous n'avez pas entrer un email",Toast.LENGTH_SHORT).show();
                        entrerLemail();

                    }else {


                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        listeChoixDur = new ListView(SettingActivity.this);


                        ArrayAdapter arrayAdapter = new ArrayAdapter(SettingActivity.this, android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("30 minutes");
                        arrayAdapter.add("1 heure");
                        arrayAdapter.add("2 heures");

                        listeChoixDur.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listeChoixDur.setAdapter(arrayAdapter);


                        //paramétres
                        choiceDur = setting.getChoiceDuree();

                        listeChoixDur.setItemChecked(choiceDur - 1, true);

                        builder.setTitle("Envoyer l’Email chaque");
                        builder.setView(listeChoixDur);
                        builder.setNegativeButton("Annulez", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // traitement Setting

                                setting.setChoiceDuree(choiceDur);

                                long interval = AlarmManager.INTERVAL_HALF_HOUR;

                                switch (choiceDur) {

                                    case 1:
                                        interval = AlarmManager.INTERVAL_HALF_HOUR;
                                        break;

                                    case 2:
                                        interval = AlarmManager.INTERVAL_HOUR;
                                        break;

                                    case 3:
                                        interval = AlarmManager.INTERVAL_HOUR * 2;
                                        break;


                                    default:
                                        interval = AlarmManager.INTERVAL_HALF_HOUR;
                                        break;

                                }


                                Intent intentAlarm = new Intent(SettingActivity.this, SendMail.class);
                                intentAlarm.putExtra("alarmCall", SettingActivity.this.getFilesDir().getAbsolutePath() + "/calls.txt");
                                PendingIntent pendingIntent = PendingIntent.getService(SettingActivity.this, 0, intentAlarm, 0);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                                alarmManager.cancel(pendingIntent);
                                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, interval, interval, pendingIntent);


                                dialog.dismiss();
                            }
                        });

                        builder.show();


                        listeChoixDur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                choiceDur = position + 1;

                            }
                        });


                    }



                }


            }
        });



    }



    private void entrerLemail(){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingActivity.this);
        alertDialog.setTitle("Taper votre Email");
        final EditText editText = new EditText(SettingActivity.this);

        //Margin Dialog
        LinearLayout linearLayout = new LinearLayout(SettingActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30,10,30,5);
        editText.setLayoutParams(layoutParams);

        linearLayout.addView(editText);



        alertDialog.setView(linearLayout);
        alertDialog.setPositiveButton("Validez", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String email = editText.getText().toString();
                if(!email.contains("@") || email.length()<=5 || !email.contains(".")) {
                    Toast.makeText(SettingActivity.this, "Email invalide !", Toast.LENGTH_SHORT).show();
                }else{

                    setting.setEmail(email);
                    dialog.dismiss();

                }


            }
        });
        alertDialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.KEYCODE_BACK == keyCode) {
        this.setResult(2);
            this.finish();

            return true;
        }else{
            return super.onKeyDown(keyCode,event);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home : this.setResult(2); this.finish();
           return true;

default:return super.onOptionsItemSelected(item);

        }

    }
}
