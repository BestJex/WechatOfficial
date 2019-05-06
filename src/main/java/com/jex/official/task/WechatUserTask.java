package com.jex.official.task;

import com.jex.official.common.AppContextHelper;
import com.jex.official.entity.converter.UserConverter;
import com.jex.official.entity.db.Official;
import com.jex.official.entity.db.User;
import com.jex.official.repository.UserRepository;
import com.jex.official.sdk.wechat.WechatSDK;
import com.jex.official.sdk.wechat.dto.SNSUserInfoResponse;
import com.jex.official.sdk.wechat.dto.UserInfoResponse;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

public class WechatUserTask {

    public static void addOrUpdateUser(@NotNull Official official, @NotNull Map<String, String> data)
            throws Exception{
        Assert.notNull(official, "official could not be null");
        Assert.notNull(data, "data could not be null");
        UserRepository userRepository = AppContextHelper.getBean(UserRepository.class);

        WechatSDK sdk = new WechatSDK(official.getAppId(), official.getAppSecret());
        String openId = data.get("FromUserName");
        UserInfoResponse response = sdk.getUserInfo(openId);

        User user = null;
        Optional<User> optionalUser = userRepository.findByAppIdAndOpenId(official.getAppId(), openId);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            user = new User();
            user.setAppId(official.getAppId());
        }
        user = UserConverter.updateUserResponseToUser(user, response);
        userRepository.save(user);
    }

    public static void unsubscribeUser(@NotNull Official official, @NotNull Map<String, String> data) throws Exception{
        Assert.notNull(official, "official could not be null");
        Assert.notNull(data, "data could not be null");
        UserRepository userRepository = AppContextHelper.getBean(UserRepository.class);

        String openId = data.get("FromUserName");
        Optional<User> optionalUser = userRepository.findByAppIdAndOpenId(official.getAppId(), openId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setStatus(User.STATUS_UNSUBSCRIBE);
            userRepository.save(user);
        }
    }

    public static void addOrUpdateUser(@NotNull Official official, @NotNull SNSUserInfoResponse response)
            throws Exception{
        Assert.notNull(official, "official could not be null");
        Assert.notNull(response, "response could not be null");

        UserRepository userRepository = AppContextHelper.getBean(UserRepository.class);
        String openId = response.getOpenId();

        User user = null;
        Optional<User> optionalUser = userRepository.findByAppIdAndOpenId(official.getAppId(), openId);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            user = new User();
            user.setAppId(official.getAppId());
        }
        user = UserConverter.updateSNSUserResponseToUser(user, response);
        userRepository.save(user);
    }
}
