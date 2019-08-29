package com.cubodrom.metacollector.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta_file_field")
public class MetaFileField {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;

    private String fieldName;

    private String fieldType;

    private long nullValueAmount;

    private long nonNullValueAmount;

    public MetaFileField() {
    }

    public MetaFileField(String filePath, String fieldName, String fieldType, long nullValueAmount, long nonNullValueAmount) {
        this.filePath = filePath;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.nullValueAmount = nullValueAmount;
        this.nonNullValueAmount = nonNullValueAmount;
    }

    public MetaFileField(Long id, String filePath, String fieldName, String fieldType, long nullValueAmount, long nonNullValueAmount) {
        this.id = id;
        this.filePath = filePath;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.nullValueAmount = nullValueAmount;
        this.nonNullValueAmount = nonNullValueAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public long getNullValueAmount() {
        return nullValueAmount;
    }

    public void setNullValueAmount(long nullValueAmount) {
        this.nullValueAmount = nullValueAmount;
    }

    public long getNonNullValueAmount() {
        return nonNullValueAmount;
    }

    public void setNonNullValueAmount(long nonNullValueAmount) {
        this.nonNullValueAmount = nonNullValueAmount;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", nullValueAmount=" + nullValueAmount +
                ", nonNullValueAmount=" + nonNullValueAmount +
                '}';
    }
}
