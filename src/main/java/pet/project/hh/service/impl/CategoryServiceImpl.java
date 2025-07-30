package pet.project.hh.service.impl;

import pet.project.hh.Dto.CategoryDto;
import pet.project.hh.dao.CategoryDao;
import pet.project.hh.exceptions.CategoryNotFoundException;
import pet.project.hh.models.Category;
import pet.project.hh.repository.CategoryRepository;
import pet.project.hh.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> CategoryDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .build())
                .toList();
    }

    @Override
    @SneakyThrows
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            return null;
        }
        return category;
    }

    @SneakyThrows
    @Override
    public CategoryDto getCategoryDtoById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        Category categoryId = categoryRepository.findById(category.getCategoryId().getId())
                .orElse(null);
        String categoryIdName;
        if (categoryId != null) {
            categoryIdName = categoryId.getName();
        } else {
            categoryIdName = "";
        }
        return new CategoryDto(category.getId(), category.getCategoryId().getId(), categoryIdName, category.getName());
    }
}
