package com.teamnoname.streetartzone.StreetStage;

public class StageInfo {

    private int seq;
    private String district;
    private String placeName;
    private String address;
    private double lat;
    private double lot;

    public StageInfo(String district, String placeName, String address, double lat, double lot) {
        this.district = district;
        this.placeName = placeName;
        this.address = address;
        this.lat = lat;
        this.lot = lot;
    }

    public int getSeq() {
        return seq;
    }

    public String getDistrict() {
        return district;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLot() {
        return lot;
    }
}
