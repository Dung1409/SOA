package com.SOA.MenuService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class Controller {

    private static final List<MenuItem> MENU_ITEMS = List.of(
            new MenuItem("M01", "Cơm gà nướng", 45000.0, "Cơm gà nướng sốt mật ong"),
            new MenuItem("M02", "Phở bò tái", 50000.0, "Phở bò với nước dùng đậm vị"),
            new MenuItem("M03", "Bún chả", 55000.0, "Bún chả nướng ăn kèm rau sống"),
            new MenuItem("M04", "Trà đào", 25000.0, "Trà đào mát lạnh"));

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/items")
    public List<MenuItem> getMenuItems() {
        return MENU_ITEMS;
    }
}
