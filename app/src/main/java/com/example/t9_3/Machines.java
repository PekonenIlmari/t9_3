package com.example.t9_3;

import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Machines {
    private PostalMachine postalMachine;
    private String ee = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=EE&type=APT";
    private String fin = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=FI&type=APT";

    public ArrayList<PostalMachine> machines = new ArrayList<PostalMachine>();

    private static Machines mc = new Machines();

    public Machines() {
        readXML(ee);
        readXML(fin);
        Collections.sort(machines, PostalMachine.nameComparator);
        parseDays();
    }

    public static Machines getInstance() {
        return mc;
    }

    private void readXML(String url) {
        String newA = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("item");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    /*if (element.getElementsByTagName("country").item(0).getTextContent().equals("EE")) {
                        String tempA = element.getElementsByTagName("availability").item(0).getTextContent();
                        newA = parseAvailability(tempA);
                        //System.out.println(newA + " VIRO");
                    } else {
                        newA = element.getElementsByTagName("availability").item(0).getTextContent();
                        //System.out.println(newA + " SUOMI");
                    }*/

                    String tempA = element.getElementsByTagName("availability").item(0).getTextContent();
                    newA = parseAvailability(tempA);

                    postalMachine = new PostalMachine(element.getElementsByTagName("place_id").item(0).getTextContent(),
                            element.getElementsByTagName("name").item(0).getTextContent(),
                            element.getElementsByTagName("city").item(0).getTextContent(),
                            element.getElementsByTagName("address").item(0).getTextContent(),
                            element.getElementsByTagName("country").item(0).getTextContent(),
                            element.getElementsByTagName("postalcode").item(0).getTextContent(),
                            newA);
                    machines.add(postalMachine);
                }
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("####DONE####");
        }


    }

    private String parseAvailability(String a) {
        String[] temp = a.split(" ");

        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].replace("E-P", "ma-su");
            temp[i] = temp[i].replace("E-L", "ma-la");
            temp[i] = temp[i].replace("P", "su");
            temp[i] = temp[i].replace("L", "la");
            temp[i] = temp[i].replace("E-N", "ma-to");
            temp[i] = temp[i].replace("R-L", "pe-la");
            temp[i] = temp[i].replace("E-R", "ma-pe");
            temp[i] = temp[i].replace("L-P", "la-su");
            temp[i] = temp[i].replace("0-2", "0 - 2");
            temp[i] = temp[i].replace("0-1", "0 - 1");
            temp[i] = temp[i].replace(":", ".");
            temp[i] = temp[i].replace(";", "");
            temp[i] = temp[i].replace(",", "");
            temp[i] = temp[i].replace(" ", "");
            temp[i] = temp[i].replace("kell", "");
        }

        return String.join(" ", temp);
    }

    private void parseDays() {
        for (int i = 0; i < machines.size(); i++) {
            System.out.println(machines.get(i).getAvailability());
        }
    }

    int findInvIndex(String name) {
        int value = -1;
        for (int i = 0; i < machines.size(); i++) {
            if (name.equals(machines.get(i).getName().trim())) {
                value = i;
            }
        }
        return value;
    }

    public void print() {
        /*for (int i = 0; i < machines.size(); i++) {
            System.out.println(machines.get(i).getName() + " " + machines.get(i).getAvailability());
        }*/
        System.out.println(machines.get(3).getName() + " " + machines.get(3).getAvailability());
    }
}
