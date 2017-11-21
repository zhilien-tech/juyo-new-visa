/**
 * TestDownload.java
 * com.juyo.visa
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2017年11月21日 	 
 */
public class TestDownload {

	public TestDownload() {
	}

	public static boolean saveUrlAs(String fileUrl, String savePath)/* fileUrl网络资源地址 */
	{

		try {
			/* 将网络资源地址传给,即赋值给url */
			URL url = new URL(fileUrl);

			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());

			/* 此处也可用BufferedInputStream与BufferedOutputStream  需要保存的路径*/
			DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));

			/* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
			{
				out.write(buffer, 0, count);
			}
			out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
			in.close();
			connection.disconnect();
			return true;/* 网络资源截取并存储本地成功返回true */

		} catch (Exception e) {
			System.out.println(e + fileUrl + savePath);
			return false;
		}
	}

	public static void main(String[] args) {
		TestDownload pic = new TestDownload();/* 创建实例 */

		//需要下载的URL
		String photoUrl = "http://oyu1xyxxk.bkt.clouddn.com/93fdb832-45b1-4fa6-b739-482738878628.jpg";

		// 截取最后/后的字符串
		String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));

		//图片保存路径
		String filePath = "E:";

		/* 调用函数，并且进行传参 */
		boolean flag = pic.saveUrlAs(photoUrl, filePath + fileName);

		System.out.println("Run ok!\n Get URL file " + flag);
		System.out.println(filePath);
		System.out.println(fileName);
	}
}
