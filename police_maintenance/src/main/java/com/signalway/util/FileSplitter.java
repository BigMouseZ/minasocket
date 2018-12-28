package com.signalway.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Keven split a src-filt into sevral small pieces. The src-file's size
 * if required to be smaller than miPartSize * Integer.MAX_VALUE
 */
public class FileSplitter {

    private final String msSrcFile;
    private int miPartSize = 1024 * 1024; //1M
    private RandomAccessFile rf = null;

    /**
     * Constructor
     * @param psSrcFile
     */
    public FileSplitter(String psSrcFile) {
        msSrcFile = psSrcFile;
    }
    /**
     * Start splitting
     * @return the array of the names of split-out files
     * @throws IOException
     */
    public List<byte[]> start() throws IOException {
        File loFile = new File(msSrcFile);
        if (!loFile.exists()) {
            throw new IOException("Src-File not found:" + msSrcFile);
        }
        long liFileLen = loFile.length();
        long liPartCnt = liFileLen / miPartSize + (liFileLen % miPartSize == 0 ? 0 : 1);
        if (liPartCnt > Integer.MAX_VALUE) {
            throw new IOException("Src-File too large");
        }
        rf = new RandomAccessFile(msSrcFile,"r");
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < (int) liPartCnt; i++) {
          //  moThrPool.execute();
            byte[] buff = SplitThread(i, i == liPartCnt - 1 ? liFileLen % miPartSize : miPartSize);
            list.add(buff);
        }
        return list;
    }
    /**
     * Splitting Thread
     */
    private byte[] SplitThread(int miPartIndex, long miSize) {
            byte[] buffer = null;
            try {
                buffer = new byte[(int) miSize];
                // Input Map
                rf.seek(miPartSize * miPartIndex);
                rf.read(buffer, 0, (int) miSize);
            } catch (IOException ex) {
                System.out.print("part_index:" + miPartIndex + " boom");
                Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        return buffer;
    }
}
