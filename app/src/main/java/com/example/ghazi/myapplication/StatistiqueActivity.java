package com.example.ghazi.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ghazi on 11/09/2016.
 */
public class StatistiqueActivity extends ActionBarActivity {

    private LinearLayout layout = null;

    private MenuInflater menuInflater = null;

    private CategorySeries mSeries = null;
    private DefaultRenderer mRenderer = null;
    private GraphicalView statCercle = null;


    private int nbrAppel = 0;
    private int nbrAppelInteromp = 0;


    private EditText dateSearch1 = null;
    private EditText dateSearch2 = null;

    private TextView succes = null;
    private TextView dropped = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_activity);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout) findViewById(R.id.stat_layout);




        layout.removeAllViews();


        if(savedInstanceState == null) {


            Info info = new Info(this);
            nbrAppel = info.getNbrAppel();
            nbrAppelInteromp = info.getNbrAppelInterrompu();




        }else{

            nbrAppel = savedInstanceState.getInt("nbrAppel",-1);
            nbrAppelInteromp = savedInstanceState.getInt("nbrAppelInteromp",-1);

        }


        if (nbrAppel > 0) {

            createStatCerrcle(nbrAppel,nbrAppelInteromp);

        } else {

            pasDeStat();

        }



    }


    private void pasDeStat(){

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 20, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
        TextView textView = new TextView(StatistiqueActivity.this);
        textView.setText("Pas d'appel trouvé");
        textView.setTextSize(20);
        textView.setTextColor(Color.CYAN);
        textView.setLayoutParams(layoutParams);


        layout.addView(textView);

    }

    private void createStatCerrcle(int allCalls,int droppedCalls){

        mSeries = new CategorySeries("");
        mRenderer = new DefaultRenderer();

        int colors[] = {Color.GREEN, Color.RED};
        String nameValues[] = {"Success Call", "Drop Call"};
        double values[] = {allCalls - droppedCalls, droppedCalls};

        // mRenderer.setApplyBackgroundColor(true);
        //mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setLabelsTextSize(10);
        mRenderer.setLegendTextSize(20);
        mRenderer.setShowLabels(false);
        mRenderer.setShowTickMarks(true);
        mRenderer.setDisplayValues(true);


        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);


        for (int i = 0; i < colors.length; i++) {

            mSeries.add(nameValues[i] + " " + ((int) (values[i] * 100 / (values[0] + values[1]))) + "%", values[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(colors[(mSeries.getItemCount() - 1) % colors.length]);
            mRenderer.addSeriesRenderer(renderer);

        }


        statCercle = ChartFactory.getPieChartView(this, mSeries, mRenderer);


        succes = new TextView(this);
        dropped = new TextView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(0,10,0,0);

        succes.setLayoutParams(params); succes.setTextColor(Color.GREEN);
        dropped.setLayoutParams(params); dropped.setTextColor(Color.RED);

        succes.setText("Success Calls :  "+(nbrAppel-nbrAppelInteromp));
        dropped.setText("Dropped Calls :  "+nbrAppelInteromp);


        layout.addView(succes); layout.addView(dropped);

        if (statCercle != null)
            layout.addView(statCercle);
        else
            Log.e("null", "NULL");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        outState.putInt("nbrAppel",nbrAppel);
        outState.putInt("nbrAppelInteromp",nbrAppelInteromp);

        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == event.KEYCODE_BACK) {
            this.finish();
            return true;
        } else {

            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_stat, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.date :

            AlertDialog.Builder    alert = new AlertDialog.Builder(this);
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



                                SearchStat searchStat = new SearchStat(StatistiqueActivity.this,d1,d2);
                                nbrAppel = searchStat.getAllCalls();
                                nbrAppelInteromp = searchStat.getDroppedCalls();

                                layout.removeAllViews();

                                if(nbrAppel != 0) {

                                createStatCerrcle(nbrAppel,nbrAppelInteromp);

                                }else{

                                pasDeStat();

                                }





                            }else{

                                Toast.makeText(StatistiqueActivity.this,"Vérifiez les dates svp",Toast.LENGTH_LONG).show();


                            }




                        }else{

                            Toast.makeText(StatistiqueActivity.this,"Vérifiez les dates svp",Toast.LENGTH_LONG).show();

                        }




                    }
                });
                alert.show();








                break;


            case R.id.toutes :
                startActivity(getIntent());
                finish();

                break;


            case android.R.id.home:
                this.setResult(2);
                this.finish();
               break;


            default:
                return super.onOptionsItemSelected(item);

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


        }
