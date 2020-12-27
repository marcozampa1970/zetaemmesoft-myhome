package com.zetaemmesoft.myhome.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwitchBean extends ItemBean {

    private static final long serialVersionUID = 2749608550131628013L;

    private String unit;
    private String topic;

    private Integer value;

}

