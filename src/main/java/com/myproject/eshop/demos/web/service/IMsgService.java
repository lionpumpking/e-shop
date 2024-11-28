package com.myproject.eshop.demos.web.service;

import com.myproject.eshop.demos.web.model.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxt
 * @since 2024-11-13
 */
@Service
public interface IMsgService extends IService<Msg> {

    List<Msg> getMsgList(int fromid, int toid);

    List<Msg> getMsgListAll(int id);
}
