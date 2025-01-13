package com.hrbatovic.micronaut.master.product.api;

import com.hrbatovic.micronaut.master.product.model.Category;
import com.hrbatovic.micronaut.master.product.model.CategoryListResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
public class CategoryApiService implements CategoryManagementApi{

    @Override
    public CategoryListResponse listCategories(Integer page, Integer limit, String search) {
        return null;
    }

    @Override
    public Category getCategoryById(UUID id) {
        return null;
    }

}
