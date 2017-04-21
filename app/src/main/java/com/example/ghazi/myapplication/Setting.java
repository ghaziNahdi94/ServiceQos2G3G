package com.example.ghazi.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 25/07/2016.
 */
public class Setting {


        private final String PATH;



    public Setting(Context context){


        PATH = context.getFilesDir().getAbsolutePath()+"/setting.txt";
    }

    public Setting(String path){
        PATH = path;

    }




public void setDefaultSetting(){


    try {


        FileWriter fw = new FileWriter(PATH);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("Activer les traces : 1"); // 1 activé  | 0 désactivé
        bw.newLine();
        bw.write("Envoyer les données : 1"); // 1 Toutes type de connexion | Wifi | Internet mobile
        bw.newLine();
        bw.write("Durée d'envoie : 1"); // 1 (30 minutes) | 2 (1 heures)  | 3 (2 heures)
        bw.newLine();
        bw.write("Email : rien");


        bw.flush();
        bw.close();
        fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}


    public void setEmail(String email){

        List<String> lignes = new ArrayList<String>();

        try {


            FileReader fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);



            String ligne = "";

            while ((ligne = br.readLine()) != null) {

                lignes.add(ligne);

            }


            br.close();fr.close();

            File file = new File(PATH);
            file.delete();

            file.createNewFile();



            String ligne1  =  "Email : "+email;



            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(ligne1);

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}





    }



public String getEmail(){


    FileReader fr = null;
    String res = "";
    try {
        fr = new FileReader(PATH);
        BufferedReader br = new BufferedReader(fr);

        res = br.readLine();
        res = br.readLine();
        res = br.readLine();
        res = br.readLine();


        res = res.split(" : ")[1].trim();

        br.close();
        fr.close();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }




return res;


}




    public int getChoiceDuree(){

        FileReader fr = null;
        String res = "";
        try {
            fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);

            res = br.readLine();
            res = br.readLine();
            res = br.readLine();


            res = res.split(" : ")[1].trim();

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(res.equals("1"))
            return 1;
        else if(res.equals("2"))
            return 2;
        else return 3;


    }






    public void setChoiceDuree(int i) {

        List<String> lignes = new ArrayList<String>();

        try {


            FileReader fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);



            String ligne = "";

            while ((ligne = br.readLine()) != null) {

                lignes.add(ligne);

            }


            br.close();fr.close();

            File file = new File(PATH);
            file.delete();

            file.createNewFile();

            String ligne1 = "";


            switch (i){

                case 1 : ligne1 = "Durée d'envoie : 1";
                    break;

                case 2 : ligne1 = "Durée d'envoie : 2";
                    break;

                case 3 : ligne1 = "Durée d'envoie : 3";
                    break;

                default: ligne1 = "Durée d'envoie : 1";


            }




            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(3));

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}





    }






    public void setChoiceEnv(int i) {

        List<String> lignes = new ArrayList<String>();

        try {


            FileReader fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);



            String ligne = "";

            while ((ligne = br.readLine()) != null) {

                lignes.add(ligne);

            }


            br.close();fr.close();

            File file = new File(PATH);
            file.delete();

            file.createNewFile();

            String ligne1 = "";


            switch (i){

                case 1 : ligne1 = "Envoyer les données : 1";
                    break;

                case 2 : ligne1 = "Envoyer les données : 2";
                    break;

                case 3 : ligne1 = "Envoyer les données : 3";
                    break;

                default: ligne1 = "Envoyer les données : 1";


            }




            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(lignes.get(3));

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}





    }





    public int getChoiceEnvoie(){

        FileReader fr = null;
        String res = "";
        try {
            fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);

            res = br.readLine();
            res = br.readLine();


            res = res.split(" : ")[1].trim();

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(res.equals("1"))
         return 1;
        else if(res.equals("2"))
            return 2;
        else return 3;


    }











    public void setApplicationActive(boolean b) {

        List<String> lignes = new ArrayList<String>();

try {


    FileReader fr = new FileReader(PATH);
    BufferedReader br = new BufferedReader(fr);



    String ligne = "";

    while ((ligne = br.readLine()) != null) {

        lignes.add(ligne);

    }


br.close();fr.close();

File file = new File(PATH);
    file.delete();

    file.createNewFile();

    String ligne1 = "";
    if(b)
        ligne1  = "Récupérer les traces : 1";
    else
        ligne1 = "Récupérer les traces : 0";




    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(ligne1);
    bw.newLine();
    bw.write(lignes.get(1));
    bw.newLine();
    bw.write(lignes.get(2));
    bw.newLine();
    bw.write(lignes.get(3));

    bw.flush();
bw.close();
    fw.close();

}catch (IOException e){e.printStackTrace();}





    }





    public boolean getApplicationActive(){

        FileReader fr = null;
        String res = "";
        try {
            fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);

            res = br.readLine();


            res = res.split(" : ")[1].trim();

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res.equals("1"))
            return true;
        else
            return false;

    }









}
