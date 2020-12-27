package com.zetaemmesoft.myhome.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorBean extends ItemBean {

    private static final long serialVersionUID = -4418696677549014511L;

    private Float value;
    private String unit;    
    
    /*
    value: number;
    unit: string;
    */
}
