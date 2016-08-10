package com.cloudcode.framework.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;



/**
 * 解压zip文件
 * 压缩zip文件
 **/
public class ZIPUtil {
	private static final Logger logger = Logger.getLogger(ZIPUtil.class);
	static void createDir(String path) {
		File dir = new File(path);
		if (dir.exists() == false)
			dir.mkdir();
	}
	static String getSuffixName(String name) {
		return name.substring(0, name.lastIndexOf("."));
	}
	public static void main(String[] args) throws Exception {
		/**
		 * 压缩文件zip
		 */
		// File[] files = new File[] { new File("E:\\logs") };
		// File zip = new File("E:\\logs.zip");
		// ZipFiles(zip, null, files);
		// logger.info("ok!");

		unzip("C:\\test2\\as.zip",
				"C:\\test2\\");
		logger.info("ok!");
	}
	public static void unzip(String zipFilePath, String unzipDirectory)
			throws Exception {
		// 创建文件对象
		File file = new File(zipFilePath);
		unzip(file, unzipDirectory);
	}
	public static void ZipFiles(File zip, String path, File... srcFiles) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zip));
			ZIPUtil.ZipFiles(out, path, srcFiles);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
		logger.info("*****************压缩完毕*******************");
	}
	
	public static void ZipFiles(File zip, String path, List<File> srcFiles) {

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zip));
			ZIPUtil.ZipFiles(out, path, srcFiles);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}

		logger.info("*****************压缩完毕*******************");
	}

	public static void ZipFiles(ZipOutputStream out, String path,
			File... srcFiles) {
		List<File> Files = new ArrayList<File>();
		for (File file : srcFiles) {
			Files.add(file);
		}
		ZipFiles(out, path, Files);
	}

	public static void ZipFiles(ZipOutputStream out, String path, List<File> srcFiles) {
		if (path != null) {
			path = path.replaceAll("\\*", "/");
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "";
		}
		byte[] buf = new byte[1024];
		try {
			for (int i = 0; i < srcFiles.size(); i++) {
				File file_ = srcFiles.get(i);
				if (file_.isDirectory()) {
					File[] files = file_.listFiles();
					String srcPath = file_.getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if (!srcPath.endsWith("/")) {
						srcPath += "/";
					}
					out.putNextEntry(new ZipEntry(path + srcPath));
					ZipFiles(out, path + srcPath, files);
				} else {
					FileInputStream in = null;
					try {
						in = new FileInputStream(file_);
						logger.info(path + file_.getName());
						out.putNextEntry(new ZipEntry(path
								+ file_.getName()));
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						out.closeEntry();
						IOUtils.close(in);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzip(File file, String unzipDirectory)
			throws IOException {
		// 创建zip文件对象
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file,"gbk");
			// 创建本zip文件解压目录
			// File unzipFile = new File(unzipDirectory + "/" +
			// getSuffixName(file.getName()));
			File unzipFile = new File(unzipDirectory);
			if (unzipFile.exists())
				unzipFile.delete();
			unzipFile.mkdir();
			// 得到zip文件条目枚举对象
			Enumeration zipEnum = zipFile.getEntries();
			// 定义输入输出流对象
			// 定义对象
			ZipEntry entry = null;
			// 循环读取条目
			while (zipEnum.hasMoreElements()) {
				// 得到当前条目
				entry = (ZipEntry) zipEnum.nextElement();				
				String entryName = new String(entry.getName());
				// 用/分隔条目名称
				String names[] = entryName.split("\\/");
				int length = names.length;
				String path = unzipFile.getAbsolutePath();
				for (int v = 0; v < length; v++) {
					if (v < length - 1) { // 最后一个目录之前的目录
						path += "/" + names[v] + "/";
						createDir(path);
					} else { // 最后一个
						if (entryName.endsWith("/")) // 为目录,则创建文件夹
							createDir(unzipFile.getAbsolutePath() + "/"
									+ entryName);
						else { // 为文件,则输出到文件
							InputStream input = null;
							OutputStream output = null;
							try {
								input = zipFile.getInputStream(entry);
								output = new FileOutputStream(new File(
										unzipFile.getAbsolutePath() + "/"
												+ entryName));
								byte[] buffer = new byte[1024 * 8];
								int readLen = 0;
								while ((readLen = input.read(buffer, 0,
										1024 * 8)) != -1)
									output.write(buffer, 0, readLen);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								// 关闭流
								IOUtils.close(input);
								output.flush();
								IOUtils.close(output);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null !=zipFile)
			zipFile.close();
		}
	}
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}