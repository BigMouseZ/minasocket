package com.signalway.entity.fileentity;

/**
 * Created by ZhangGang on 2018/7/6.
 */
public class FileCutPartInfo {
    private String FileName; //文件名,
    private String SourcePath; //文件来源路径,
    private String DestPath; //文件存储路径,
    private String FileMd5; //文件MD5,
    private String FrameMd5; //该帧MD5,
    private int AllFrameNum; //文件被拆成的帧总数,
    private int FrameSN; //该帧序号,

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSourcePath() {
        return SourcePath;
    }

    public void setSourcePath(String sourcePath) {
        SourcePath = sourcePath;
    }

    public String getDestPath() {
        return DestPath;
    }

    public void setDestPath(String destPath) {
        DestPath = destPath;
    }

    public String getFileMd5() {
        return FileMd5;
    }

    public void setFileMd5(String fileMd5) {
        FileMd5 = fileMd5;
    }

    public String getFrameMd5() {
        return FrameMd5;
    }

    public void setFrameMd5(String frameMd5) {
        FrameMd5 = frameMd5;
    }

    public int getAllFrameNum() {
        return AllFrameNum;
    }

    public void setAllFrameNum(int allFrameNum) {
        AllFrameNum = allFrameNum;
    }

    public int getFrameSN() {
        return FrameSN;
    }

    public void setFrameSN(int frameSN) {
        FrameSN = frameSN;
    }
}
