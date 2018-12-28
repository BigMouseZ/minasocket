package com.signalway.service;

import com.signalway.entity.questentity.SendCommandPojo;
import com.signalway.entity.questentity.SendFilePojo;

/**
 * Created by ZhangGang on 2018/6/28.
 */
public interface MaintenanceService {


   String GetOBCFile(SendCommandPojo info);

   String SendCommand(SendCommandPojo info);

   String SendFile(SendFilePojo info);

}
