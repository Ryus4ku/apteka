package ru.shutov.apteka.apteka.Services;

import org.springframework.stereotype.Service;
import ru.shutov.apteka.apteka.DTOs.RemedyDto;
import ru.shutov.apteka.apteka.Model.Remedy;
import ru.shutov.apteka.apteka.Repository.RemedyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemedyService {
    private final RemedyRepository remedyRepository;

    public RemedyService(RemedyRepository remedyRepository) {
        this.remedyRepository = remedyRepository;
    }

    public void createNewRemedy(RemedyDto remedy) {
        Remedy newRemedy = Remedy.builder().name(remedy.getName()).price(remedy.getPrice()).build();
        remedyRepository.save(newRemedy);
    }

    public List<RemedyDto> getList() {
        return remedyRepository.findAll()
                .stream()
                .map(this::transformRemedy)
                .collect(Collectors.toList());
    }

    private RemedyDto transformRemedy(Remedy remedy) {
        return RemedyDto.builder().id(remedy.getId()).name(remedy.getName()).price(remedy.getPrice()).build();
    }
}
