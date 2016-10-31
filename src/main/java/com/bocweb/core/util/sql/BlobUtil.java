package com.bocweb.core.util.sql;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
/**
 * Blob类型的字段处理工具类
 * @author tongpuxin
 */
public class BlobUtil {

	public static String toString(Blob blob) {
		String result = null;
		if(blob == null)return result;
		try {
			result = new String(blob.getBytes(1L, Long.valueOf(blob.length()).intValue()));
		} catch (Exception e) {
			try {
				InputStream is = blob.getBinaryStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				for (int count; (count = is.read(buffer)) != -1;) {
					out.write(buffer, 0, count);
				}
				result = new String(out.toByteArray());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
}
