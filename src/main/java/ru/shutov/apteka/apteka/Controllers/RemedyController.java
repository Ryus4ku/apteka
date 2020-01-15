package ru.shutov.apteka.apteka.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shutov.apteka.apteka.DTOs.RemedyDto;
import ru.shutov.apteka.apteka.Services.RemedyService;

import java.util.List;

@RestController
@RequestMapping("/remedy")
public class RemedyController {
    private final RemedyService remedyService;

    public RemedyController(RemedyService remedyService) {
        this.remedyService = remedyService;
    }

    @PostMapping("/new")
    public ResponseEntity create(@RequestBody RemedyDto remedy) {
        remedyService.createNewRemedy(remedy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RemedyDto>> list() {
        return new ResponseEntity<>(remedyService.getList(), HttpStatus.OK);
    }
}
