package com.signalway.cachemanage;

import com.signalway.engine.ReponseQueue;
import com.signalway.entity.fileentity.FileByteInfo;
import com.signalway.entity.fileentity.ReturnFileInfo;
import com.signalway.entity.minacode.DwDataInfo;
import com.signalway.entity.minacode.MsgPack;
import com.signalway.util.SpringContextUtils;
import com.signalway.util.fastdfs.FastFileUtil;
import com.signalway.util.json.JsonUtils;
import com.signalway.util.manage.SessionIDListMap;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ZhangGang on 2018/7/10.
 *
 */
public class CacheManageCentre {
    /**
     * 引入日志，注意都是"org.slf4j"包下
     */
    private  static final Logger logger = LoggerFactory.getLogger(CacheManageCentre.class);
    private FastFileUtil fastFileUtil = (FastFileUtil) SpringContextUtils.getBean(FastFileUtil.class);
   /**
    * @param session 当前长连接session
    * @param cutPartInfo 片段
    * @Description 功能：累计切片信息，直至满足条件输出
    **/
    public  void accumulateCutPart(IoSession session,MsgPack cutPartInfo){
        //累计上传的文件切片
        /*1、根据sessionid创建缓存，存在则忽略*/
        if(SessionIDListMap.instance().get(session.getId()+"") ==null){
            //不存在缓存这创建
            SessionIDListMap.instance().put(session.getId()+"",new ArrayList<>());
        }
        /*2、解析文件，存入缓存*/
        if(SessionIDListMap.instance().get(session.getId()+"") !=null){
            DwDataInfo dwDataInfo = null;
            if(cutPartInfo.getMsgJsonPack() !=null){
                dwDataInfo = JsonUtils.ConvertToObject(cutPartInfo.getMsgJsonPack(),DwDataInfo.class);
            }
            byte[] data = cutPartInfo.getData();
            FileByteInfo fileByteInfo = new FileByteInfo();
            if(dwDataInfo !=null){
                fileByteInfo.setAllFrameNum(dwDataInfo.getContent().getAllFrameNum());
                fileByteInfo.setFrameSN(dwDataInfo.getContent().getFrameSN());
            }
            fileByteInfo.setData(data);
            SessionIDListMap.instance().get(session.getId()+"").add(fileByteInfo);
            String number = String.valueOf(SessionIDListMap.instance().get(session.getId()+"").size());
            /*3、判断文件片段是否完整传输完毕*/
            if(number.equals(dwDataInfo.getContent().getAllFrameNum())){
                //文件片段发送完毕。
                List<FileByteInfo> list = SessionIDListMap.instance().get(session.getId()+"");
                List<byte[]> imageByteArrayList = list.stream().map(FileByteInfo::getData).collect(Collectors.toList());
                byte[] allByte = byteMergerAll(imageByteArrayList);
                String filePath = fastFileUtil.uploadByteFile(allByte,dwDataInfo.getContent().getFileName());
              //  String imageBase64 = ImageUtils.encodeImage(allByte);
                ReturnFileInfo returnFileInfo = new ReturnFileInfo();
                returnFileInfo.setFileName(dwDataInfo.getContent().getFileName());
                returnFileInfo.setFilePath(filePath);
                //将结果当如阻塞队列
                logger.info("存储的sessionid:"+session.getId()+"===="+"返回文件结果："+returnFileInfo.toString());
                ReponseQueue.setQueueValue(session.getId()+"",returnFileInfo);
                //移除缓存
                SessionIDListMap.instance().remove(session.getId()+"");
                logger.info("文件流转成功！");
            }

        }

    }

    /**
     * @param values 入参
     * @Description 功能：返回一个byte[]
     * @return allByte
     **/
    private static byte[] byteMergerAll(List<byte[]> values) {
        int lengthByte = 0;
        for (int i = 0; i < values.size(); i++) {
            lengthByte += values.get(i).length;
        }
        byte[] allByte = new byte[lengthByte];
        int countLength = 0;
        for (int i = 0; i < values.size(); i++) {
            byte[] b = values.get(i);
            System.arraycopy(b, 0, allByte, countLength, b.length);
            countLength += b.length;
        }
        return allByte;
    }
}
