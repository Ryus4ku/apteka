package ru.shutov.apteka.apteka.Services;

import org.springframework.stereotype.Service;
import ru.shutov.apteka.apteka.DTOs.PurchaseDto;
import ru.shutov.apteka.apteka.DTOs.RemedyDto;
import ru.shutov.apteka.apteka.Model.Offer;
import ru.shutov.apteka.apteka.Model.Purchase;
import ru.shutov.apteka.apteka.Repository.PurchaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final RemedyService remedyService;

    public PurchaseService(PurchaseRepository purchaseRepository, RemedyService remedyService) {
        this.purchaseRepository = purchaseRepository;
        this.remedyService = remedyService;
    }

    public List<PurchaseDto> getList() {
        List<Purchase> purchases = purchaseRepository.findAllByActualIsTrue();
        return purchases.stream()
                .map(this::transformPurchase)
                .collect(Collectors.toList());
    }

    private PurchaseDto transformPurchase(Purchase purchase) {
        List<RemedyDto> baskets = purchase.getOffer().stream()
                .map(Offer::getRemedy)
                .distinct()
                .map(remedyService::transformRemedy)
                .collect(Collectors.toList());

        baskets.forEach(basket -> {
            long count = purchase.getOffer().stream()
                    .filter(offer -> offer.getRemedy().getId().equals(basket.getId()))
                    .count();

            basket.setPurchaseCount((int) count);
        });

        return PurchaseDto.builder()
                .id(purchase.getId())
                .fio(purchase.getFio())
                .phoneNumber(purchase.getPhoneNumber())
                .comment(purchase.getComment())
                .baskets(baskets)
                .totalPrice(purchase.getTotalPrice())
                .build();
    }

    public void closePurchase(Long id) {
        Purchase purchase = purchaseRepository.getOne(id);
        purchase.setActual(false);
        purchaseRepository.save(purchase);
    }
}
