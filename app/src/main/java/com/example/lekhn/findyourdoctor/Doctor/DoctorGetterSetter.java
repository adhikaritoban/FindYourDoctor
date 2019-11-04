package com.example.lekhn.findyourdoctor.Doctor;

public class DoctorGetterSetter {
    private String doctor_id, doctor_name,specialist,hospital_name,image_url,comment,contact,gmail,sunday,monday,tuesday,wednesday,thursday,friday,saturday,disease_name;

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public DoctorGetterSetter(String doctor_name, String specialist, String hospital_name, String image_url, String comment, String contact, String gmail, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String disease_name) {
        this.setDoctor_id(doctor_id);
        this.setDoctor_name(doctor_name);
        this.setSpecialist(specialist);
        this.setHospital_name(hospital_name);
        this.setImage_url(image_url);
        this.setComment(comment);
        this.setContact(contact);
        this.setGmail(gmail);
        this.setSunday(sunday);
        this.setMonday(monday);
        this.setTuesday(tuesday);
        this.setWednesday(wednesday);
        this.setThursday(thursday);
        this.setFriday(friday);
        this.setSaturday(saturday);
        this.setDisease_name(disease_name);
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }
    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
}
