package com.jex.official.entity.converter;

import com.jex.official.entity.db.User;
import com.jex.official.sdk.wechat.dto.SNSUserInfoResponse;
import com.jex.official.sdk.wechat.dto.UserInfoResponse;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

public class UserConverter {
    public static User updateUserResponseToUser(@NotNull User user, @NotNull UserInfoResponse wechatUser){
        Assert.notNull(user, "user could not be null");
        Assert.notNull(wechatUser, "wechatUser could not be null");
        if(wechatUser.getSubscribe() == 1) {
            user.setStatus(User.STATUS_SUBSCRIBE);
            user.setOpenId(wechatUser.getOpenId());
            user.setUnionId(StringUtils.isEmpty(wechatUser.getUnionId()) ? "" : wechatUser.getUnionId());
            user.setNickname(wechatUser.getNickname());
            user.setSex(wechatUser.getSex());
            user.setCountry(wechatUser.getCountry());
            user.setProvince(wechatUser.getProvince());
            user.setCity(wechatUser.getCity());
            user.setLanguage(wechatUser.getLanguage());
            user.setHeadImgUrl(wechatUser.getHeadImgUrl());
            user.setSubscribeScene(wechatUser.getSubscribeScene());
            user.setSubscribeTime(wechatUser.getSubscribeTime());
        }else{
            user.setStatus(User.STATUS_UNSUBSCRIBE);
            user.setOpenId(wechatUser.getOpenId());
        }
        return user;
    }

    public static User updateSNSUserResponseToUser(@NotNull User user, @NotNull SNSUserInfoResponse wechatUser){
        Assert.notNull(user, "user could not be null");
        Assert.notNull(wechatUser, "wechatUser could not be null");
        user.setOpenId(wechatUser.getOpenId());
        user.setUnionId(StringUtils.isEmpty(wechatUser.getUnionId()) ? "" : wechatUser.getUnionId());
        user.setNickname(wechatUser.getNickname());
        user.setSex(wechatUser.getSex());
        user.setCountry(wechatUser.getCountry());
        user.setProvince(wechatUser.getProvince());
        user.setCity(wechatUser.getCity());
        user.setHeadImgUrl(wechatUser.getHeadImgUrl());
        return user;
    }
}
