package com.example.coursewebapp.Models;

public class MessageObj {
    int id;
    String TeacherName;
    String Subject;
    String Message;

    public MessageObj() {
    }

    public MessageObj(int id, String teacherName, String subject, String message) {
        this.id = id;
        TeacherName = teacherName;
        Subject = subject;
        Message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
