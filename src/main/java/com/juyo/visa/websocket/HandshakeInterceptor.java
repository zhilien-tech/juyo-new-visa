package com.juyo.visa.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.uxuexi.core.common.util.Util;

/**
 * websocket拦截器，一般用于处理权限，日志等问题
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	private static Log logger = Logs.get();

		@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		// 解决The extension [x-webkit-deflate-frame] is not supported问题
		if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
		}

		logger.info("Before Handshake");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest sreq = (ServletServerHttpRequest) request;
			HttpSession session = sreq.getServletRequest().getSession(false);

			if (!Util.isEmpty(session)) {
				// 标记用户
				Object ouid = session.getAttribute("uid");
				String uid = "";
				if (!Util.isEmpty(ouid)) {
					uid = (String) ouid;
					if (!Util.isEmpty(uid)) {
						attributes.put("uid", uid);
					}
					logger.info("Websocket:用户[ID:" + session.getAttribute("uid") + "]已经建立连接");
				} else {
					uid = session.getId();
					attributes.put("uid", uid);
				}
			}

		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}

}
