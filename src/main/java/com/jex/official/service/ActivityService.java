package com.jex.official.service;

import com.jex.official.entity.db.Activity;
import com.jex.official.entity.db.Official;
import com.jex.official.repository.ActivityRepository;
import com.jex.official.service.dto.ActivityRequest;
import com.jex.official.service.dto.ActivityResponse;
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
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final AdminOfficialService adminOfficialService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           AdminOfficialService adminOfficialService){
        this.activityRepository = activityRepository;
        this.adminOfficialService = adminOfficialService;
    }

    public ActivityResponse findAllActivity(ActivityRequest model){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit(), sort);
        // List<Activity> activities = this.activityRepository.findAllActivity(pageable);
        Page<Activity> activityPage = this.activityRepository.findAll(pageable);
        ActivityResponse data = new ActivityResponse();
        data.setCount((int) activityPage.getTotalElements());
        List<ActivityResponse.ActivityItemResponse> list = new ArrayList<>();
        for(Activity activity:activityPage.getContent()){
            ActivityResponse.ActivityItemResponse item = new ActivityResponse.ActivityItemResponse();
            item.setId(activity.getId());
            item.setName(activity.getName());
            Official official = adminOfficialService.findOfficialByOfficialId(activity.getOfficialId());
            item.setOfficialName(official.getName());
            item.setDescription(activity.getDescription());
            item.setCreateTime(activity.getCreateTime());
            item.setUpdateTime(activity.getUpdateTime());
            list.add(item);
        }
        data.setItems(list);
        return data;
    }

    public void createActivity(ActivityRequest model){
    Activity activity = new Activity();
    activity.setOfficialId(model.getOfficialId());
    activity.setName(model.getName());
    activity.setDescription(model.getDescription());
    this.activityRepository.save(activity);
    }

    public List<Activity> findAll(){
        return  this.activityRepository.findAll();
    }

    public String findNameById(int activityId){
       Optional<Activity> activityOptional = this.activityRepository.findOneById(activityId);
       Activity activity = activityOptional.get();
       return activity.getName();

    }
}
