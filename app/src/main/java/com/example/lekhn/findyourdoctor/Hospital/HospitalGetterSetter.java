package com.example.lekhn.findyourdoctor.Hospital;

/**
 * Created by Lekhn on 1/8/2018.
 */

public class HospitalGetterSetter {
    private String hospital_id;
    private String hospital_names;
    private String hospital_address;
    private String image_url;
    private String total_doc;
    private String total_bed;
    private String total_icu;

    public HospitalGetterSetter(String hospital_names, String hospital_address, String image_url, String total_doc, String total_bed, String total_icu) {
        this.setHospital_id(hospital_id);
        this.setHospital_names(hospital_names);
        this.setHospital_address(hospital_address);
        this.setImage_url(image_url);
        this.setTotal_doc(total_doc);
        this.setTotal_bed(total_bed);
        this.setTotal_icu(total_icu);
    }

    public String getTotal_doc() {
        return total_doc;
    }

    public void setTotal_doc(String total_doc) {
        this.total_doc = total_doc;
    }

    public String getTotal_bed() {
        return total_bed;
    }

    public void setTotal_bed(String total_bed) {
        this.total_bed = total_bed;
    }

    public String getTotal_icu() {
        return total_icu;
    }

    public void setTotal_icu(String total_icu) {
        this.total_icu = total_icu;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_names() {
        return hospital_names;
    }

    public void setHospital_names(String hospital_names) {
        this.hospital_names = hospital_names;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}
