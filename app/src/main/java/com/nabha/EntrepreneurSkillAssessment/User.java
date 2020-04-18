package com.nabha.EntrepreneurSkillAssessment;

public class User {
    public String Name;
    public String Surname;

    public User(String name, String surname, String email, String aClass, String caste, String dob, String areaOfIntrest, String familyIncom, String annualIncom, String estate, String maritalStatus, String area, String phone) {
        Name = name;
        Surname = surname;
        Email = email;
        Class = aClass;
        Caste = caste;
        Dob = dob;
        AreaOfIntrest = areaOfIntrest;
        FamilyIncom = familyIncom;
        AnnualIncom = annualIncom;
        Estate = estate;
        MaritalStatus = maritalStatus;
        Area = area;
        Phone = phone;
    }

    public String Email;
    public String Class ;
    public String Caste ;
    public String Dob ;
    public String AreaOfIntrest;
    public String FamilyIncom;
    public String AnnualIncom;
    public String Estate;
    public String MaritalStatus;
    public String Area;
    public String Phone;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }






}
