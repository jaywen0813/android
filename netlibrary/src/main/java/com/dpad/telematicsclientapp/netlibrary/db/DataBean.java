package com.dpad.telematicsclientapp.netlibrary.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * UserBean实体类，存储数据库中user表中的数据
 * <p>
 * 注解：
 * DatabaseTable：通过其中的tableName属性指定数据库名称
 * DatabaseField：代表数据表中的一个字段
 * ForeignCollectionField：一对多关联，表示一个UserBean关联着多个ArticleBean（必须使用ForeignCollection集合）
 * <p>
 * 属性：
 * id：当前字段是不是id字段（一个实体类中只能设置一个id字段）
 * columnName：表示当前属性在表中代表哪个字段
 * generatedId：设置属性值在数据表中的数据是否自增
 * useGetSet：是否使用Getter/Setter方法来访问这个字段
 * canBeNull：字段是否可以为空，默认值是true
 * unique：是否唯一
 * defaultValue：设置这个字段的默认值
 */
@DatabaseTable(tableName = "datasBean") // 指定数据表的名称
public class DataBean {
    // 定义字段在数据库中的字段名
    public static final String COLUMNNAME_ID = "id";

    @DatabaseField(generatedId = true, columnName = COLUMNNAME_ID, useGetSet = true)
    private int id;
    @DatabaseField(columnName = "description", useGetSet = true, canBeNull = false, unique = true)
    private String description;
    @DatabaseField(columnName = "distinctId", useGetSet = true, canBeNull = false, unique = true)
    private String distinctId;
    @DatabaseField(columnName = "event", useGetSet = true, canBeNull = false, unique = true)
    private String event;
    @DatabaseField(columnName = "executeTime", useGetSet = true, canBeNull = false, unique = true)
    private String executeTime;
    @DatabaseField(columnName = "project", useGetSet = true, canBeNull = false, unique = true)
    private String project;
    @DatabaseField(columnName = "sourceType", useGetSet = true, canBeNull = false, unique = true)
    private String sourceType;
    @DatabaseField(columnName = "userType", useGetSet = true, canBeNull = false, unique = true)
    private String userType;
    @DatabaseField(columnName = "properties", useGetSet = true, canBeNull = false, unique = true)
    private String properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistinctId() {
        return distinctId;
    }

    public void setDistinctId(String distinctId) {
        this.distinctId = distinctId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}