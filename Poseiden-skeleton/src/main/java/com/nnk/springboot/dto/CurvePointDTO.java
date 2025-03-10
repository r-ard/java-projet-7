package com.nnk.springboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CurvePointDTO {
    private Integer id;

    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @Min(value = 1, message = "Term must not be null")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Min(value = 1, message = "Value must not be null")
    private Double value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
