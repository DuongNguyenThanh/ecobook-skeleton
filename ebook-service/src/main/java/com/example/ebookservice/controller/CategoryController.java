package com.example.ebookservice.controller;

import com.example.ebookdatamodel.entity.Category;
import com.example.ebookservice.repository.CategoryRepository;
import com.example.ebookservice.payload.request.CategoryAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/category", produces = "application/json")
public class CategoryController {
    @Autowired
    private EntityLinks entityLinks;
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/")
    public Iterable<Category> getAll(){
        return categoryRepository.findAll();
    }
    @GetMapping("/{cateId}")
    public Category getDetail(@PathVariable Integer cateId){
        return categoryRepository.findById(cateId).get();
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCate(@RequestBody CategoryAddRequest categoryAddRequest){
        Optional<Category> cs = categoryRepository.findByName(categoryAddRequest.getName());
        if(!cs.isPresent()){
            Category c = new Category();
            c.setName(categoryAddRequest.getName());
            c.setDescription(categoryAddRequest.getDescription());
            return categoryRepository.save(c);
        }
        return null;
    }
    @PutMapping("/{cateId}")
    public Category putCate(@RequestBody Category category){
        return  categoryRepository.save(category);
    }
//    @DeleteMapping("/{cateId}")
//    public void deleteCategory(@PathVariable Integer cateId){
//        try{
//            categoryRepository.deleteById(cateId);
//        }catch (EmptyResultDataAccessException e){
//            log.info("error");
//        }
//    }
}
