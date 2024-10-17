package com.myproject.eshop.demos.web.config;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.*;

/**
 * @author hxt
 * @since 2024-10-17
 */

/**
 * MyBatisPlus配置类
 */
@Configuration
public class MyBatisPlusConfig {

   /**
    * MyBatisPlus拦截器（用于分页）
    */
   @Bean
   public MybatisPlusInterceptor paginationInterceptor() {
      MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
      //添加MySQL的分页拦截器
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
      return interceptor;
   }

//   @Bean
//   public MybatisPlusInterceptor mybatisPlusInterceptor() {
//      MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//      // 添加非法SQL拦截器
//      interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
//      return interceptor;
//   }

}
