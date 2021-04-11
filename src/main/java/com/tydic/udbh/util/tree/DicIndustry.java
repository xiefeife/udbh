package com.last.demo.util.tree;

import java.io.Serializable;

/**
 * @author: XIE
 * @Date:2020/8/12 15:23
 * @ClassName:DicIndustry
 */
public class DicIndustry implements Serializable {

    private String industryCode;
    private String industryName;
    private  String  parentCode;

    private  String level;
    private  String spell;

    private  String enable;


    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
