package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Express;
import com.myproject.eshop.demos.web.mapper.ExpressMapper;
import com.myproject.eshop.demos.web.service.IExpressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
@Service
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express> implements IExpressService {

    @Autowired
    private  ExpressMapper expressMapper;


    @Override
    public Express getByName(String express) {
        return expressMapper.getByName(express);
    }
}
