package com.jishitoutiao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 爬虫源表
 * @author leitianxiang
 *
 */
@Entity
@Table(name="crawler_source")
public class CrawlerSource implements Serializable {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid")
    @Column(name="bid",length=40,nullable=false)
    private String bid;

    @Column(name="bkey",length=20,nullable=false)
    private String bkey;

    @Column(name="bname",length=20,nullable=false)
    private String bname;

    @Column(name="homepage",length=100,nullable=true)
    private String homepage;

    @Column(name="logo",length=100,nullable=true)
    private String logo;

    @Column(name="remark",length=100,nullable=true)
    private String remark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update")
    private Date lastUpdate;

    public Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getBkey() {
        return bkey;
    }
    public void setBkey(String bkey) {
        this.bkey = bkey;
    }
    public String getBname() {
        return bname;
    }
    public void setBname(String bname) {
        this.bname = bname;
    }
    public String getHomepage() {
        return homepage;
    }
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CrawlerSource() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CrawlerSource(String bid, String bkey, String bname, String homepage, String logo, String remark,
                         Date lastUpdate) {
        super();
        this.bid = bid;
        this.bkey = bkey;
        this.bname = bname;
        this.homepage = homepage;
        this.logo = logo;
        this.remark = remark;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "CrawlerSource [bid=" + bid + ", bkey=" + bkey + ", bname=" + bname + ", homepage=" + homepage
                + ", logo=" + logo + ", remark=" + remark + ", lastUpdate=" + lastUpdate + "]";
    }
}