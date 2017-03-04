package ru.mail.aslanisl.allinone.bashPostFragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BushPostModel {

    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("elementPureHtml")
    @Expose
    private String elementPureHtml;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public BushPostModel withSite(String site) {
        this.site = site;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BushPostModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BushPostModel withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BushPostModel withLink(String link) {
        this.link = link;
        return this;
    }

    public String getElementPureHtml() {
        return elementPureHtml;
    }

    public void setElementPureHtml(String elementPureHtml) {
        this.elementPureHtml = elementPureHtml;
    }

    public BushPostModel withElementPureHtml(String elementPureHtml) {
        this.elementPureHtml = elementPureHtml;
        return this;
    }

}