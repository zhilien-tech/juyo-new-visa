package com.juyo.visa.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket业务处理器
 * <p>
 * 获取当前登陆的用户，把用户和该用户所有需要消息通知的页面
 */
@Configuration
//指明该类为Spring 配置类
@EnableWebSocket
//声明该类支持WebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	/**
	 * 设置来自那些域名的请求可访问，默认为localhost
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("=========注册websocket=================");

		//注册WebSocket Server实现类，第二个参数是访问WebSocket的地址
		registry.addHandler(myhandler(), "/websocket").addInterceptors(myInterceptors()).setAllowedOrigins("*");
		registry.addHandler(myBasicInfoHandler(), "/basicinfowebsocket").addInterceptors(myInterceptors())
				.setAllowedOrigins("*");
		registry.addHandler(mySimpleInfoHander(), "/simpleinfowebsocket").addInterceptors(myInterceptors())
				.setAllowedOrigins("*");
		registry.addHandler(myVisaInfoHander(), "/visainfowebsocket").addInterceptors(myInterceptors())
				.setAllowedOrigins("*");
		registry.addHandler(myVcodeHander(), "/vcodewebsocket").addInterceptors(myInterceptors())
				.setAllowedOrigins("*");
		registry.addHandler(myhandler(), "/sockjs/websocket").addInterceptors(myInterceptors()).withSockJS();
		registry.addHandler(mySimpleSendInfoWSHandler(), "/simplesendinfosocket").addInterceptors(myInterceptors())
				.setAllowedOrigins("*");
	}

	@Bean
	public VisaInfoWSHandler myVisaInfoHander() {
		return new VisaInfoWSHandler();
	}

	@Bean
	public VcodeWSHandler myVcodeHander() {
		return new VcodeWSHandler();
	}

	@Bean
	public SimpleInfoWSHandler mySimpleInfoHander() {
		return new SimpleInfoWSHandler();
	}

	@Bean
	public SimpleSendInfoWSHandler mySimpleSendInfoWSHandler() {
		return new SimpleSendInfoWSHandler();
	}

	@Bean
	public BasicInfoWSHandler myBasicInfoHandler() {
		return new BasicInfoWSHandler();
	}

	@Bean
	public WebSocketHandler myhandler() {
		return new DemoWSHandler();
	}

	@Bean
	public HandshakeInterceptor myInterceptors() {
		return new HandshakeInterceptor();
	}

}
