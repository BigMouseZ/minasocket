package com.signalway.util.common;

import com.signalway.util.RabbitMQUtils;
import com.signalway.util.SpringContextUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Weign
 *
 */
public class FileBase {

	private static ContextBase contextBase = (ContextBase) SpringContextUtils.getBean(ContextBase.class);
	public FileBase(){}
	
	/**
	 * springmvc专用的保存文件上传
	 * @param mFile 
	 * @param relativeDir 不能为空。保存路径，此路径相对于webroot
	 * @param newfileName 可以为空。定义保存后，承现的文件名。如果不带有后缀名，将使用上传源文件的后缀名。
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static boolean saveFile(MultipartFile mFile, String relativeDir, String newfileName) throws IllegalStateException, IOException {
		String fileDir = createDir(relativeDir);

		if (newfileName != null) {
			if (FilenameUtils.getExtension(newfileName) == "") {
				newfileName = newfileName.concat(".").concat(FilenameUtils.getExtension(mFile.getOriginalFilename()));
			}
		} else {
			newfileName = mFile.getOriginalFilename();
		}

		String newFileFullName = FilenameUtils.concat(fileDir, newfileName);
		mFile.transferTo(new File(newFileFullName));

		return true;
	}
	
	/**
	 * 保存文件
	 * 
	 * @param input
	 *            想要保存的源文件全路径
	 * @param newFileFullName
	 *            想要保存的目标文件全路径
	 * @return 成功返回true,失败则抛出IOException
	 * @throws IOException
	 */
	public static void saveFile(String sourceFileFullPath, String targetFileFullPath) throws IOException{
		FileInputStream input = FileUtils.openInputStream(new File(sourceFileFullPath));
		OutputStream output =FileUtils.openOutputStream(new File(targetFileFullPath));
		saveFile(input,output);
	}
	
	/**
	 * 保存文件
	 * @param input	想要保存的文件流
	 * @param relativeDir 不能为空。保存路径，此路径相对于webroot
	 * @param newfileName 不能为空。定义保存后，承现的文件名。必须带有后缀名。
	 * @return  成功返回true,失败则抛出IOException
	 * @throws IOException
	 */
	public static boolean saveFile(InputStream input, String relativeDir, String newfileName) throws IOException {
		String fileDir = createDir(relativeDir);
		String newFileFullName = FilenameUtils.concat(fileDir, newfileName);
		saveFile(input, newFileFullName);
		return true;
	}
	
	/**
	 * 保存文件
	 * @param input	想要保存的文件流
	 * @param newFileFullName 不能为空。定义要保存的完全路径名。
	 * @return  成功返回true,失败则抛出IOException
	 * @throws IOException
	 */
	public static void saveFile(InputStream input, String fileFullPath) throws IOException {
		OutputStream output = FileUtils.openOutputStream(new File(fileFullPath));
		saveFile(input, output);
	}
	
	/**
	 * 保存文件
	 * @param input 想要保存的源文件流
	 * @param output 想要保存的目标文件流
	 * @throws IOException
	 */
	public static void saveFile(InputStream input, OutputStream output) throws IOException {
		IOUtils.copy(input, output);
		try {
			output.close();
			input.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 创建文件保存的目录，此目录相对于webroot。先查看目录是否已经存在，如果不存在，则创建。
	 * @param dir
	 * @return
	 * @throws IOException 
	 */
	public static String createDir(String dir) throws IOException {
		String webRootPath = contextBase.getWebRootAbsolutePath();

		File dirPath = new File(webRootPath + dir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		return dirPath.getCanonicalPath();
	}
	
	/**
	 * 取得此目录相对于webRoot的绝对路径，如果此路径不存在此创建此目录
	 * @param filePath 文件相对路径, 文件路径/文件夹路径
	 * @return 绝对路径
	 * @throws IOException 
	 */
	public static String getAbsoluteDir(String file) throws IOException {
		String webRootPath = new ContextBase().getWebRootAbsolutePath();
		//String webRootPath = "";
		String filePath = null;
		
		// 检查目录是否存在,不存在则创建
		if(file.indexOf(".")!=-1){
			int lenth = file.lastIndexOf("/")==-1?(file.lastIndexOf("\\")!=-1?file.lastIndexOf("\\"):0):file.lastIndexOf("/");
			filePath = file.substring(0, lenth);			
		}else{
			filePath = file;
		}
		File dirPath = new File(webRootPath + filePath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		File fileFullPath = new File(webRootPath + file);
		
		return fileFullPath.getCanonicalPath();
	}
	
	public static void main(String[] args){
		try {
			System.out.println(getAbsoluteDir("c:/thum1/thum1pro_fl.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
