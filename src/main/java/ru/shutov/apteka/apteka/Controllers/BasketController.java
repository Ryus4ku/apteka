package ru.shutov.apteka.apteka.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shutov.apteka.apteka.DTOs.PurchaseDto;
import ru.shutov.apteka.apteka.Services.BasketService;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/new")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity newPurchase(@RequestBody PurchaseDto purchase) {
        basketService.createPurchase(purchase);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
