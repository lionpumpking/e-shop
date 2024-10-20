package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Menu;
import com.myproject.eshop.demos.web.mapper.MenuMapper;
import com.myproject.eshop.demos.web.service.IMenuService;
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
 * @since 2024-10-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private  MenuMapper menuMapper;


    @Override
    public List<Menu> getMenuByRoleid(String roleid) {
        return menuMapper.getByRoleid(roleid);
    }
}
