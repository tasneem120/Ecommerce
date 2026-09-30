package com.example.FirstProject.Service;

import com.example.FirstProject.Reposatery.CategoryRepo;
import com.example.FirstProject.exception.APIException;
import com.example.FirstProject.exception.ResourceNotFoundException;
import com.example.FirstProject.model.Category;
import com.example.FirstProject.payload.CategoryDTOReq;
import com.example.FirstProject.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimp implements CategoryService{
//    private List<Category> categories=new ArrayList<>();
    @Autowired
    CategoryRepo categoryrepo;
    @Autowired
    private ModelMapper modelMapper  ;
    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories = categoryrepo.findAll();
        if (categories.isEmpty()) {
            throw new APIException("There is no category Found");
        }
List<CategoryDTOReq> categoriesDTO = categories.stream().map(category->modelMapper.map(category,
        CategoryDTOReq.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoriesDTO);
        return categoryResponse;
    }

    @Override
    public CategoryDTOReq CreateCategory(CategoryDTOReq categoryDTOReq) {
        Category category = modelMapper.map(categoryDTOReq, Category.class);
Category savedCategory = categoryrepo.findByCategoryName(category.getCategoryName());
if(savedCategory!=null){
    throw new APIException("Category with the name  "+category.getCategoryName()+" already exists !!!!!!!!!!!!!!");
}
Category savedCategory1 = categoryrepo.save(category);
        return modelMapper.map(savedCategory1,CategoryDTOReq.class);
    }

    @Override
    public CategoryDTOReq deleteCategory(Long id) {

        Category category = categoryrepo.findById(id) .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",id));
        categoryrepo.delete(category);
CategoryDTOReq categoryDTOReq = modelMapper.map(category,CategoryDTOReq.class);
       return  modelMapper.map(categoryDTOReq,CategoryDTOReq.class);
    }

    @Override
    public CategoryDTOReq updateCategory(CategoryDTOReq categoryDTOReq, Long id) {
Category savedCategory= categoryrepo.findById(id).orElseThrow(()->
        new ResourceNotFoundException("Category","CategoryId",id));
        Category category = modelMapper.map(categoryDTOReq, Category.class);

category.setCategoryid(id);
savedCategory=categoryrepo.save(category);
return modelMapper.map(savedCategory,CategoryDTOReq.class);

    }}
