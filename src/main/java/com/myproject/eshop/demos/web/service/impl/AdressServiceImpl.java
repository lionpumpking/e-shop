package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Adress;
import com.myproject.eshop.demos.web.mapper.AdressMapper;
import com.myproject.eshop.demos.web.service.IAdressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxt
 * @since 2024-10-22
 */
@Service
public class AdressServiceImpl extends ServiceImpl<AdressMapper, Adress> implements IAdressService {

    @Autowired
    private AdressMapper adressMapper;

    @Override
    public List<Adress> getByUserId(int userid) {
        return adressMapper.getByUserId(userid);
    }
}
