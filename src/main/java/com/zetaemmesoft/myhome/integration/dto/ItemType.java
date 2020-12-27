package com.zetaemmesoft.myhome.integration.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemType implements Serializable {

    private static final long serialVersionUID = 6862864743481926171L;
    
    private Integer id;
    private String name;  
  
}
