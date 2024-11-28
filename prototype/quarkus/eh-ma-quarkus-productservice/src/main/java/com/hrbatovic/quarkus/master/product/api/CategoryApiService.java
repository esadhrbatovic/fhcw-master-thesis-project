package com.hrbatovic.quarkus.master.product.api;

import com.hrbatovic.master.quarkus.product.api.CategoriesApi;
import com.hrbatovic.master.quarkus.product.model.Category;
import com.hrbatovic.master.quarkus.product.model.CategoryListResponse;
import com.hrbatovic.master.quarkus.product.model.Pagination;
import com.hrbatovic.quarkus.master.product.db.entities.CategoryEntity;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class CategoryApiService implements CategoriesApi {

    @Override
    public CategoryListResponse listCategories(Integer page, Integer limit, String search) {

        String query = (search != null && !search.isEmpty())
                ? "{ 'name': { '$regex': ?1, '$options': 'i' } }"
                : "{}";

        PanacheQuery<CategoryEntity> panacheQuery = CategoryEntity.find(query, search);

        if (page == null || page < 1) page = 1;
        if (limit == null || limit < 1) limit = 10;

        panacheQuery.page(Page.of(page - 1, limit));

        List<CategoryEntity> categoryEntities = panacheQuery.list();
        long totalItems = panacheQuery.count();
        int totalPages = panacheQuery.pageCount();

        List<Category> categories = categoryEntities.stream()
                .map(entity -> {
                    Category category = new Category();
                    category.setId(entity.getId());
                    category.setName(entity.getName());
                    return category;
                })
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

        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());

        return category;
    }
}
