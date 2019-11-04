package com.example.lekhn.findyourdoctor.BloodManagement;

/**
 * Created by Lekhn on 1/14/2018.
 */

public class BloodRequestGetterSetter {
    private String donor_name, donor_contact,donor_group,donor_zone,donor_address;

    public BloodRequestGetterSetter(String donor_name, String donor_contact, String donor_group, String donor_zone, String donor_address) {
        this.setDonor_name(donor_name);
        this.setDonor_contact(donor_contact);
        this.setDonor_group(donor_group);
        this.setDonor_zone(donor_zone);
        this.setDonor_address(donor_address);
    }

    public String getDonor_name() {
        return donor_name;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public String getDonor_contact() {
        return donor_contact;
    }

    public void setDonor_contact(String donor_contact) {
        this.donor_contact = donor_contact;
    }

    public String getDonor_group() {
        return donor_group;
    }

    public void setDonor_group(String donor_group) {
        this.donor_group = donor_group;
    }

    public String getDonor_zone() {
        return donor_zone;
    }

    public void setDonor_zone(String donor_zone) {
        this.donor_zone = donor_zone;
    }

    public String getDonor_address() {
        return donor_address;
    }

    public void setDonor_address(String donor_address) {
        this.donor_address = donor_address;
    }
}
