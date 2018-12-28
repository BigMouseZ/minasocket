/**
 * The MIT License
 *
 * Copyright (c) 2010-2012 www.myjeeva.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE. 
 * 
 */
package com.signalway.util;

import com.signalway.util.common.FileBase;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.UUID;

/**
 * @desc Image manipulation - Conversion
 * 
 * @filename ImageManipulation.java
 * @author <a href="mailto:jeeva@myjeeva.com">Jeevanandam Madanagopal</a>
 * @copyright &copy; 2010-2012 www.myjeeva.com
 */
public class ImageUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("E:\\Downloads\\1120161226155630.png");
		try {
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int)file.length()];
			imageInFile.read(imageData);
			String imageDataString = encodeImage(imageData);
			byte[] imageByteArray = decodeImage(imageDataString);
			//Base64ToImage(imageDataString,"testdir",null);
			System.out.println("Image Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
	}
	
	/**
	 * Encodes the byte array into base64 string
	 * @param imageByteArray - byte array
	 * @return String a {@link String}
	 */
	public static String encodeImage(byte[] imageByteArray){

		return Base64.encodeBase64URLSafeString(imageByteArray);

	}

	/**
	 * Decodes the base64 string into byte array
	 * @param imageDataString - a {@link String}
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {

		return Base64.decodeBase64(imageDataString);
	}
	
	/**
	 * 将Base64字符串转成图片并保存到指定目录中
	 * @param imageDataString
	 * @param relativeDir 目录
	 * @param suffix 后缀
	 * @return
	 * @throws IOException
	 */
	public static String Base64ToImage(String imageDataString, String relativeDir,String suffix) throws IOException {
		String fileName = UUID.randomUUID().toString();
		fileName = fileName.concat(suffix!=null? suffix : ".png"); 
		byte[] imageByteArray = decodeImage(imageDataString);
		InputStream input = new ByteArrayInputStream(imageByteArray);
		FileBase.saveFile(input, relativeDir, fileName);
		return relativeDir.concat( "/" ).concat( fileName );
	}
	/**
	 * 将byte[]字符串转成图片并保存到指定目录中
	 * @param imageDataString
	 * @param relativeDir 目录
	 * @param suffix 后缀
	 * @return
	 * @throws IOException
	 */
	public static String ByteToImage(byte[] imageByteArray, String relativeDir,String suffix) throws IOException {
		String fileName = UUID.randomUUID().toString();
		fileName = fileName.concat(suffix!=null? suffix : ".png");
		InputStream input = new ByteArrayInputStream(imageByteArray);
		FileBase.saveFile(input, relativeDir, fileName);
		return relativeDir.concat( "/" ).concat( fileName );
	}
}
