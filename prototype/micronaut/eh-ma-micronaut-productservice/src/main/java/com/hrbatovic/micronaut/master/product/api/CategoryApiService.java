package com.hrbatovic.micronaut.master.product.api;

import com.hrbatovic.micronaut.master.product.db.entities.CategoryEntity;
import com.hrbatovic.micronaut.master.product.db.repositories.CategoryRepository;
import com.hrbatovic.micronaut.master.product.mapper.MapUtil;
import com.hrbatovic.micronaut.master.product.model.Category;
import com.hrbatovic.micronaut.master.product.model.CategoryListResponse;
import com.hrbatovic.micronaut.master.product.model.Pagination;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Singleton
public class CategoryApiService implements CategoryManagementApi{

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    MapUtil mapper;

    @Override
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
    public Category getCategoryById(UUID id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for ID: " + id));
        return mapper.map(categoryEntity);
    }

}
