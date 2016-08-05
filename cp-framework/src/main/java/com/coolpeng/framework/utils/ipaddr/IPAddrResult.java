package com.coolpeng.framework.utils.ipaddr;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.framework.utils.StringUtils;

import java.util.List;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class IPAddrResult {

    private boolean ok = true;

    private String country;
    private String country_id;
    private String area;
    private String area_id;
    private String region;
    private String region_id;
    private String city;
    private String city_id;
    private String county;
    private String county_id;
    private String isp;
    private String isp_id;
    private String ip;


    public IPAddrResult() {
    }



    public IPAddrResult(boolean isOK) {
        this.setOk(isOK);
    }


    public IPAddrResult(JSONObject jsonObject) {
        this.country =jsonObject.getString("country");
        this.country_id = jsonObject.getString("country_id");
        this.area = jsonObject.getString("area");
        this.area_id = jsonObject.getString("area_id");
        this.region = jsonObject.getString("region");
        this.region_id = jsonObject.getString("region_id");
        this.city = jsonObject.getString("city");
        this.city_id = jsonObject.getString("city_id");
        this.county = jsonObject.getString("county");
        this.county_id = jsonObject.getString("county_id");
        this.isp = jsonObject.getString("isp");
        this.isp_id = jsonObject.getString("isp_id");
        this.ip = jsonObject.getString("ip");
        this.ok = true;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }


    public String toDisplayString(){
        StringBuffer sb = new StringBuffer();

        if (!"CN".equalsIgnoreCase(this.country_id)){
            if (StringUtils.isNotBlank(this.country)){
                sb.append(this.country);
            }
        }
        sb.append(";");

        if (StringUtils.isNotBlank(this.region)){
            sb.append(this.region);
        }
        sb.append(";");

        if (StringUtils.isNotBlank(this.city)){
            sb.append(this.city);
        }
        sb.append(";");

//        if (StringUtils.isNotBlank(this.isp)){
//            sb.append(this.isp);
//        }
//        sb.append(";");

        return sb.toString();
    }


    @Override
    public String toString() {
        return "IPAddrResult{" +
                "ok=" + ok +
                ", country='" + country + '\'' +
                ", country_id='" + country_id + '\'' +
                ", area='" + area + '\'' +
                ", area_id='" + area_id + '\'' +
                ", region='" + region + '\'' +
                ", region_id='" + region_id + '\'' +
                ", city='" + city + '\'' +
                ", city_id='" + city_id + '\'' +
                ", county='" + county + '\'' +
                ", county_id='" + county_id + '\'' +
                ", isp='" + isp + '\'' +
                ", isp_id='" + isp_id + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
