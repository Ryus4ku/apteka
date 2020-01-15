package ru.shutov.apteka.apteka.Services;

import org.springframework.stereotype.Service;
import ru.shutov.apteka.apteka.DTOs.PurchaseDto;
import ru.shutov.apteka.apteka.DTOs.RemedyDto;
import ru.shutov.apteka.apteka.Model.Offer;
import ru.shutov.apteka.apteka.Model.Purchase;
import ru.shutov.apteka.apteka.Model.Remedy;
import ru.shutov.apteka.apteka.Repository.PurchaseRepository;
import ru.shutov.apteka.apteka.Repository.RemedyRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final PurchaseRepository purchaseRepository;
    private final RemedyRepository remedyRepository;

    public BasketService(PurchaseRepository purchaseRepository, RemedyRepository remedyRepository) {
        this.purchaseRepository = purchaseRepository;
        this.remedyRepository = remedyRepository;
    }

    public void createPurchase(PurchaseDto purchase) {
        List<Offer> offers = new ArrayList<>();
        purchase.getBaskets()
                .forEach(basket -> {
                    remedyRepository.findById(basket.getId()).ifPresent(remedy -> {
                        remedy.getOffers().stream()
                                .filter(offer -> offer.getPurchase() == null)
                                .limit(basket.getPurchaseCount())
                                .forEach(offers::add);
                    });
                });

        Purchase newPurchase = Purchase.builder()
                .fio(purchase.getFio())
                .phoneNumber(purchase.getPhoneNumber())
                .comment(purchase.getComment())
                .actual(true)
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;
        if (!offers.isEmpty()) {
            for(Offer offer : offers) {
                Remedy remedy = offer.getRemedy();
                totalPrice = totalPrice.add(new BigDecimal(remedy.getPrice()));
                offer.setPurchase(newPurchase);
            }
        }

        newPurchase.setTotalPrice(totalPrice);
        newPurchase.setOffer(offers);

        purchaseRepository.save(newPurchase);
    }
}
