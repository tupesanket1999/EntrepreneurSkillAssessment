package com.example.testapplication;

public class User {
    public String name;
    public String surname;
    public String email;
    public String clas ;
    public String cast ;
    public String dob ;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String surname, String email, String clas, String cast, String dob, String areaOfIntrest, String familyIncom, String annualIncom, String estate, boolean married, boolean rural) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.clas = clas;
        this.cast = cast;
        this.dob = dob;
        this.areaOfIntrest = areaOfIntrest;
        this.familyIncom = familyIncom;
        this.annualIncom = annualIncom;
        this.estate = estate;
        this.married = married;
        this.rural = rural;
    }

    public String areaOfIntrest;
    public String familyIncom;
    public String annualIncom;
    public String estate;
    public boolean married;
    public boolean rural;



}
