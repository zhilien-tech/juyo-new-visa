/**
 * ZipFile.java
 * com.linyun.airline.common.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.io.InputStream;

import lombok.Data;

/**
 * 用于zip打包的辅助bean
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年3月4日 	 
 */
@Data
public class ZFile {

	/**文件名*/
	private String fileName;

	/**文件输入流(文件内容)*/
	private InputStream input;

	/**文件在zip包中的相对路径*/
	private String relativePathInZip;

}
