package com.signalway.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Keven
 */
public class FileUnifier {
    private final String msDstFile;
    private List<byte[]> list;

    /**
     * Constructor
     *
     * @param psList
     * @param psDstFile
     */
    public FileUnifier(List<byte[]> psList, String psDstFile) {
        list = psList;
        msDstFile = psDstFile;
    }

    /**
     * @throws IOException io异常
     * @Description 功能：拆分文件，暂未启用此方法
     **/
    public void start() throws IOException {
        //写入大文件的输出流
        File file2 = new File(msDstFile);
        if (file2.exists()) {//如果同名文件存在，先删除在合并
            file2.delete();
        }
        for (byte[] outByte : list) {
            try {
                OutputStream out = new FileOutputStream(file2, true);
                out.write(outByte);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      //  System.out.println("文件合并完成！");
    }
}
