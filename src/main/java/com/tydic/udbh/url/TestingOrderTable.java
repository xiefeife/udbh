package com.tydic.udbh.url;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TestingOrderTable implements Serializable {
    /**
     * 序列号YYYYMMDDHHMMSS+毫秒(3) +6位随机数
     */
    private String transid;

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户宽带账号
     */
    private String userBroadcast;

    /**
     * 用户联系电话
     */
    private String userPhone;

    /**
     * 用户身份证
     */
    private String userIdc;

    /**
     * 用户所在省分编码（2位）
     */
    private String provinceCode;

    /**
     * 用户所在地市编码（3位）
     */
    private String cityCode;

    /**
     * 用户所在区县编码（6位）
     */
    private String districtCode;

    /**
     * 用户详细地址
     */
    private String userAddr;

    /**
     * 订单状态；（默认0未处理）,0-未处理,2-处理成功
     */
    private Integer orderStatus;

    /**
     * 是否联通宽带用户 0-否 1-是
     */
    private Integer isCUBroadcast;

    /**
     * 下单时间(yyyyMMddHHmmss)
     */
    private Date orderTime;

    /**
     * 户型信息    下拉  1:一室、2:两室、3:三室、4:四室、5:五室及以上
     */
    private Integer unit;

    /**
     * 建筑面积 平米,正值,保留两位小数
     */
    private Float area;

    /**
     * 主卧信息号强度
     */
    private Integer masterBedroomDbm;

    /**
     * 卫生间信息号强度
     */
    private Integer bathroomDbm;

    /**
     * 客厅信息号强
     */
    private Integer livingRoomDbm;

    /**
     * 同频干扰 1强、2较强、3较弱、4弱
     */
    private Integer coi;

    /**
     * 邻频干扰 1强、2较强、3较弱、4弱
     */
    private Integer aci;

    /**
     * 丢包率 百分比分子,保留两位小数
     */
    private Float packetLoss;

    /**
     * 网络延迟 单位ms正整数
     */
    private Float latency;

    /**
     * 最高速度 Mb/s保留两位小数
     */
    private Float highestRate;

    /**
     * 最低速度 Mb/s保留两位小数
     */
    private Float lowestRate;

    /**
     * 平均速度 Mb/s保留两位小数
     */
    private Float avgSpeed;

    /**
     * 网速水平 1高等、2中等、3低等
     */
    private Integer speedLevel;

    /**
     * 网速超全国 百分比分子,整数
     */
    private Float gtRatio;

    /**
     * 检测员姓名 16汉字
     */
    private String inspectorName;

    /**
     * 检测员电话 1开头13位
     */
    private String inspectorPhone;

    /**
     * 小区名称,16汉字以内
     */
    private String compoundName;

    /**
     * 宽带速率
     */
    private Integer broadbandRate;

    /**
     * 来源渠道
     * 1:端外-去预约页
     * 2:广告位-去预约页
     * 3:宽带专家对话框-预约网络检测单-下单页
     * 4:手厅-服务-宽带-预约网络检测单-下单页
     * 5:宽带专区-下单页
     */
    private Integer sourceChannel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单结束时间
     */
    private Date orderEndTime;

    /**
     * aliyun的oss图片url地址
     */
    private String ossPngUrl;

    public String getOssPngUrl() {
        return ossPngUrl;
    }

    public void setOssPngUrl(String ossPngUrl) {
        this.ossPngUrl = ossPngUrl;
    }

    /**
     * 失败类型 :1-检测地址无资源 2-用户退单
     */
    private Integer failType;

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid == null ? null : transid.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserBroadcast() {
        return userBroadcast;
    }

    public void setUserBroadcast(String userBroadcast) {
        this.userBroadcast = userBroadcast == null ? null : userBroadcast.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserIdc() {
        return userIdc;
    }

    public void setUserIdc(String userIdc) {
        this.userIdc = userIdc == null ? null : userIdc.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode == null ? null : districtCode.trim();
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr == null ? null : userAddr.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIsCUBroadcast() {
        return isCUBroadcast;
    }

    public void setIsCUBroadcast(Integer isCUBroadcast) {
        this.isCUBroadcast = isCUBroadcast;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getMasterBedroomDbm() {
        return masterBedroomDbm;
    }

    public void setMasterBedroomDbm(Integer masterBedroomDbm) {
        this.masterBedroomDbm = masterBedroomDbm;
    }

    public Integer getBathroomDbm() {
        return bathroomDbm;
    }

    public void setBathroomDbm(Integer bathroomDbm) {
        this.bathroomDbm = bathroomDbm;
    }

    public Integer getLivingRoomDbm() {
        return livingRoomDbm;
    }

    public void setLivingRoomDbm(Integer livingRoomDbm) {
        this.livingRoomDbm = livingRoomDbm;
    }

    public Integer getCoi() {
        return coi;
    }

    public void setCoi(Integer coi) {
        this.coi = coi;
    }

    public Integer getAci() {
        return aci;
    }

    public void setAci(Integer aci) {
        this.aci = aci;
    }

    public Float getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(Float packetLoss) {
        this.packetLoss = packetLoss;
    }

    public Float getLatency() {
        return latency;
    }

    public void setLatency(Float latency) {
        this.latency = latency;
    }

    public Float getHighestRate() {
        return highestRate;
    }

    public void setHighestRate(Float highestRate) {
        this.highestRate = highestRate;
    }

    public Float getLowestRate() {
        return lowestRate;
    }

    public void setLowestRate(Float lowestRate) {
        this.lowestRate = lowestRate;
    }

    public Float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Integer getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(Integer speedLevel) {
        this.speedLevel = speedLevel;
    }

    public Float getGtRatio() {
        return gtRatio;
    }

    public void setGtRatio(Float gtRatio) {
        this.gtRatio = gtRatio;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName == null ? null : inspectorName.trim();
    }

    public String getInspectorPhone() {
        return inspectorPhone;
    }

    public void setInspectorPhone(String inspectorPhone) {
        this.inspectorPhone = inspectorPhone == null ? null : inspectorPhone.trim();
    }

    public String getCompoundName() {
        return compoundName;
    }

    public void setCompoundName(String compoundName) {
        this.compoundName = compoundName == null ? null : compoundName.trim();
    }

    public Integer getBroadbandRate() {
        return broadbandRate;
    }

    public void setBroadbandRate(Integer broadbandRate) {
        this.broadbandRate = broadbandRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(Integer sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public Integer getFailType() {
        return failType;
    }

    public void setFailType(Integer failType) {
        this.failType = failType;
    }

}
