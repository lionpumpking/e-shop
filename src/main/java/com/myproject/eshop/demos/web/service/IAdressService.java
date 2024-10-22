package com.myproject.eshop.demos.web.service;

import com.myproject.eshop.demos.web.model.Adress;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxt
 * @since 2024-10-22
 */

public interface IAdressService extends IService<Adress> {

    List<Adress> getByUserId(int userid);
}
