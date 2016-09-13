//package com.coolpeng.chat.websocket.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebMvc
//@EnableWebSocket
//public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
//
//    @Autowired
//    private ChatWebSocketHandler chatWebSocketHandler;
//
//    @Autowired
//    private WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new ChatWebSocketHandler(),"chat/websocket.websocket").addInterceptors(webSocketHandshakeInterceptor);
//    }
//
//
////    @Bean
////    public WebSocketHandler chatWebSocketHandler(){
////        return new ChatWebSocketHandler();
////    }
//
//}