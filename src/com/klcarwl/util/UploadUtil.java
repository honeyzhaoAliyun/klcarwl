package com.klcarwl.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

public class UploadUtil {
	
	public static String uploadUtil(String dirPath,File file,String fileName){
		String rename = "";
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		if(!("".equals(fileName)) && fileName != null){
			rename = UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
			File newFile = new File(dir, rename);
			try {
				FileUtils.copyFile(file, newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return rename;
		}else{
			return null;
		}
	}

}
