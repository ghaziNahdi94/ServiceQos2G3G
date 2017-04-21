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
 * Created by ghazi on 27/07/2016.
 */
public class Info {

    private final String PATH;

    public Info(String path){

        
        PATH = path;

    }

    public Info(Context context){


        PATH = context.getFilesDir().getAbsolutePath()+"/info.txt";
    }





    public void setDefaultInfo(){


        try {


            FileWriter fw = new FileWriter(PATH);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Capture des traces : Activé");
            bw.newLine();
            bw.write("Nombre des appels : 0");
            bw.newLine();
            bw.write("Nombre des appels interrompu : 0");
            bw.newLine();
            bw.write("Taux des appels interrompu : 0 %");
            bw.newLine();
            bw.write("Nombre des SMS : 0");


            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                ligne1  = "Capture des traces : Activé";
            else
                ligne1 = "Capture des traces : Désactivé";




            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(lignes.get(3));
            bw.newLine();
            bw.write(lignes.get(4));

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

        if(res.equals("Activé"))
            return true;
        else
            return false;

    }



    public int getNbrAppel(){



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



        return Integer.parseInt(res);



    }








    public void setNbrAppel(int i) {

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


                ligne1  = "Nombre des appels : "+i;





            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(lignes.get(3));
            bw.newLine();
            bw.write(lignes.get(4));

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}


    }





    public int getNbrAppelInterrompu(){



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



        return Integer.parseInt(res);



    }





    public void setNbrAppelInterrompu(int i) {

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


            ligne1  = "Nombre des appels interrompu : "+i;





            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(3));
            bw.newLine();
            bw.write(lignes.get(4));

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}


    }



    public long getTauxAppelInterrompu(){



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



        return Long.parseLong(res.split("%")[0].trim());



    }





    public void setTauxAppelInterrompu(long i) {

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


            ligne1  = "Taux des appels interrompu : "+i+" %";





            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(ligne1);
            bw.newLine();
            bw.write(lignes.get(4));

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}


    }



    public int getNbrSMS(){



        FileReader fr = null;
        String res = "";
        try {
            fr = new FileReader(PATH);
            BufferedReader br = new BufferedReader(fr);

            res = br.readLine();
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



        return Integer.parseInt(res);



    }




    public void setNbrSMS(int i) {

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


            ligne1  = "Nombre des SMS : "+i;





            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(lignes.get(0));
            bw.newLine();
            bw.write(lignes.get(1));
            bw.newLine();
            bw.write(lignes.get(2));
            bw.newLine();
            bw.write(lignes.get(3));
            bw.newLine();
            bw.write(ligne1);

            bw.flush();
            bw.close();
            fw.close();

        }catch (IOException e){e.printStackTrace();}


    }




}
