package com.jex.official.task;

import com.jex.official.entity.db.Official;
import com.jex.official.sdk.wechat.dto.SNSUserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OfficialTask {
    private static Logger logger = LoggerFactory.getLogger(OfficialTask.class);
    @Async
    public void asyncUpdateWechatUser(Official official, Map<String, String> data) {
        try {
            WechatUserTask.addOrUpdateUser(official, data);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }

    @Async
    public void asyncUnsubscribeUser(Official official, Map<String, String> data){
        try{
            WechatUserTask.unsubscribeUser(official, data);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }

    @Async
    public void asyncUpdateWechatUser(Official official, SNSUserInfoResponse response){
        try {
            WechatUserTask.addOrUpdateUser(official, response);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }
}
