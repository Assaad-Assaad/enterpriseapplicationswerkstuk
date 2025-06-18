package be.ehb.enterpriseapplications.werkstuk.controller;


import be.ehb.enterpriseapplications.werkstuk.model.Category;
import be.ehb.enterpriseapplications.werkstuk.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Operations related to auction categories")
@Validated
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    ResponseEntity<Category> save(@Valid @RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

}
