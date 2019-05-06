package com.jex.official.service;

import com.jex.official.entity.db.Order;
import com.jex.official.repository.OrderRepository;
import com.jex.official.service.dto.ActivityRequest;
import com.jex.official.service.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ActivityService activityService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ActivityService activityService){
        this.orderRepository = orderRepository;
        this.activityService = activityService;
    }

    public OrderResponse findAllOrder(ActivityRequest model){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit(), sort);
        // List<Order> orders = this.orderRepository.findAllOrder(pageable);
        Page<Order> orderPage = this.orderRepository.findAll(pageable);
        OrderResponse data = new OrderResponse();
        data.setCount((int)orderPage.getTotalElements());
        List<OrderResponse.OrderItemResponse> list = new ArrayList<>();
        for(Order order:orderPage.getContent()) {
            OrderResponse.OrderItemResponse item = new OrderResponse.OrderItemResponse();
            item.setOrderId(order.getOrderId());
            item.setActivity(activityService.findNameById(order.getActivityId()));
            item.setName(order.getName());
            item.setOpenId(order.getOpenId());
            item.setPayType(order.getPayType());
            item.setAddress(order.getAddress());
            item.setPrice( String .format("%.2f",(double)order.getPrice()/100));
            item.setPhone(order.getPhone());
            item.setShippingCode(order.getShippingCode());
            item.setStatus(order.getStatus());
            item.setTradeNo(order.getTradeNo());
            item.setCreateTime(order.getCreateTime());
            item.setUpdateTime(order.getUpdateTime());
            item.setPaymentTime(order.getPaymentTime());
            item.setRemark(order.getRemark());
            list.add(item);
        }
        data.setItems(list);
        return data;
    }

    public void createOrder(int activityId, int price, String remark){
        String orderId = this.randomOrder();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setActivityId(activityId);
        order.setPrice(price);
        order.setRemark(remark);
        this.orderRepository.save(order);
    }

    public Order findOrderByOrderId(String orderId){
        Optional<Order> optionalOrder = this.orderRepository.findOneByOrderId(orderId);
        return  optionalOrder.isPresent() ? optionalOrder.get() : null;
    }

    public String randomOrder(){
        //生成随机订单id
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        String rand = String.valueOf((int)((Math.random()*9+1)*100000));
        return now.format(formatter) + rand;
    }

    public int getPriceByOrderId(String orderId){
        Optional<Order> optionalOrder = this.orderRepository.findOneByOrderId(orderId);
        Order order = optionalOrder.get();
        return order.getPrice();
    }

    public void  updateOrder(Order order){
        this.orderRepository.save(order);
    }

}
