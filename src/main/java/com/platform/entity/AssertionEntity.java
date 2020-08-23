package com.platform.entity;

import lombok.Data;


@Data
public class AssertionEntity {

    private int key;
    private String parameter;
    private String except;
    private String rule;
}
