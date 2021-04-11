package com.last.demo.util.tree;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: XIE
 * @Date:2020/8/12 11:28
 * @ClassName:IndustryMapTreeVo
 */
public class IndustryMapTreeVo {

    //节点名称
    private String title;
    //节点id
    private String key;
    //图标
    private String icon;
    //等级
    private String level;
    //是否禁点
    private boolean disabled = false;
    //是否禁选
    private boolean disableCheckbox = false;
    //是否禁选
    private boolean selectable = false;
    //是否叶节点
    private boolean isLeaf = false;
    //是否展开  默认不展开
    private boolean expanded = false;
    // 上一层节点的key
    private String pId;
    // 当前节点的状态
    private String status;
    //子元素
    @JsonInclude( JsonInclude.Include.NON_EMPTY)
    private List<IndustryMapTreeVo> children =new ArrayList<>();

    public List<IndustryMapTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<IndustryMapTreeVo> children) {
        this.children = children;
    }

    // 下一层节点个数
    private BigInteger childNum;


    private String kind;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }




    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigInteger getChildNum() {
        return childNum;
    }

    public void setChildNum(BigInteger childNum) {
        this.childNum = childNum;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }


}
