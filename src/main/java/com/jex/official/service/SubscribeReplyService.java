package com.jex.official.service;

import com.jex.official.entity.db.SubscribeReply;
import com.jex.official.repository.SubscribeReplyRepository;
import com.jex.official.service.dto.SubscribeReplyRequest;
import com.jex.official.service.dto.SubscribeReplyResponse;
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
public class SubscribeReplyService {

    private final SubscribeReplyRepository subscribeReplyRepository;
    private final AdminOfficialService adminOfficialService;

    @Autowired
    public SubscribeReplyService(SubscribeReplyRepository subscribeReplyRepository,
                                 AdminOfficialService adminOfficialService){
        this.subscribeReplyRepository = subscribeReplyRepository;
        this.adminOfficialService = adminOfficialService;
    }

    public SubscribeReplyResponse findAllSubscribeReply(SubscribeReplyRequest model){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit(), sort);
        // List<SubscribeReply> subscribeReplyList = this.subscribeReplyRepository.findAllSubscribeReply(pageable);
        Page<SubscribeReply> subscribeReplyPage = this.subscribeReplyRepository.findAll(pageable);
        SubscribeReplyResponse data = new SubscribeReplyResponse();
        data.setCount((int)subscribeReplyPage.getTotalElements());
        List<SubscribeReplyResponse.SubscribeReplyItemResponse> list = new ArrayList<>();
        for(SubscribeReply subscribeReply:subscribeReplyPage.getContent()) {
            SubscribeReplyResponse.SubscribeReplyItemResponse item = new SubscribeReplyResponse.SubscribeReplyItemResponse();
            item.setId(subscribeReply.getId());
            item.setOfficialId(subscribeReply.getOfficialId());
            item.setOfficialName(adminOfficialService.findOfficialByOfficialId(subscribeReply.getOfficialId()).getName());
            item.setContent(subscribeReply.getContent());
            item.setCreateTime(subscribeReply.getCreateTime());
            item.setUpdateTime(subscribeReply.getUpdateTime());
            list.add(item);
        }
        data.setItems(list);
        return data;
    }

    public void  createSubcribeReply(int officialId, String content){
        SubscribeReply subscribeReply = new SubscribeReply();
        subscribeReply.setOfficialId(officialId);
        subscribeReply.setContent(content);
        subscribeReplyRepository.save(subscribeReply);
    }

    public SubscribeReply findSubReplyByOfficialId(int officialId){
        Optional<SubscribeReply> optionalSubcribeReply = subscribeReplyRepository.findByOfficialId(officialId);
        return optionalSubcribeReply.isPresent()? optionalSubcribeReply.get(): null;
    }

    public void  editSubcribeReply(int officialId, String content){

        SubscribeReply subscribeReply = findSubReplyByOfficialId(officialId);
        subscribeReply.setOfficialId(officialId);
        subscribeReply.setContent(content);
        subscribeReplyRepository.save(subscribeReply);
    }
}
