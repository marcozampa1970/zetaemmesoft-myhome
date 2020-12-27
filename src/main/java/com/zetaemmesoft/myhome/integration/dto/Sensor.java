package com.zetaemmesoft.myhome.integration.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Sensor implements Serializable {

    private static final long serialVersionUID = -3818380383849042747L;
    
    private Integer id;
    private String name;  
    private int type;      
    private Float value;
    private String unit;  
    private String node;      
    private Timestamp time;
    private String topic;      
}
