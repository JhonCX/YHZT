package com.cc.yhzt.constant;

import com.cc.yhzt.entity.TreeEntity;
import lombok.Data;

import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/10
 */
@Data
public class TreeResult {
    private Status status;
    private List<TreeEntity> data;
}
