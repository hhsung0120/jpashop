package jpabook.jpashop.api;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        //하이버네이트 5 모듈을 사용해도 포스 레이지 가능하지만, 안쓰는게 좋음.
        //엔티티로 반환은 X
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //강제로 Lazy 초기화 하기 위한 포문
            order.getDelivery().getOrder(); //아무 값이나 호출..! lazy 초기화 하기 위한 것
        }

        return all;
    }

}
