package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotEmpty;

public class RuleNameDTO {
    private Integer id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Description is mandatory")
    private String description;

    @NotEmpty(message = "JSON is mandatory")
    private String json;

    @NotEmpty(message = "Template is mandatory")
    private String template;

    @NotEmpty(message = "SQL is mandatory")
    private String sql;

    @NotEmpty(message = "SQLPart is mandatory")
    private String sqlPart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
