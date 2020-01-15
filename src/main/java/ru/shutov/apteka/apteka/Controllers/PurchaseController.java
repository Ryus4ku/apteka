package ru.shutov.apteka.apteka.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.shutov.apteka.apteka.DTOs.PurchaseDto;
import ru.shutov.apteka.apteka.Services.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/list")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity<List<PurchaseDto>> list() {
        return new ResponseEntity<>(purchaseService.getList(), HttpStatus.OK);
    }

    @GetMapping("/close/{id}")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity close(@PathVariable("id") Long id) {
        purchaseService.closePurchase(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
