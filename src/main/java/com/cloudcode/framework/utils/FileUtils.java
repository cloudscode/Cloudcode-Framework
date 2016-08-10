package com.cloudcode.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	public static void copyFile(File sourceFile, File targetFile) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(sourceFile);
			fo = new FileOutputStream(targetFile);
			in = fi.getChannel();
			out = fo.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("FileUtils文件复制失败：" + e.getMessage());
		} finally {
			IOUtils.close(fi);
			IOUtils.close(in);
			IOUtils.close(out);
			IOUtils.close(fo);
		}
	}
	public static void mkdirs(File targetFile) {
		if(!targetFile.exists()){
			File file = targetFile.getParentFile();
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}
	public static void mkdirs(String targetFilePath) {
		File file = new File(targetFilePath);
		mkdirs(file);
	}
}
