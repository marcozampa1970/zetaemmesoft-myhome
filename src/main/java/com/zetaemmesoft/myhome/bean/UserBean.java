package com.zetaemmesoft.myhome.bean;


import java.io.Serializable;

import lombok.Data;

@Data
public class UserBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6196203918435253539L;

    private String id;

    private String username;

    private String password;
}
