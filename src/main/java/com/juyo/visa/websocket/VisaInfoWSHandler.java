/**
 * SimpleInfoWSHandler.java
 * com.juyo.visa.websocket
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2018年2月1日 	 
 */
public class VisaInfoWSHandler implements WebSocketHandler {

	public static final Map<String, List<WebSocketSession>> visaInfoSocketSessionMap = Maps.newConcurrentMap();

	/**
	 * 连接建立
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession wss) throws Exception {
		System.out.println("connect to the websocket success......");

		String uid = (String) wss.getAttributes().get("uid");
		List<WebSocketSession> wssLst = visaInfoSocketSessionMap.get(uid);
		if (Util.isEmpty(wssLst)) {
			wssLst = Lists.newArrayList();
			wssLst.add(wss);
			visaInfoSocketSessionMap.put(uid, wssLst);
		} else {
			wssLst.add(wss);
		}

		//向关联的页面发送一条信息
		wss.sendMessage(new TextMessage(""));
	}

	/**
	 * 后台消息处理
	 */
	@Override
	public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
		TextMessage returnMessage = new TextMessage(wsm.getPayload() + " received at server");

		//向所有的页面发送一条信息
		broadcast(returnMessage);
	}

	/**
	 * 处理异常，当异常发生的时候关闭websocket连接
	 */
	@Override
	public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
		removeClosedWss(wss);
		System.out.println("websocket connection closed......");
	}

	/**
	 * 连接关闭的时候
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
		System.out.println("websocket connection closed......");
		removeClosedWss(wss);
	}

	private void removeClosedWss(WebSocketSession wss) throws IOException {
		if (wss.isOpen()) {
			wss.close();
		}

		//移除已经关闭的连接 
		String uid = (String) wss.getAttributes().get("uid");
		List<WebSocketSession> wssLst = visaInfoSocketSessionMap.get(uid);
		if (Util.isEmpty(wssLst)) {
			wssLst = Lists.newArrayList();
			wssLst.add(wss);
			visaInfoSocketSessionMap.put(uid, wssLst);
		} else {
			wssLst.remove(wss);
		}
	}

	/**
	* 给所有在线用户发送消息
	* 
	* @param message
	* @throws IOException
	*/
	public void broadcast(final TextMessage message) throws IOException {
		Set<String> keySet = visaInfoSocketSessionMap.keySet();
		if (!Util.isEmpty(keySet)) {
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String nextJ = iter.next();
				List<WebSocketSession> lst = visaInfoSocketSessionMap.get(nextJ);
				if (!Util.isEmpty(lst)) {
					//多线程群发  TODO
					for (WebSocketSession wss : lst) {
						synchronized (wss) {
							if (wss.isOpen()) {
								wss.sendMessage(message);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public static Map<String, List<WebSocketSession>> getUsersocketsessionmap() {
		return visaInfoSocketSessionMap;
	}

}
