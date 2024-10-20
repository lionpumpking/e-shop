//package com.myproject.eshop.demos.web.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.util.pattern.PathPatternParser;
//
///**
// * Description:
// * Author:
// * Data:
// */
//@Configuration
//public class CorsConfig  {
//
//   public CorsFilter addCorsMappings(CorsRegistry registry) {
//      CorsConfiguration config = new CorsConfiguration();
//      config.addAllowedOrigin("http://172.20.36.194:8081");
//      config.addAllowedHeader("*");
//      config.addAllowedMethod("*");
//      config.setAllowCredentials(true);
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//      source.registerCorsConfiguration("/**", config);
//      return new CorsFilter(source);
//
//      //会报错返回没有Access-Control-Allow-Origin
//   }
//}
