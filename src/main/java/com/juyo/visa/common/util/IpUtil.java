/**
 * IpUtil.java
 * com.xiaoka.core.common.util
 * Copyright (c) 2014, 北京聚智未来直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uxuexi.core.common.util.CollectionUtil;

/**
 * 获取ip地址的工具类
 * 
 * @author   朱晓川
 * @Date	 2014-07-18	 
 * @version  0.0.1
 */
public class IpUtil {
	/**
	* Logger for this class
	*/
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	private static Enumeration<NetworkInterface> allNetInterfaces = null;

	/**
	 * 获取本地ip地址列表
	 * 
	 * @return ip列表
	 */
	public static List<String> getAllLocalIP() {
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			logger.info("获取ip地址失败", e); //$NON-NLS-1$\
			return CollectionUtil.list();
		}
		InetAddress ip = null;
		List<String> ips = CollectionUtil.list();
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = addresses.nextElement();
				if (ip == null) {
					continue;
				}
				if (!(ip instanceof Inet4Address)) {
					continue;
				}
				if ("127.0.0.1".equals(ip.getHostAddress()) || "0.0.0.0".equals(ip.getHostAddress())) {
					continue;
				}
				ips.add(ip.getHostAddress());
			}
		}
		return ips;
	}

	public static String getIpAddr(HttpServletRequest req) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = req.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = req.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = req.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 判断本机端口是否被占用
	 *
	 * @param port 端口
	 * @return 是否被占用
	 */
	public static boolean isPortUsed(final int port) {
		try {
			bindPort("0.0.0.0", port);
			List<String> ips = getAllLocalIP();
			for (String ip : ips) {
				bindPort(ip, port);
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	/**
	 * 绑定端口，如果被占用则会提示异常
	 *
	 * @param host 地址
	 * @param port 端口
	 * @throws IOException 如果被占用则提示异常
	 */
	private static void bindPort(final String host, final int port) throws IOException {
		Socket s = new Socket();
		s.bind(new InetSocketAddress(host, port));
		s.close();
	}

}
