package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public ModelAndView createForm() {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        return new ModelAndView("/order/orderForm")
                .addObject("members", members)
                .addObject("items", items);
    }

    @PostMapping("/order")
    public ModelAndView order(@RequestParam("memberId") Long memberId
            , @RequestParam("itemId") Long itemId
            , @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return new ModelAndView("redirect:/orders");
    }

    @GetMapping("/orders")
    public ModelAndView orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch) {
        return new ModelAndView("/order/orderList")
                .addObject("orders", orderService.findOrders(orderSearch));
    }

    @PostMapping("/orders/{id}/cancel")
    public ModelAndView cancel(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
        return new ModelAndView("redirect:/orders");
    }
}
