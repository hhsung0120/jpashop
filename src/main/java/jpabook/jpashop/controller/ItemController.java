package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public ModelAndView createForm() {
        return new ModelAndView("/items/createItemForm")
                .addObject("form", new BookForm());
    }

    @PostMapping("/items/new")
    public String create(@ModelAttribute BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public ModelAndView list(){
        List<Item> items = itemService.findItems();
        return new ModelAndView("/items/itemList")
                .addObject("items", items);
    }

    @GetMapping("/items/{itemId}/edit")
    public ModelAndView edit(@PathVariable Long itemId){
        Item items = itemService.findOne(itemId);
        return new ModelAndView("/items/updateItemForm")
                .addObject("form", items);
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, @ModelAttribute BookForm form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        //itemService.saveItem(book);
        itemService.updateItem(itemId, book);

        //더 나은 설계
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }

}
