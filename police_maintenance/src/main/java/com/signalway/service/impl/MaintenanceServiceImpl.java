package com.signalway.service.impl;

import com.signalway.config.CommandConstant;
import com.signalway.engine.ReponseQueue;
import com.signalway.entity.BasicReturn;
import com.signalway.entity.ErrorCode;
import com.signalway.entity.fileentity.ReturnFileInfo;
import com.signalway.entity.minacode.DwDataContent;
import com.signalway.entity.minacode.DwDataInfo;
import com.signalway.entity.minacode.DwInfoContent;
import com.signalway.entity.minacode.DwJsonInfo;
import com.signalway.entity.minacode.HeaderInfo;
import com.signalway.entity.minacode.MsgPack;
import com.signalway.entity.minacode.NetInfoType;
import com.signalway.entity.questentity.SendCommandPojo;
import com.signalway.entity.questentity.SendFilePojo;
import com.signalway.entity.rabbitmq.ReturnQueuingObject;
import com.signalway.service.MaintenanceService;
import com.signalway.util.fastdfs.FastFileUtil;
import com.signalway.util.json.GsonUtil;
import com.signalway.util.manage.CarIDSessionIDMap;
import com.signalway.util.manage.SocketSessionMap;
import net.sf.json.JSONObject;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.signalway.util.json.JsonUtils.ConvertToString;

/**
 * Created by ZhangGang on 2018/6/28.
 */
