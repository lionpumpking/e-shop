package com.myproject.eshop.demos.web.Result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.myproject.eshop.demos.web.model.Msg;
import lombok.Data;

import java.io.Serializable;

/**
    * Description:
    * Author:
    * Data:
    */
@Data
   public class MsgVo {

      private Integer id;

      private Integer fromid;

      private Integer toid;

      private String msg;

      private boolean mine;

      private int type;

      public MsgVo(Msg msg,boolean Mine){
         this.id = msg.getId();
         this.fromid = msg.getFromid();
         this.toid = msg.getToid();
         this.msg = msg.getMsg();
         this.mine = Mine;
         this.type = msg.getType();
      }
   }
