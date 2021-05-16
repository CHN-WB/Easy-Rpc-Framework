package com.wb.rpc.loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @ClassName: RoundRobinLoadBalancer
 * @Author: wangb
 * @Date: 2021/5/13 15:42
 */
public class RoundRobinLoadBalancer implements LoadBalancer{

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if(index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);
    }
}