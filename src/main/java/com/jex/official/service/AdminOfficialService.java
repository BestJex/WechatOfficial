package com.jex.official.service;

import com.jex.official.entity.db.Official;
import com.jex.official.repository.OfficialRepository;
import com.jex.official.service.dto.OfficialRequest;
import com.jex.official.service.dto.OfficialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminOfficialService {

    private final OfficialRepository officialRepository;


    @Autowired
    public  AdminOfficialService(OfficialRepository officialRepository){
        this.officialRepository = officialRepository;
    }

    public OfficialResponse findAllOfficial(OfficialRequest model){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit(), sort);
        Page<Official> officialPage = this.officialRepository.findAll(pageable);
        OfficialResponse data = new OfficialResponse();
        data.setCount((int)officialPage.getTotalElements());
        List<OfficialResponse.OfficialItemResponse> list = new ArrayList<>();
        for(Official official:officialPage.getContent()) {
            OfficialResponse.OfficialItemResponse item = new OfficialResponse.OfficialItemResponse();
            item.setId(official.getId());
            item.setAccount(official.getAccount());
            item.setName(official.getName());
            item.setAppId(official.getAppId());
            item.setAppSecret(official.getAppSecret());
            item.setToken(official.getToken());
            item.setAesKey(official.getAesKey());
            item.setCreateTime(official.getCreateTime());
            item.setUpdateTime(official.getUpdateTime());
            list.add(item);
        }
        data.setItems(list);
        return data;
    }

    public List<Official> findAll(){
    return officialRepository.findAll();
    }

    public Official findOfficialByOfficialId(int officialId){
        Optional<Official> optionalOfficial = this.officialRepository.findById(officialId);
        return  optionalOfficial.isPresent() ? optionalOfficial.get() : null;
    }

    public Official findOfficialByAppId(String appId){
        Optional<Official> optionalOfficial = this.officialRepository.findOneByAppId(appId);
        return  optionalOfficial.isPresent() ? optionalOfficial.get() : null;
    }

    public void  createOfficial(OfficialRequest model){
        Official official = new Official();
        official.setName(model.getName());
        official.setAccount(model.getAccount());
        official.setAppId(model.getAppId());
        official.setAppSecret(model.getAppSecret());
        official.setToken(model.getToken());
        official.setAesKey(model.getAesKey());
        officialRepository.save(official);
    }
}
