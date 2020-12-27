package com.zetaemmesoft.myhome.integration.dto;

import java.io.Serializable;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Switch implements Serializable {

    private static final long serialVersionUID = 1242280015937350027L;

    private Integer id;
    private String name;  
    private int type;      
    private Integer value;
    private String unit;
    private String node;    
    private Timestamp time;
    private String topic;  

}

