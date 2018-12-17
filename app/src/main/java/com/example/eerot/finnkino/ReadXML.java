package com.example.eerot.finnkino;

import android.text.Editable;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ReadXML {

    ArrayList<Theatre> teatteritOlio = new ArrayList<Theatre>();
    ArrayList teatterit = new ArrayList();



    private static ReadXML XML = new ReadXML();

    public static ReadXML getInstance(){
        return XML;
    }


    public ArrayList theatres(){
        String id,teatteri = null;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";

            URL url = new URL("https://www.finnkino.fi/xml/TheatreAreas/");
            Document doc = builder.parse(String.valueOf(url));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i= 0; i<nList.getLength(); i++){
                Node node = nList.item(i);
                Theatre theatre = new Theatre();

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    id = element.getElementsByTagName("ID").item(0).getTextContent();

                    teatteri = element.getElementsByTagName("Name").item(0).getTextContent();

                    theatre.setInfo(teatteri,id);
                    teatteritOlio.add(theatre);

                    teatterit.add(teatteri);
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teatterit;
    }


    public int getID(String nimi) {

        int p;
        p = -11;

        //System.out.println(nimi);

        for (int i= 0; i< teatteritOlio.size() ;i++) {

            System.out.println(teatteritOlio.get(i).getName());

            if (teatteritOlio.get(i).getName().equals(nimi)){
                p = teatteritOlio.get(i).getID();
            }
        }
        return p;
    }

    public String Tname(int id) {

        String nimi = "";


        for (int i= 0; i< teatteritOlio.size() ;i++) {



            if (teatteritOlio.get(i).getID() == id){
                nimi = teatteritOlio.get(i).getName();
            }
        }
        return nimi;
    }

    public void printMovies(int id, TextView textView, Editable pvmAlk, LocalTime klo, LocalTime klo2) {

        String title= null, time= null;
        LocalTime vertaaklo = null;
        //String teatteri = Tname(id);
        String teatteri = "";

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();


            URL url = new URL("http://www.finnkino.fi/xml/Schedule/?area="+String.valueOf(id)+"&dt="+pvmAlk);
            System.out.println(String.valueOf(url));
            Document doc = builder.parse(String.valueOf(url));

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");

            for (int i= 0; i<nList.getLength(); i++){
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    title = element.getElementsByTagName("Title").item(0).getTextContent();
                    teatteri = element.getElementsByTagName("Theatre").item(0).getTextContent();

                    time = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();

                    if (klo != null || klo2 != null) vertaaklo = getTime(time);


                }

                if (klo != null && klo2 == null){

                    if (vertaaklo.isAfter(klo)) {
                        textView.append(teatteri +": "+title + " " + time + "\n\n");
                    }
                    System.out.println("1111");
                }

                if (klo2 != null && klo == null ){

                    if (vertaaklo.isBefore(klo2)) {
                        textView.append(teatteri +": "+title + " " + time + "\n\n");
                    }
                    System.out.println("2222");
                }
                if (klo == null && klo2 == null){

                    textView.append(teatteri +": "+title+" "+time+"\n\n");
                    System.out.println("3333");
                    continue;
                }

                else if (klo != null && klo2 != null){

                    if (vertaaklo.isAfter(klo) && vertaaklo.isBefore(klo2)) {
                        textView.append(teatteri +": "+title + " " + time + "\n\n");
                    }
                    System.out.println("444");
                }



                //System.out.println(getDate("2014-06-16T10:35:00"));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalTime getTime(String fulldate) {
        String input = fulldate;
        String date1 = "";
        LocalTime time = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDate date = LocalDate.parse(input, formatter);
            date1= LocalDate.parse(input, formatter).toString();

            time = LocalTime.parse(input, formatter);
            //System.out.println(date);
            //System.out.println(time);
        }
        catch (DateTimeParseException exc) {
            System.out.println("Päivämäärää ei voitu muokata!");
        }
        return time;
    }

}
