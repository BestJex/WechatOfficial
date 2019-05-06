package com.jex.official.service;

import com.jex.official.entity.db.KeywordReply;
import com.jex.official.entity.db.Official;
import com.jex.official.repository.KeywordReplyRepository;
import com.jex.official.repository.OfficialRepository;
import com.jex.official.service.dto.KeywordReplyCreateRequest;
import com.jex.official.service.dto.KeywordReplyEditRequest;
import com.jex.official.service.dto.KeywordReplyListRequest;
import com.jex.official.service.dto.KeywordReplyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class KeywordReplyService {
    private final KeywordReplyRepository keywordReplyRepository;
    private final OfficialRepository officialRepository;

    @Autowired
    public KeywordReplyService(KeywordReplyRepository keywordReplyRepository,
                               OfficialRepository officialRepository){
        this.keywordReplyRepository = keywordReplyRepository;
        this.officialRepository = officialRepository;
    }

    public KeywordReplyListResponse getKeywordReplies(KeywordReplyListRequest model){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage() - 1, model.getLimit(), sort);
        //Page<KeywordReply> keywordReplyPage = this.keywordReplyRepository.findAll(pageable);
        Specification<KeywordReply> specOfficialId = (root, query, cb) -> {
            if(model.getOfficialId() > 0) {
                return cb.equal(root.<Integer>get("officialId"), model.getOfficialId());
            }else{
                return null;
            }
        };
        Specification<KeywordReply> specKeyword = (root, query, cb) -> {
            if(StringUtils.hasText(model.getKeyword())) {
                return cb.equal(root.<String>get("keyword"), model.getKeyword());
            }else{
                return null;
            }
        };
        Specification spec = Specification.where(specOfficialId).and(specKeyword);
        Page<KeywordReply> keywordReplyPage = this.keywordReplyRepository.findAll(spec, pageable);

        KeywordReplyListResponse data = new KeywordReplyListResponse();
        data.setCount((int)keywordReplyPage.getTotalElements());

        List<KeywordReplyListResponse.KeywordReplyItemResponse> list = new ArrayList<>();
        for (KeywordReply keywordReply:keywordReplyPage.getContent()) {
            KeywordReplyListResponse.KeywordReplyItemResponse item = new KeywordReplyListResponse.KeywordReplyItemResponse();
            item.setId(keywordReply.getId());
            item.setOfficialName(keywordReply.getOfficial().getName());
            item.setKeyword(keywordReply.getKeyword());
            item.setContent(keywordReply.getContent());
            item.setCreateTime(keywordReply.getCreateTime());
            item.setUpdateTime(keywordReply.getUpdateTime());
            list.add(item);
        }

        data.setItems(list);
        return data;
    }

    public boolean createKeywordReply(KeywordReplyCreateRequest model){
        List<Official> officials = this.officialRepository.findAllById(Arrays.asList(model.getOfficialIds()));
        if(officials.size() == 0 || officials.size() != model.getOfficialIds().length){
            return false;
        }
        for (Official official: officials) {
            KeywordReply keywordReply = new KeywordReply();
            keywordReply.setKeyword(model.getKeyword());
            keywordReply.setContent(model.getContent());
            keywordReply.setOfficial(official);
            this.keywordReplyRepository.save(keywordReply);
        }
        return true;
    }

    public boolean deleteKeywordReply(int id){
        this.keywordReplyRepository.deleteById(id);
        return true;
    }

    public boolean updateKeywordReply(KeywordReplyEditRequest model){
        Optional<KeywordReply> optionalKeywordReply = this.keywordReplyRepository.findById(model.getId());
        if(!optionalKeywordReply.isPresent()){
            return false;
        }
        KeywordReply keywordReply = optionalKeywordReply.get();
        keywordReply.setKeyword(model.getKeyword());
        keywordReply.setContent(model.getContent());
        this.keywordReplyRepository.save(keywordReply);
        return true;
    }

    public KeywordReply getKeywordReply(Official official, String keyword){
        Optional<KeywordReply> optionalKeywordReply = this.keywordReplyRepository.findOneByOfficialAndKeyword(official, keyword);
        if(optionalKeywordReply.isPresent()){
            return optionalKeywordReply.get();
        }else{
            return null;
        }
    }
}
