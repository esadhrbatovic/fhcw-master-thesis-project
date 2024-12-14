package com.hrbatovic.quarkus.master.product.api;

import com.hrbatovic.master.quarkus.product.api.CategoriesApi;
import com.hrbatovic.master.quarkus.product.model.Category;
import com.hrbatovic.master.quarkus.product.model.CategoryListResponse;
import com.hrbatovic.master.quarkus.product.model.Pagination;
import com.hrbatovic.quarkus.master.product.db.entities.CategoryEntity;
import com.hrbatovic.quarkus.master.product.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class CategoryApiService implements CategoriesApi {

    @Inject
    MapUtil mapper;

    @Override
    public CategoryListResponse listCategories(Integer page, Integer limit, String search) {
        if (page == null || page < 1) page = 1;
        if (limit == null || limit < 1) limit = 10;

        PanacheQuery<CategoryEntity> panacheQuery = CategoryEntity.findCategories(page, limit, search);

        List<CategoryEntity> categoryEntities = panacheQuery.list();

        long totalItems = panacheQuery.count();
        int totalPages = panacheQuery.pageCount();

        List<Category> categories = categoryEntities.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categories);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setLimit(limit);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);
        response.setPagination(pagination);

        return response;
    }

    @Override
    public Category getCategoryById(UUID id) {
        CategoryEntity categoryEntity = CategoryEntity.findById(id);

        if (categoryEntity == null) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        return mapper.map(categoryEntity);
    }
}
