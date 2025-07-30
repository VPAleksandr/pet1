package pet.project.hh.service;

import pet.project.hh.Dto.CategoryDto;
import pet.project.hh.models.Category;
import lombok.SneakyThrows;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    @SneakyThrows
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    @SneakyThrows
    CategoryDto getCategoryDtoById(Long id);
}
