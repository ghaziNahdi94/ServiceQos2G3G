package com.example.ghazi.myapplication;



import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity{

  private TabHost tabHost = null;
    private Menu menu = null;
  private MenuInflater menuInflater = null;

    private LinearLayout tab1 = null;
    private LinearLayout tab2 = null;




    private EditText hostFTP = null;
    private EditText loginFTP = null;
    private EditText passwordFTP = null;
    private TextView nameFile = null;
    private Button choixFile = null;
    private TextView statut = null;
    private Button envoyerFile = null;
    private Button annulerFile = null;
    private File file = null;
    private String fileName = null;
    private String filePath = null;
    private ProgressDialog progressDialog = null;
    private SendFileFTP sendFileFTP = null;


    private EditText hostFTPdown = null;
    private EditText loginFTPdown = null;
    private EditText passwordFTPdown = null;
    private EditText pathFTPdown = null;
    private TextView statutDown = null;
    private Button telecharger = null;
    private Button annuler = null;
    private ProgressDialog progressDialogDown = null;
    private DownloadFileFTP downloadFileFTP = null;



    private TextView info1 = null;
    private TextView info2 = null;
    private TextView info3 = null;
    private Button btn_stat = null;
    private Button btn_tempsReel = null;




     private String PATH = "";
    private String PATH_INFO = "";
    private File callsFile = null;
    private String PATH_CALLS = "" ;
    private String PATH_SMS= "";
    private File smsFile = null;




//Handlers  Upload
    Handler cnxEtablie = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            statut.setText("La connexion est établie");
        }
    };

    Handler cnxEchoue = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            statut.setText("La connexion est échoué\n Assurer que vous étes connécté\nVerifier les données");
        }
    };

    Handler progressShow = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressDialog.setMax((int) file.length());
            progressDialog.setProgress(0);
            progressDialog.show();
        }
    };

    Handler progress = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressDialog.setProgress(sendFileFTP.getAvance());


        }
    };


    Handler envReuissit = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            statut.setText("L'envoie du fichier est réuissit\n"+file.length()+" octets envoyé dans "+sendFileFTP.getDuree()+" secondes\n");
            statut.setText(statut.getText().toString()+"Débit UP : "+file.length()/sendFileFTP.getDuree()+" octets/s");
        }
    };

    Handler envEchoue = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statut.setText(statut.getText()+"\nL'envoie du fichier est échoué\n");
            statut.setText("Veuillez vérifier les données");
        }
    };

//Handlers download

private Handler cnxEtablieDown = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        statutDown.setText("La connexion est établie");
    }
};

    private Handler cnxEchoueDown = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statutDown.setText("La connexion est échoué\n Assurer que vous étes connécté\n" +
                    "Verifier les données");
        }
    };

    private Handler progressShowDown = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressDialogDown.setMax(downloadFileFTP.getTaille());
            progressDialogDown.setProgress(0);
            progressDialogDown.show();
        }
    };


    private Handler progressDown = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

           progressDialogDown.setProgress(downloadFileFTP.getAvance());
        }
    };

    private Handler downReuissit = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressDialogDown.dismiss();
           statutDown.setText("Le téléchargement du fichier est réuissit\n");
            statutDown.setText(statutDown.getText().toString()+downloadFileFTP.getTaille()+" octets Téléchargé en "+downloadFileFTP.getDuree()+" secondes\n");
            statutDown.setText(statutDown.getText().toString()+"Débit Down : "+downloadFileFTP.getTaille()/downloadFileFTP.getDuree()+" octets/s");
        }
    };

    private Handler downEchoue = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialogDown.dismiss();
            statutDown.setText(statutDown.getText()+"\nLe téléchargement du fichier est échoué\n");
            statutDown.setText(statutDown.getText().toString()+"Veuillez vérifier les données");

        }
    };


    private Handler updateTab1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(tab1 != null && PATH_CALLS != null && callsFile != null) {
                PreapareTab1 preapareTab1 = new PreapareTab1(MainActivity.this, MainActivity.this);
                preapareTab1.putViews(tab1, PATH_CALLS, callsFile);
                tab1.invalidate();
            }
        }
    };

    private Handler updateTab2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(PATH_SMS != null && smsFile != null && tab2 != null){
            PrepareTab2 prepareTab2 = new PrepareTab2(MainActivity.this,MainActivity.this);
            prepareTab2.putViews(PATH_SMS,smsFile,tab2);
                tab2.invalidate();

        }}
    };


    private Handler updateInfo1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(info != null && PATH_INFO != null){
            if(info.getApplicationActive()) {
                info1.setText("Activé");
                info1.setTextColor(Color.GREEN);
            } else{
                info1.setText("Désactivé");
                info1.setTextColor(Color.RED);
            }
        }}
    };

    private Handler updateInfo2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(info != null && PATH_INFO != null) {

                int nbrAppel =  info.getNbrAppel();
                info2.setText(info.getNbrAppelInterrompu() + "  Dropped calls / " +nbrAppel + "  Calls");

                if(nbrAppel > 0)
                    btn_stat.setVisibility(View.VISIBLE);

            }

        }
    };

    private Handler updateInfo3 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(info != null && PATH_INFO != null)
            info3.setText(info.getNbrSMS()+"");
        }
    };








        private Handler accueil = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                princ.removeView(acc);
            }
        };


    //Filtrage-------------------------------------

       private AlertDialog.Builder alert = null;
    private EditText dateSearch1 = null;
    private EditText dateSearch2 = null;
    private EditText dureeSearch = null;

        private List<View> viewCalls = new ArrayList<View>();
    private List<View> viewSMS = new ArrayList<View>();






