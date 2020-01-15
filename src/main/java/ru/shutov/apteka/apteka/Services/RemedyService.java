package ru.shutov.apteka.apteka.Services;

import org.springframework.stereotype.Service;
import ru.shutov.apteka.apteka.DTOs.RemedyDto;
import ru.shutov.apteka.apteka.Model.Offer;
import ru.shutov.apteka.apteka.Model.Remedy;
import ru.shutov.apteka.apteka.Repository.RemedyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RemedyService {
    private final RemedyRepository remedyRepository;

    public RemedyService(RemedyRepository remedyRepository) {
        this.remedyRepository = remedyRepository;
    }

    public void createNewRemedy(RemedyDto remedy) {
        List<Offer> offers = new ArrayList<>();

        Remedy newRemedy = Remedy.builder()
                .name(remedy.getName())
                .price(remedy.getPrice())
                .build();

        if (remedy.getCount() > 0) {
            IntStream.range(0, remedy.getCount())
                    .forEach(val ->
                        offers.add(Offer.builder()
                            .remedy(newRemedy)
                            .build())
                    );
        }

        newRemedy.setOffers(offers);
        remedyRepository.save(newRemedy);
    }

    public List<RemedyDto> getList() {
        return remedyRepository.findAll()
                .stream()
                .map(this::transformRemedy)
                .collect(Collectors.toList());
    }

    public RemedyDto transformRemedy(Remedy remedy) {
        long count = remedy.getOffers().stream()
                .filter(offer -> offer.getPurchase() == null)
                .count();

        return RemedyDto.builder()
                .id(remedy.getId())
                .name(remedy.getName())
                .price(remedy.getPrice())
                .count((int) count)
                .build();
    }
}
