package com.myproject.eshop.demos.web.service;

import com.myproject.eshop.demos.web.model.Express;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxt
 * @since 2024-10-17
 */
public interface IExpressService extends IService<Express> {

    Express getByName(String express);
}
