package io.github.chihqq.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * Created by Administrator on 2022/7/31 is 12:17.
 *
 * @Description
 * @Author sznzn <sznzn614866450@outlook.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/7/31
 */

public class Base64Util {


	/**
	 * 将图片转换成Base64编码
	 *
	 * @param imgFile 待处理图片地址
	 * @return
	 */
	public static String getImgBase(String imgFile) {

		// 将图片文件转化为二进制流
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = Files.newInputStream(Paths.get(imgFile));
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(data);
	}


	public static String getImgBaseWithHead(String imgFile) {
		String imgBase = getImgBase(imgFile);
		// 图片头
		String imghead = "data:image/jpeg;base64,";
		return imghead + imgBase;
	}


}
