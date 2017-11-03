/**
 * ZipUtil.java
 * com.xiaoka.test.mail
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uxuexi.core.common.util.Util;

/**
 * zip打包工具
 * @author   朱晓川
 */
public class ZipUtils {

	private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * @param files              文件数组 
	 * @param zos                ZIP输出流
	 */
	public static void zipFile(ZFile[] files, ZipOutputStream zos) throws IOException {
		List<ZFile> fileList = Arrays.asList(files);
		zipFile(fileList, zos);
	}

	/**
	 * @param files              文件集合
	 * @param zos                ZIP输出流
	 */
	public static void zipFile(List<ZFile> files, ZipOutputStream zos) throws IOException {
		if (Util.isEmpty(files)) {
			logger.info("没有需要zip压缩的文件");
			return;
		}
		for (ZFile zf : files) {
			InputStream input = zf.getInput();
			if (Util.isEmpty(input)) {
				logger.info("文件内容为空，忽略...");
				continue;
			}
			String fName = zf.getFileName();
			if (Util.isEmpty(fName)) {
				logger.info("文件名为空，忽略...");
				continue;
			}

			try {
				zos.putNextEntry(new ZipEntry(zf.getRelativePathInZip() + fName));
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = input.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
				input.close();
				buffer = null;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
