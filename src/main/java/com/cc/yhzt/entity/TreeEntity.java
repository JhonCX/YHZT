package com.cc.yhzt.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author :cc
 * @date : 2019/6/10
 */
@Data
public class TreeEntity {
    private String id;
    private String title;
    private List<TreeEntity> children;
}
