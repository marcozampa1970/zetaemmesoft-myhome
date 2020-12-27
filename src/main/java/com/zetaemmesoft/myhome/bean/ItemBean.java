package com.zetaemmesoft.myhome.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public abstract class ItemBean implements Serializable {

    private static final long serialVersionUID = -9018621124041399589L;

    private Integer id;  
    private String name;
    private String type;
    private String node;         
    private Date dateTime;
    
    /*
    id: number;
    name: string;
    type: string;
    node: string;
    dateTime: Date;    
    */





}

