package com.wb.rpc.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @ClassName: LoadBalancer
 * @Author: wangb
 * @Date: 2021/5/13 15:38
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}