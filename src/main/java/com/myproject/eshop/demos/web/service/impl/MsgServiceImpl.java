package com.myproject.eshop.demos.web.service.impl;

import com.myproject.eshop.demos.web.model.Msg;
import com.myproject.eshop.demos.web.mapper.MsgMapper;
import com.myproject.eshop.demos.web.service.IMsgService;
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
 * @since 2024-11-13
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

    @Autowired
    private MsgMapper mapper;

    @Override
    public List<Msg> getMsgList(int fromid, int toid) {
        return mapper.getMsgList(fromid, toid);
    }

    @Override
    public List<Msg> getMsgListAll(int id) {
        return mapper.getMsgListAll(id);
    }
}