//Setting
    private Setting setting = null;
    private boolean captureTrace = true;



    //save TabHost state
    private int currentTab = 0;
    private String currentStatutUp = "";
    private String currentStatutDown = "";
    private String currentNameFile = "";
    private boolean currentEtatEnv = false;
    private String currentPath = "";


    //Alarm for Email
    AlarmManager alarmManager = null;

    //Info.txt
    Info info = null;


    //Image Accueil
    private ImageView acc = null;
    private LinearLayout princ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,0,0)));


        acc = (ImageView) findViewById(R.id.acc);
        princ = (LinearLayout) findViewById(R.id.layoutPrinc);



        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);


        hostFTP = (EditText) findViewById(R.id.hostFTP);
        loginFTP = (EditText) findViewById(R.id.loginFTP);
        passwordFTP = (EditText) findViewById(R.id.passwordFTP);
        nameFile = (TextView) findViewById(R.id.nameFile);
        choixFile = (Button) findViewById(R.id.choixFile);
        statut = (TextView) findViewById(R.id.statut);
        envoyerFile = (Button) findViewById(R.id.env);
        annulerFile = (Button) findViewById(R.id.annulerFile);

        hostFTPdown = (EditText) findViewById(R.id.hostFTPdown);
        loginFTPdown = (EditText) findViewById(R.id.loginFTPdown);
        passwordFTPdown = (EditText) findViewById(R.id.passwordFTPdown);
        pathFTPdown = (EditText) findViewById(R.id.pathFTPdown);
        statutDown = (TextView) findViewById(R.id.statutDown);
        telecharger = (Button) findViewById(R.id.download);
        annuler = (Button) findViewById(R.id.annulerFileDown);





        tabHost = (TabHost) findViewById(R.id.tabhost);

        tabHost.setup();


        TabHost.TabSpec tabSpec5 = tabHost.newTabSpec("Onglets 5");
        tabSpec5.setContent(R.id.tab5);
        tabSpec5.setIndicator("Info");
        tabHost.addTab(tabSpec5);


        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Onglets 1");
        tabSpec1.setContent(R.id.tab1);
        tabSpec1.setIndicator("drop call");
        tabHost.addTab(tabSpec1);



        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Onglets 2");
        tabSpec2.setContent(R.id.tab2);
        tabSpec2.setIndicator("sms");
        tabHost.addTab(tabSpec2);




        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Onglets 3");
        tabSpec3.setContent(R.id.tab3);
        tabSpec3.setIndicator("ftp up");
        tabHost.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("Onglets 4");
        tabSpec4.setContent(R.id.tab4);
        tabSpec4.setIndicator("ftp down");
        tabHost.addTab(tabSpec4);


        if(savedInstanceState != null) {


            currentTab = savedInstanceState.getInt("tab");
            currentStatutUp = savedInstanceState.getString("statut-up");
            currentStatutDown = savedInstanceState.getString("statut-down");
            currentNameFile = savedInstanceState.getString("name-file");
            currentEtatEnv = savedInstanceState.getBoolean("etat_env");
            currentPath = savedInstanceState.getString("path-file");

            tabHost.setCurrentTab(currentTab);
            statut.setText(currentStatutUp);
            statutDown.setText(currentStatutDown);
            nameFile.setText(currentNameFile);
            envoyerFile.setEnabled(currentEtatEnv);

            if(currentPath != null && file != null)
            file = new File(currentPath);

            princ.removeView(acc);


        }





        /******************************* Partie Téléphonique *************************************************/



        //les fichiers



         PATH = getFilesDir().getAbsolutePath();



          PATH_INFO = PATH+"/info.txt";



        File infoFile = new File(PATH_INFO);
        info = new Info(this);

        if(!infoFile.exists()) {
            try {
                infoFile.createNewFile();

                info.setDefaultInfo();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



         PATH_CALLS = PATH+"/calls.txt";


        File callsFile = new File(PATH_CALLS);



        if(!callsFile.exists()){



            try {
                callsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

         PATH_SMS= PATH+"/sms.txt";

         smsFile = new File(PATH_SMS);

        if(!smsFile.exists())
            try {
                smsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }





        //Settings
        setting = new Setting(this);
        File settingFile = new File(getFilesDir().getAbsolutePath()+"/setting.txt");
        if(!settingFile.exists()){
            try {
                settingFile.createNewFile();
                setting.setDefaultSetting();
                captureTrace = true;

                Intent intentAlarm = new Intent(MainActivity.this,SendMail.class);
                intentAlarm.putExtra("alarmCall",callsFile.getAbsolutePath());
                PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,0,intentAlarm,0);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,AlarmManager.INTERVAL_HALF_HOUR,AlarmManager.INTERVAL_HALF_HOUR,pendingIntent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        PreapareTab1 preapareTab1 = new PreapareTab1(this,this);
        preapareTab1.putViews(tab1,PATH_CALLS,callsFile);



        PrepareTab2 prepareTab2 = new PrepareTab2(this,this);
        prepareTab2.putViews(PATH_SMS,smsFile,tab2);


        info1 = (TextView) findViewById(R.id.info1);
        info2 = (TextView) findViewById(R.id.info2);
        info3 = (TextView) findViewById(R.id.info3);
        btn_stat = (Button) findViewById(R.id.stat_btn);
        btn_tempsReel = (Button) findViewById(R.id.temps_réel_btn);

        if(info.getApplicationActive())
        info1.setText("Activé");
        else
        info1.setText("Désactivé");



        info2.setText(info.getNbrAppelInterrompu()+"  Dropped calls /  "+info.getNbrAppel()+"  Calls");
        info3.setText(info.getNbrSMS()+"");

        btn_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StatistiqueActivity.class);
                startActivity(intent);
            }
        });
        btn_tempsReel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TempsReelActivity.class);
                startActivity(intent);
            }
        });



        // String telId = telephonyManager.getDeviceId();
        //String numTel = telephonyManager.getLine1Number();








        /*************************************** Partie FTP test *********************************************************/


        //--------------------- FTP upload ------------------//


        annulerFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hostFTP.setText("");
                    envoyerFile.setEnabled(false);
                    statut.setText("");
                    nameFile.setText("");
                       loginFTP.setText("");
                     passwordFTP.setText("");
                }
            });


        choixFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });



        envoyerFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filePath = file.getAbsolutePath();
                String host = hostFTP.getText().toString();
                String login = loginFTP.getText().toString();
                String password = passwordFTP.getText().toString();


                if(!host.equals("") && !login.equals("") && !password.equals("")) {
                   sendFileFTP =  new SendFileFTP(cnxEtablie,cnxEchoue,progressShow,progress,envReuissit,envEchoue);
                    sendFileFTP.execute(filePath, host, login, password);
                   progressDialog  = new ProgressDialog(MainActivity.this); progressDialog.setCancelable(false);
                    progressDialog.setTitle("Envoie du fichier");
                    progressDialog.setMessage("En cours....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                }else {
                    Toast.makeText(MainActivity.this, "Verifier les champs de données", Toast.LENGTH_LONG).show();
                }


            }
        });


                    //--------------------- FTP download ------------------//



                annuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            hostFTPdown.setText("");
                            loginFTPdown.setText("");
                            passwordFTPdown.setText("");
                            statutDown.setText("");
                            pathFTPdown.setText("");

                    }
                });


        passwordFTPdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    if(!passwordFTPdown.getText().toString().equals(""))
                        telecharger.setEnabled(true);


                }
            }
        });



        telecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String host = hostFTPdown.getText().toString();
                String login = loginFTPdown.getText().toString();
                String password = passwordFTPdown.getText().toString();
                String path = pathFTPdown.getText().toString();

                if(!host.equals("") && !login.equals("") && !password.equals("") && !path.equals("")){

                  downloadFileFTP  = new DownloadFileFTP(MainActivity.this,cnxEtablieDown,cnxEchoueDown,progressShowDown,progressDown,downReuissit,downEchoue);

                    downloadFileFTP.execute("/sdcard/Download",host,login,password,path);

                    progressDialogDown  = new ProgressDialog(MainActivity.this); progressDialogDown.setCancelable(false);
                    progressDialogDown.setTitle("Téléchargement du fichier");
                    progressDialogDown.setMessage("En cours....");
                    progressDialogDown.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                }else{

                    Toast.makeText(MainActivity.this, "Verifier les champs de données", Toast.LENGTH_LONG).show();
                }

            }
        });








tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
    @Override
    public void onTabChanged(String tabId) {

        menu.clear();

        switch (tabHost.getCurrentTab()){

            case 0 : menuInflater.inflate(R.menu.info_menu,menu);
                if(info.getNbrAppel() == 0) {
                    btn_stat.setVisibility(View.INVISIBLE);
                    menu.removeItem(R.id.statistique);
                }else {
                    btn_stat.setVisibility(View.VISIBLE);
                    menu.clear();
                    menuInflater.inflate(R.menu.info_menu,menu);
                }
                break;

            case 1 : menuInflater.inflate(R.menu.menu_calls,menu);
                break;


            case 2 :  menuInflater.inflate(R.menu.menu_sms,menu);
                break;

            case 3 : menuInflater.inflate(R.menu.menu_ftp_up,menu);
                break;

            case 4 : menuInflater.inflate(R.menu.menu_ftp_up,menu);
                break;

        }

    }
});





                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        accueil.sendEmptyMessage(0);


                    }
                }).start();




    }




    @Override
    protected void onResume() {
        super.onResume();

        captureTrace = setting.getApplicationActive();
        Intent intentService = new Intent(MainActivity.this,CaptureCallsSMSService.class);
        intentService.putExtra("capture",captureTrace);
        startService(intentService);


        final String PATH = getFilesDir().getAbsolutePath();
        final String PATH_CALLS = PATH+"/calls.txt";
        callsFile = new File(PATH_CALLS);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){



                        updateTab1.sendEmptyMessage(0);
                        updateTab2.sendEmptyMessage(0);

                        updateInfo1.sendEmptyMessage(0);
                        updateInfo2.sendEmptyMessage(0);
                        updateInfo3.sendEmptyMessage(0);




                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("tab",tabHost.getCurrentTab());
        outState.putString("statut-up",statut.getText().toString());
        outState.putString("statut-down",statutDown.getText().toString());
        outState.putString("name-file",nameFile.getText().toString());
        outState.putBoolean("etat_env",envoyerFile.isEnabled());
        if(file != null)
        outState.putString("path-file",file.getAbsolutePath());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1 && data != null){
            fileName = data.getStringExtra("file1");
            filePath = data.getStringExtra("file2");


            statut.setText("");
            envoyerFile.setEnabled(false);

            file = new File(filePath);
            nameFile.setText(fileName);
            envoyerFile.setEnabled(true);


        }else if(resultCode == 2){

            captureTrace = setting.getApplicationActive();

            Intent intentService = new Intent(MainActivity.this,CaptureCallsSMSService.class);
            intentService.putExtra("capture",captureTrace);
            startService(intentService);

            info = new Info(this);
            if(info.getApplicationActive())
                info1.setText("Activé");
            else
                info1.setText("Désactivé");


            info2.setText(info.getNbrAppelInterrompu()+" Dropped calls /  "+info.getNbrAppel()+" Calls");
            info3.setText(info.getNbrSMS()+"");

        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

        this.menu = menu;
        menuInflater = getMenuInflater();



            if (currentTab == 0) {

                if(info.getNbrAppel() == 0) {
                    btn_stat.setVisibility(View.INVISIBLE);
                    menuInflater.inflate(R.menu.info_menu, menu);
                    menu.removeItem(R.id.statistique);
                }else {
                    btn_stat.setVisibility(View.VISIBLE);
                    menu.clear();
                    menuInflater.inflate(R.menu.info_menu,menu);
                }


            } else if (currentTab == 1) {
                menuInflater.inflate(R.menu.menu_calls, menu);
            } else if (currentTab == 2 ) {
                menuInflater.inflate(R.menu.menu_sms, menu);
            }else if(currentTab == 3 || currentTab == 4){
                menuInflater.inflate(R.menu.menu_ftp_up,menu);
            }



return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);


        switch (item.getItemId()){


            case R.id.temps_réel :

                Intent intent_TempsReel = new Intent(MainActivity.this,TempsReelActivity.class);
                startActivity(intent_TempsReel);

                break;

            case R.id.statistique :

                Intent intent_stat = new Intent(MainActivity.this,StatistiqueActivity.class);
                startActivity(intent_stat);

                break;



            case R.id.parDate :

                alert = new AlertDialog.Builder(this);
                alert.setTitle("Chercher par date");

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.dilaog_filter_lyout,null);
                alert.setView(view);







                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Dialog View for Search
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        dateSearch1 = (EditText) alertDialog.findViewById(R.id.dateSearch1); String date1 = dateSearch1.getText().toString();
                        dateSearch2 = (EditText) alertDialog.findViewById(R.id.dateSearch2); String date2 = dateSearch2.getText().toString();


                        if(verifyDate(date1) && verifyDate(date2)) {

                            Date d1 = new Date();d1.setTime(0);
                            d1.setDate(Integer.parseInt(date1.split("/")[0]));
                            d1.setMonth(Integer.parseInt(date1.split("/")[1]));
                            d1.setYear(Integer.parseInt(date1.split("/")[2]));

                            Date d2 = new Date();d2.setTime(0);
                            d2.setDate(Integer.parseInt(date2.split("/")[0]));
                            d2.setMonth(Integer.parseInt(date2.split("/")[1]));
                            d2.setYear(Integer.parseInt(date2.split("/")[2]));



                            if(d1.compareTo(d2) == -1 || d1.compareTo(d2) == 2 || d1.compareTo(d2) == 0){


                                if(tabHost.getCurrentTab() == 0)
                                 new  SearchCalls(tab1,viewCalls,d1,d2);
                                else if(tabHost.getCurrentTab() == 1)
                                    new SearchSMS(tab2,viewSMS,d1,d2);


                            }else{

                                Toast.makeText(MainActivity.this,"Vérifiez les dates svp",Toast.LENGTH_LONG).show();


                            }




                        }else{

                            Toast.makeText(MainActivity.this,"Vérifiez les dates svp",Toast.LENGTH_LONG).show();

                        }




                    }
                });
                alert.show();
                break;



            case R.id.setting :

                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivityForResult(intent,2);

                break;

            case R.id.tous :

                List<View> lista = null;
                LinearLayout layouta = null;
                if(tabHost.getCurrentTab() == 0) {
                    lista = viewCalls; layouta = tab1;
                  }else{
                    lista = viewSMS; layouta = tab2;
                }

                layouta.removeAllViews();
                for (View v : lista) {
                    layouta.addView(v);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
                    layoutParams.setMargins(10, 10, 10, 10);
                    v.setLayoutParams(layoutParams);
                }



            break;




        }

        return true;
    }






    private boolean verifyDate(String date){

        boolean b = true;

        int count = 0;

        for(int i=0;i<date.length();i++){
            if(date.charAt(i) == '/')
                count++;
        }


        if(count != 2) {
            b = false;

        }else {


            String jj = date.split("/")[0];

            if (jj.length() < 1 || jj.length() > 2)
                b = false;

            if(b){
            for (int i = 0; i < jj.length(); i++) {
                if (!Character.isDigit(jj.charAt(i))) {
                    b = false;
                    break;
                }
            }}

            if (b) {
                if (Integer.parseInt(jj) < 0 || Integer.parseInt(jj) > 31) b = false;
            }



            if(b) {
                String mm = date.split("/")[1];

                if (mm.length() < 1 || mm.length() > 2)
                    b = false;

                if (b) {
                    for (int i = 0; i < mm.length(); i++) {
                        if (!Character.isDigit(mm.charAt(i))) {
                            b = false;
                            break;
                        }
                    }
                }

                if (b) {
                    if (Integer.parseInt(mm) < 0 || Integer.parseInt(mm) > 12) b = false;
                }

            }





if(b) {
    String aaaa = date.split("/")[2];

    if (aaaa.length() != 4)
        b = false;

    if (b) {
        for (int i = 0; i < aaaa.length(); i++) {
            if (!Character.isDigit(aaaa.charAt(i))) {
                b = false;
                break;
            }
        }
    }
}




        }


        return b;
    }




    private void pickFile(){

        Intent intent = new Intent(MainActivity.this,ListFilesActivity.class);
        startActivityForResult(intent,1);

    }










//Setters


    public void setViewCalls(List<View> viewCalls) {
        this.viewCalls = viewCalls;
    }

    public void setViewSMS(List<View> viewSMS) {
        this.viewSMS = viewSMS;
    }
}
