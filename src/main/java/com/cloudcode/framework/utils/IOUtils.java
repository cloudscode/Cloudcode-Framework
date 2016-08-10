package com.cloudcode.framework.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
	private static Logger log = LoggerFactory.getLogger(IOUtils.class);

	public static void close(InputStream is) {
		if (is != null)
			try {
				is.close();
			} catch (IOException localIOException) {
			}
	}

	public static void close(FileChannel fc) {
		if (fc != null)
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void close(OutputStream os) {
		if (os != null)
			try {
				os.close();
			} catch (IOException localIOException) {
			}
	}

	public static void close(Reader in) {
		if (in != null)
			try {
				in.close();
			} catch (IOException localIOException) {
			}
	}

	public static void close(Writer out) {
		if (out != null)
			try {
				out.close();
			} catch (IOException localIOException) {
			}
	}

	public static void close(RandomAccessFile mm) {
		if (mm != null)
			try {
				mm.close();
			} catch (IOException localIOException) {
			}
	}

	public static int streamSize = 1024;

	public static byte[] streamToByte(InputStream inputStream) {
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(inputStream);
			out = new ByteArrayOutputStream(streamSize);
			byte[] temp = new byte[streamSize];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
			IOUtils.close(inputStream);
			IOUtils.close(in);
		}
		return null;
	}

	public static boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(mm);
		}
		return flag;
	}

	public static void copyAndCloseIOStream(OutputStream os, InputStream in) {
		try {
			copyIOStream(os, in);
		} catch (IOException e) {
		} finally {
			IOUtils.close(in);
			IOUtils.close(os);
		}
	}
	public static void copyAndCloseIOStream(Writer writer, Reader reader) {
		try {
			copyIOStream(writer, reader);
		} catch (IOException e) {
		} finally {
			IOUtils.close(writer);
			IOUtils.close(reader);
		}
	}
	public static void copyIOStream(OutputStream os, InputStream in)
			throws IOException {
		int len = 0, bufferSize = 8192;
		byte[] buffer = new byte[bufferSize];
		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
	}
	public static void copyIOStream(Writer writer, Reader reader)
			throws IOException {
		int len = 0, bufferSize = 8192;
		char[] buffer = new char[bufferSize];
		while ((len = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, len);
		}
	}
}