@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceServiceImpl.class);
    private static final String CHARSETNAME = "UTF-8";
    private static final String CONTENT = "Content";
    private static final String RETCODE = "RetCode";
    private static final String REPORT = "Report";

    @Autowired
    private FastFileUtil fastFileUtil;

    /*1、根据警察id查询对应socket session*/
    /*2、组织下发信息*/
    /*3、下发消息到客户端*/
    /*4、阻塞等待文件或图片返回*/

    /**
     * @param info
     * @return String
     * @Description 功能：
     **/
    @Override
    public String GetOBCFile(SendCommandPojo info) {
        String socketSessionID = CarIDSessionIDMap.instance().get(info.getPolicecarid());
        try {
            ReturnQueuingObject ro = new ReturnQueuingObject();
            if (socketSessionID == null) {
                //警车失联
                failureReturnWithoutConnect(ro, info.getPolicecarid());
                return GsonUtil.createGsonString(ro);
            }
            IoSession session = SocketSessionMap.instance().get(socketSessionID);
            if (session != null) {
                if (!ReponseQueue.isExiststQueueValue(socketSessionID)) {
                    ReponseQueue.setQueue(socketSessionID);
                }
                getOBCFileProcess(session, info, socketSessionID,ro);
            } else {
                failureReturnWithoutConnect(ro, info.getPolicecarid());
            }
            return GsonUtil.createGsonString(ro);
        } catch (Exception ex) {
            logger.error("获取文件失败,失败原因：" + ex.getMessage(), ex);
            if (ReponseQueue.isExiststQueueValue(socketSessionID)) {
                ReponseQueue.destroy(socketSessionID);
            }
            return BasicReturn.ReturnEx(ex);
        }
    }

    @Override
    public String SendCommand(SendCommandPojo info) {
        String socketSessionID = CarIDSessionIDMap.instance().get(info.getPolicecarid());
        try {
            ReturnQueuingObject ro = new ReturnQueuingObject();
            if (socketSessionID == null) {
                //警车失联
                failureReturnWithoutConnect(ro, info.getPolicecarid());
            } else {
                IoSession session = SocketSessionMap.instance().get(socketSessionID);
                if (session != null) {
                    logger.info("警车:" + info.getPolicecarid() + "的客户端连接正常");
                    if (!ReponseQueue.isExiststQueueValue(socketSessionID)) {
                        ReponseQueue.setQueue(socketSessionID);
                    }
                    HeaderInfo headerInfo = new HeaderInfo(NetInfoType.NET_STRING.getIndex(), 0, 0);
                    //命令json串
                    String json = creatJsonInfo(info);
                    headerInfo.setDwInfoLen(json.getBytes(CHARSETNAME).length);
                    logger.info("下发的头文件：" + headerInfo.toString());
                    logger.info("下发的json：" + json);
                    //第一次先发送头文件，在发送命令json串--发送消息到客户端
                    session.write(headerInfo);
                    session.write(json);
                    logger.info("下发完成");
                    //第一次阻塞等待结果
                    Object reObject = ReponseQueue.takeQueueValue(socketSessionID);
                    if (reObject instanceof MsgPack) {
                        MsgPack msgPack = (MsgPack) reObject;
                        String msgJsonPack = msgPack.getMsgJsonPack();
                        JSONObject jsonObject = JSONObject.fromObject(msgJsonPack);
                        String content = jsonObject.get(CONTENT).toString();
                        JSONObject contentObject = JSONObject.fromObject(content);
                        String retCode = contentObject.get(RETCODE).toString();
                        if ("-1".equals(retCode)) {
                            //获取失败
                            ro.setErrorcode(ErrorCode.Failure);
                            ro.setMessage("下发命令失败！");
                            ro.setData("");
                        } else if ("0".equals(retCode)) {
                            //获取失败
                            ro.setErrorcode(ErrorCode.Success);
                            ro.setMessage("下发命令成功！");
                            ro.setData(contentObject.get(REPORT).toString());
                        }
                        ReponseQueue.destroy(socketSessionID);
                        logger.info("命令下发返回结果：" + msgPack.toString());
                    } else if (reObject instanceof String) {
                        //获取失败
                        ro.setErrorcode(ErrorCode.Failure);
                        ro.setMessage("下发命令失败，等待超时");
                        ro.setData("");
                        ReponseQueue.destroy(socketSessionID);
                        logger.warn("下发命令失败，等待超时");
                        return GsonUtil.createGsonString(ro);
                    }
                } else {
                    failureReturnWithoutConnect(ro, info.getPolicecarid());
                }
            }
            return GsonUtil.createGsonString(ro);
        } catch (Exception ex) {
            logger.error("下发命令失败,失败原因：" + ex.getMessage(), ex);
            if (ReponseQueue.isExiststQueueValue(socketSessionID)) {
                ReponseQueue.destroy(socketSessionID);
            }
            return BasicReturn.ReturnEx(ex);
        }
    }

    @Override
    public String SendFile(SendFilePojo info) {
        String socketSessionID = CarIDSessionIDMap.instance().get(info.getPolicecarid());
        try {
            ReturnQueuingObject ro = new ReturnQueuingObject();
            if (socketSessionID == null) {
                //警车失联
                failureReturnWithoutConnect(ro, info.getPolicecarid());
            } else {
                IoSession session = SocketSessionMap.instance().get(socketSessionID);
                if (session != null) {
                    logger.info("警车id:" + info.getPolicecarid() + "的客户端连接正常");
                    if (!ReponseQueue.isExiststQueueValue(socketSessionID)) {
                        ReponseQueue.setQueue(socketSessionID);
                    }
                    //获取文件。
                    String filePath = info.getFilePath();
                    byte[] fileByte = fastFileUtil.downloadByteFile(filePath);
                    HeaderInfo headerInfo = new HeaderInfo(NetInfoType.NET_FILE.getIndex(), 0, 0);
                    //命令json串
                    String json = creatSendFileInfo(info);
                    headerInfo.setDwInfoLen(json.getBytes(CHARSETNAME).length);
                    headerInfo.setDwDataLen(fileByte.length);
                    //第一次先发送头文件，在发送命令json串--发送消息到客户端
                    logger.info("下发文件json：" + json);
                    session.write(headerInfo);
                    session.write(json);
                    session.write(fileByte);
                    //第一次阻塞等待结果
                    Object reObject = ReponseQueue.takeQueueValue(socketSessionID);
                    if (reObject instanceof MsgPack) {
                        MsgPack msgPack = (MsgPack) reObject;
                        String msgJsonPack = msgPack.getMsgJsonPack();
                        JSONObject jsonObject = JSONObject.fromObject(msgJsonPack);
                        String content = jsonObject.get(CONTENT).toString();
                        JSONObject contentObject = JSONObject.fromObject(content);
                        String retCode = contentObject.get(RETCODE).toString();
                        if ("-1".equals(retCode)) {
                            //获取失败
                            ro.setErrorcode(ErrorCode.Failure);
                            ro.setMessage("下发文件失败!");
                            ro.setData(contentObject.get(REPORT).toString());
                        } else if ("0".equals(retCode)) {
                            //获取失败
                            ro.setErrorcode(ErrorCode.Success);
                            ro.setMessage("下发文件成功!");
                            ro.setData(contentObject.get("DestPath").toString());
                        }
                        ReponseQueue.destroy(socketSessionID);
                    } else if (reObject instanceof String) {
                        //获取失败
                        ro.setErrorcode(ErrorCode.Failure);
                        ro.setMessage("下发文件失败，等待超时");
                        ro.setData(reObject.toString());
                        ReponseQueue.destroy(socketSessionID);
                        logger.warn("下发文件失败，等待超时");
                        return GsonUtil.createGsonString(ro);
                    }
                } else {
                    failureReturnWithoutConnect(ro, info.getPolicecarid());
                }
            }
            return GsonUtil.createGsonString(ro);
        } catch (Exception ex) {
            logger.error("下发文件失败,失败原因：" + ex.getMessage(), ex);
            if (ReponseQueue.isExiststQueueValue(socketSessionID)) {
                ReponseQueue.destroy(socketSessionID);
            }
            return BasicReturn.ReturnEx(ex);
        }
    }


    /**
     * @param info 命令实体
     * @return String 实体json串
     * @Description 功能：
     **/
    private static String creatJsonInfo(SendCommandPojo info) {
        DwJsonInfo dwJsonInfo = new DwJsonInfo();
        dwJsonInfo.setProgram(CommandConstant.JsonProgram);
        dwJsonInfo.setTargetIP(CommandConstant.JsonTargetIP);
        dwJsonInfo.setTargetPort(CommandConstant.JsonTargetPort);
        dwJsonInfo.setSourceIP(CommandConstant.JsonSourceIP);
        dwJsonInfo.setSourcePort(CommandConstant.JsonSourcePort);
        dwJsonInfo.setNetworkType(CommandConstant.JsonNetworkType);
        dwJsonInfo.setCmdType(CommandConstant.JsonCmdType);
        DwInfoContent dwInfoContent = new DwInfoContent();
        dwInfoContent.setCMD(info.getCommand());
        dwInfoContent.setParameter("");
        dwInfoContent.setReport("");
        dwInfoContent.setCmdType(info.getIssync());
        dwInfoContent.setRetCode("");
        dwInfoContent.setRunTime("");
        dwJsonInfo.setContent(dwInfoContent);
        return ConvertToString(dwJsonInfo);
    }

    /**
     * @param sourcePath 获取文件的路径
     * @return String 实体json串
     * @Description 功能：
     **/
    public static String creatGetFileInfo(String sourcePath) {
        DwDataInfo dwDataInfo = new DwDataInfo();
        dwDataInfo.setProgram(CommandConstant.JsonProgram);
        dwDataInfo.setTargetIP(CommandConstant.JsonTargetIP);
        dwDataInfo.setTargetPort(CommandConstant.JsonTargetPort);
        dwDataInfo.setSourceIP(CommandConstant.JsonSourceIP);
        dwDataInfo.setSourcePort(CommandConstant.JsonSourcePort);
        dwDataInfo.setNetworkType(CommandConstant.JsonNetworkType);
        dwDataInfo.setCmdType(CommandConstant.FileCmdType);
        DwDataContent dwInfoContent = new DwDataContent();
        dwInfoContent.setFileName("");
        //文件路径，获取客户端文件
        dwInfoContent.setSourcePath(sourcePath);
        dwInfoContent.setDestPath("");
        dwInfoContent.setFileMd5("");
        dwInfoContent.setFrameMd5("");
        dwInfoContent.setAllFrameNum("");
        dwInfoContent.setFrameSN("");
        dwInfoContent.setParameter("");
        dwInfoContent.setReport("");
        dwInfoContent.setType("1");
        dwInfoContent.setRetCode("");
        dwInfoContent.setRunTime("");
        dwDataInfo.setContent(dwInfoContent);
        return ConvertToString(dwDataInfo);
    }

    /**
     * @param info 下发文件的实体
     * @return String 实体json串
     * @Description 功能：
     **/
    public static String creatSendFileInfo(SendFilePojo info) {
        DwDataInfo dwDataInfo = new DwDataInfo();
        dwDataInfo.setProgram(CommandConstant.JsonProgram);
        dwDataInfo.setTargetIP(CommandConstant.JsonTargetIP);
        dwDataInfo.setTargetPort(CommandConstant.JsonTargetPort);
        dwDataInfo.setSourceIP(CommandConstant.JsonSourceIP);
        dwDataInfo.setSourcePort(CommandConstant.JsonSourcePort);
        dwDataInfo.setNetworkType(CommandConstant.JsonNetworkType);
        dwDataInfo.setCmdType(CommandConstant.FileCmdType);
        DwDataContent dwInfoContent = new DwDataContent();
        dwInfoContent.setFileName(info.getFileName());
        dwInfoContent.setSourcePath("");
        dwInfoContent.setDestPath(info.getDestpath());
        dwInfoContent.setFileMd5(info.getFileMD5());
        dwInfoContent.setFrameMd5(info.getFileMD5());
        dwInfoContent.setAllFrameNum("1");
        dwInfoContent.setFrameSN("1");
        dwInfoContent.setParameter("");
        dwInfoContent.setReport("");
        dwInfoContent.setType("0");
        dwInfoContent.setRetCode("");
        dwInfoContent.setRunTime("");
        dwDataInfo.setContent(dwInfoContent);
        return ConvertToString(dwDataInfo);
    }

    /**
     * @param ro          返回消息体
     * @param policecarid 警车id
     * @Description 功能：失败返回
     **/
    private void failureReturnWithoutConnect(ReturnQueuingObject ro, String policecarid) {
        logger.warn("警车id:" + policecarid + "的客户端不存在连接");
        ro.setErrorcode(ErrorCode.Failure);
        ro.setMessage("执行失败，警车客户端不存在连接");
        ro.setData("");
    }

    /**
     * @param session 客户端连接session
     * @param info 获取文件命令体
     * @param socketSessionID socketSessionID
     * @param ro 返回前端对象
     * @return ro
     * @throws Exception
     * @Description 功能：获取OBC文件过程
     **/
    private ReturnQueuingObject getOBCFileProcess(IoSession session, SendCommandPojo info, String socketSessionID,ReturnQueuingObject ro) {
        try{
            /* 0车载执法套件，1设备，2国标级联*/
            if ("1".equals(info.getConfigtype())) {
                //获取设备文件（ini，或者图片），需要二次确认是否获取
                HeaderInfo headerInfo = new HeaderInfo(NetInfoType.NET_STRING.getIndex(), 0, 0);
                //命令json串
                String json = creatJsonInfo(info);
                headerInfo.setDwInfoLen(json.getBytes(CHARSETNAME).length);
                //第一次先发送头文件，在发送命令json串--发送消息到客户端
                session.write(headerInfo);
                session.write(json);
                logger.info("下发的头文件：" + headerInfo.toString());
                logger.info("下发的json：" + json);
                //第一次阻塞等待结果
                Object reObject = ReponseQueue.takeQueueValue(socketSessionID);
                if (reObject instanceof MsgPack) {
                    MsgPack msgPack = (MsgPack) reObject;
                    String content = JSONObject.fromObject(msgPack.getMsgJsonPack()).get(CONTENT).toString();
                    JSONObject contentObject = JSONObject.fromObject(content);
                    String retCode = contentObject.get(RETCODE).toString();
                    String report = contentObject.get(REPORT).toString();
                    if ("0".equals(retCode) && report.contains("OK")) {
                        //第二次先发送头文件，在发送命令json串（转发）---发送消息到客户端
                        HeaderInfo headerInfo2 = new HeaderInfo(NetInfoType.NET_STRING.getIndex(), 0, 0);
                        //获取文件路径
                        String sourcePath = report.substring(report.indexOf("."), report.length());
                        String fileJson = creatGetFileInfo(sourcePath);
                        headerInfo2.setDwInfoLen(fileJson.getBytes(CHARSETNAME).length);
                        session.write(headerInfo2);
                        session.write(fileJson);
                        logger.info("下发的头文件：" + headerInfo.toString());
                        logger.info("下发的json：" + json);
                    } else {
                        //获取失败
                        ro.setErrorcode(ErrorCode.Failure);
                        ro.setMessage("获取文件失败，OBC获取设备文件失败");
                        ro.setData("");
                        ReponseQueue.destroy(socketSessionID);
                        return ro;
                    }
                    //第二次阻塞等待结果
                    Object returnFileInfo = ReponseQueue.takeQueueValue(socketSessionID);
                    if (returnFileInfo instanceof ReturnFileInfo) {
                        ro.setErrorcode(ErrorCode.Success);
                        ro.setMessage("获取文件成功！");
                        ro.setData(GsonUtil.createGsonString(returnFileInfo));
                    } else if (returnFileInfo instanceof MsgPack) {
                        MsgPack msInfo = (MsgPack) returnFileInfo;
                        String msInfoJson = msInfo.getMsgJsonPack();
                        JSONObject msObject = JSONObject.fromObject(msInfoJson);
                        String mscontent = msObject.get(CONTENT).toString();
                        JSONObject mscontentObject = msObject.fromObject(mscontent);
                        logger.warn("警车id为:" + info.getPolicecarid() + "的第二次阻塞获取文件信息失败");
                        ro.setErrorcode(ErrorCode.Failure);
                        ro.setMessage("文件上传失败，第二次阻塞获取文件信息失败");
                        ro.setData(mscontentObject.get(REPORT).toString() + "===" + contentObject.get(REPORT).toString());
                    } else {
                        logger.warn("警车id是:" + info.getPolicecarid() + "的第二次阻塞获取文件信息失败");
                        ro.setErrorcode(ErrorCode.Failure);
                        ro.setMessage("文件上传失败，第二次阻塞获取文件信息失败");
                        ro.setData(returnFileInfo.toString());
                    }
                    ReponseQueue.destroy(socketSessionID);
                } else if (reObject instanceof String) {
                    //获取失败
                    ro.setErrorcode(ErrorCode.Failure);
                    ro.setMessage("获取文件失败，等待超时");
                    ro.setData(reObject.toString());
                    ReponseQueue.destroy(socketSessionID);
                }
            } else {
                //OBC 或者国标,直接获取
                //获取设备文件（ini，或者图片）
                HeaderInfo headerInfo = new HeaderInfo(NetInfoType.NET_STRING.getIndex(), 0, 0);
                //命令json串
                String json = creatGetFileInfo(info.getCommand());
                headerInfo.setDwInfoLen(json.getBytes(CHARSETNAME).length);
                //第一次先发送头文件，在发送命令json串--发送消息到客户端
                session.write(headerInfo);
                session.write(json);
                logger.info("下发的头文件：" + headerInfo.toString());
                logger.info("下发的json：" + json);
                //第一次阻塞等待结果
                Object reObject = ReponseQueue.takeQueueValue(socketSessionID);
                if (reObject instanceof ReturnFileInfo) {
                    ro.setErrorcode(ErrorCode.Success);
                    ro.setMessage("获取文件成功！");
                    ro.setData(GsonUtil.createGsonString(reObject));
                    logger.info("获取文件成功！返回结果：" + reObject.toString());
                } else {
                    ro.setErrorcode(ErrorCode.Failure);
                    ro.setMessage("文件获取失败");
                    ro.setData("");
                    logger.warn("警车:" + info.getPolicecarid() + "获取文件信息失败。"+"失败原因：" + reObject.toString());
                }
                ReponseQueue.destroy(socketSessionID);
            }
            return ro;
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
