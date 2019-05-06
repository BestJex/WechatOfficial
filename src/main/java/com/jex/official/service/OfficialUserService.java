package com.jex.official.service;

import com.jex.official.entity.db.Official;
import com.jex.official.entity.db.User;
import com.jex.official.repository.OfficialRepository;
import com.jex.official.repository.OfficialUserRepository;
import com.jex.official.service.dto.OfficialUserListResponse;
import com.jex.official.service.dto.OfficialUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OfficialUserService {

    private final OfficialUserRepository officialUserRepository;
    private final OfficialRepository officialRepository;

    @Autowired
    public  OfficialUserService(OfficialUserRepository officialUserRepository, OfficialRepository officialRepository){
        this.officialUserRepository = officialUserRepository;
        this.officialRepository = officialRepository;
    }

    public OfficialUserListResponse getOfficialUserList(OfficialUserRequest model){
        Optional<Official> optionalOfficial = this.officialRepository.findById(Integer.valueOf(model.getOfficialId()));
        String appId = optionalOfficial.isPresent()? optionalOfficial.get().getAppId(): null;
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit(), sort);
        //Page<User> userPage = this.officialUserRepository.findAll(pageable);

        Specification<User> specAppId = (root, query, cb) -> {
            if(appId != null) {
                return cb.equal(root.<Integer>get("appId"), appId);
            }else{
                return null;
            }
        };
        Specification<User> speStatus = (root, query, cb) -> {
            if(model.getStatus() > 0) {
                return cb.equal(root.<Integer>get("status"), model.getStatus());
            }else{
                return null;
            }
        };
        Specification<User> speName = (root, query, cb) -> {
            if(StringUtils.hasText(model.getName())) {
                return cb.equal(root.<String>get("nickname"), model.getName());
            }else{
                return null;
            }
        };

        Specification spec = Specification.where(specAppId).and(speStatus).and(speName);
        Page<User> userPage = this.officialUserRepository.findAll(spec, pageable);

        OfficialUserListResponse data = new OfficialUserListResponse();
        data.setCount((int)userPage.getTotalElements());

        List<OfficialUserListResponse.OfficialUserItemResponse> list = new ArrayList<>();
        for (User user:userPage.getContent()){
            OfficialUserListResponse.OfficialUserItemResponse item = new OfficialUserListResponse.OfficialUserItemResponse();
            item.setId(user.getId());
            Optional<Official> optionalOfficialItem = this.officialRepository.findOneByAppId(user.getAppId());
            Official officialItem = optionalOfficialItem.get();
            item.setOfficialName(officialItem.getName());
            item.setOpenId(user.getOpenId());
            item.setUnionId(user.getUnionId());
            item.setNickName(user.getNickname());
            item.setSex(user.getSex());
            item.setCountry(user.getCountry());
            item.setProvince(user.getProvince());
            item.setCity(user.getCity());
            item.setLanguage(user.getLanguage());
            item.setHeadImgUrl(user.getHeadImgUrl());
            item.setSubscribeTime(user.getSubscribeTime());
            item.setSubscribeScene(user.getSubscribeScene());
            item.setStatus(user.getStatus());
            item.setCreateTime(user.getCreateTime());
            item.setUpdateTime(user.getUpdateTime());
            list.add(item);
        }
        data.setItems(list);
        return data;
    }
}
