package com.wb.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: ByeObject
 * @Author: wangb
 * @Date: 2021/5/16 15:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByeObject implements Serializable {

    private Integer id;

    private String message;

}