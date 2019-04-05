package com.company.firebaseproject;

import java.io.Serializable;

public class Grade implements Serializable {

    int course_id;
    String course_name;
    String grade;
    int student_id;

    String email;
    String name;
    String password;
    int id;

    public Grade() {}

    public Grade(String e, int i, String n, String pass){
        email = e;
        id = i;
        name = n;
        password = pass;
    }
    public Grade(int course, String namee, String grad, int ID){
        course_id = course;
        course_name = namee;
        grade = grad;
        student_id = ID;
    }


    public int getcourse_id()       { return course_id; }
    public String getcourse_name()  { return course_name; }
    public String getgrade() { return grade; }
    public int getstudent_id() { return student_id;}
    public int get_id()       { return id; }
    public String getemail()  { return email; }
    public String getname() { return name; }
    public String getpassword() { return password;}
}