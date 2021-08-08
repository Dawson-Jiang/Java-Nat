package com.dawson.nat.baselib.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 告警信息
 *
 * @author dawson
 * date:2018/11/2
 */
public class AlarmInfo implements Serializable, Comparable<AlarmInfo> {
    public static final long serialVersionUID = 23434L;
    /**
     * 数据库唯一id
     */
    private String uid;
    /**
     * 告警ID
     */
    private Long id;
    /**
     * 告警ID 别名，用于 id 模糊查询
     */
    private String idAlias;
    /**
     * 告警恢复类型，默认值为 0，不代表任何意义；1 是收到对应告警的恢复告警，2 是因为模块或系统重启后收到重启恢复告警
     */
    private int recoverType;
    /**
     * 告警名称
     */
    private String name;
    /**
     * 告警产生/恢复时间
     */
    private Date time;
    /**
     * 告警级别：全部-1，提示0，警告1，严重2，致命3
     */
    private int level;
    /**
     * 告警类型：全部-1，未恢复0，已恢复1，事件2
     */
    private int type;
    /**
     * 告警模块：全部，平台，Apollo，下位机；然后在每个大项下又包含 N 多子模块
     */
    private String module;
    /**
     * 告警内容
     */
    private String content;
    /**
     * ？？？？我也不知道是啥
     */
    private String identify_info;
    /**
     * 告警恢复时间
     */
    private Date recoverTime;


    public AlarmInfo() {
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentify_info() {
        return this.identify_info;
    }

    public void setIdentify_info(String identify_info) {
        this.identify_info = identify_info;
    }

    public Date getRecoverTime() {
        return this.recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getIdAlias() {
        return idAlias;
    }

    public void setIdAlias(String idAlias) {
        this.idAlias = idAlias;
    }

    public int getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(int recoverType) {
        this.recoverType = recoverType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append("|");
        stringBuilder.append(type).append("|");
        stringBuilder.append(name).append("|");
        stringBuilder.append(level).append("|");
        stringBuilder.append(module).append("|");
        stringBuilder.append(content).append("|");
        stringBuilder.append(time).append("|");
        stringBuilder.append(identify_info).append("|");
        stringBuilder.append(idAlias).append("|");
        stringBuilder.append(recoverType).append("|");
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(AlarmInfo o) {
        return Long.compare(o.getTime().getTime(), this.getTime().getTime());
    }
}
