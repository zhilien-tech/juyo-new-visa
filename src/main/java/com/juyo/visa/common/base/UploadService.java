package com.juyo.visa.common.base;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public interface UploadService {
	
	/**
	 * 上传图片
	 * @param inStream   文件流  
	 * @param file_ext_name   文件扩展名
	 * @param dest_filename   指定文件名 
	 * @return
	 */
	public String uploadImage(InputStream inStream, String file_ext_name, String dest_filename) ;
	
	
	/**用于ajax上传图片，返回地址*/
	public Map<String,Object> ajaxUploadImage(final File image) ; 

}
