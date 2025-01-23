package com.hrbatovic.springboot.master.product.api;

import com.hrbatovic.master.springboot.product.api.CategoriesApi;
import com.hrbatovic.master.springboot.product.model.Category;
import com.hrbatovic.master.springboot.product.model.CategoryListResponse;
import com.hrbatovic.master.springboot.product.model.Pagination;
import com.hrbatovic.springboot.master.product.ApiInputValidator;
import com.hrbatovic.springboot.master.product.db.entities.CategoryEntity;
import com.hrbatovic.springboot.master.product.db.repositories.CategoryRepository;
import com.hrbatovic.springboot.master.product.mapper.MapUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CategoryApiService implements CategoriesApi {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public CategoryListResponse listCategories(Integer page, Integer limit, String search) {
        List<CategoryEntity> categoryEntities = categoryRepository.queryCategories(search, page, limit);

        List<Category> categories = categoryEntities.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems(categoryEntities.size());
        pagination.setTotalPages((int) Math.ceil((double) categoryEntities.size() / (limit != null ? limit : 10)));

        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categories);
        response.setPagination(pagination);

        return response;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public Category getCategoryById(UUID id) {
        ApiInputValidator.validateCategoryId(id);
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for ID: " + id));
        return mapper.map(categoryEntity);
    }
}
