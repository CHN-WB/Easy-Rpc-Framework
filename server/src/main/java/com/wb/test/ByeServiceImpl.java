package com.wb.test;

import com.wb.rpc.annotation.Service;
import com.wb.rpc.api.ByeObject;
import com.wb.rpc.api.ByeService;

/**
 * 买东西服务的实现类
 *
 * @ClassName: ByeServiceImpl
 * @Author: wangb
 * @Date: 2021/5/16 15:25
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(ByeObject object) {
        Integer id = object.getId();
        String message = object.getMessage();
        return "您买的是第 " + id +" 号商品, 商品名称是: " + message;
    }
}