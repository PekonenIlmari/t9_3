package com.example.t9_3;

import java.util.Comparator;

public class PostalMachine {
    private String place_id, name, city, address, country, postalCode, availability;
    private String[] days;


    public PostalMachine(String pId, String n, String c, String a, String co, String pCode, String avai) {
        place_id = pId;
        name = n;
        city = c;
        address = a;
        country = co;
        postalCode = pCode;
        availability = avai;
    }

    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }

    public String getAddress() {
        String wholeAddress = address + ", " + postalCode + ", " + city;
        return wholeAddress;
    }

    //For sorting the Machines Arraylist by comparing the SmartPost machnine names
    public static Comparator<PostalMachine> nameComparator = new Comparator<PostalMachine>() {
        public int compare(PostalMachine s1, PostalMachine s2) {
            String machineName1 = s1.getName().toUpperCase();
            String machineName2 = s2.getName().toUpperCase();

            //ascending order
            return machineName1.compareTo(machineName2);
        }
    };

}
