package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.exception.MyException;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import com.neuedu.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int addCategory(Category category) throws MyException {
        return 0;
    }

    @Override
    public int deleteCategory(int categoryId) throws MyException {
        return 0;
    }

    @Override
    public ServerResponse updateCategory(Category record) throws MyException {
        if(record.getId()==null||record.getId()==0)
        {
            return ServerResponse.createServerResponseByFail(1,"更新品类名字失败");
        }
        if(record.getName()==null||record.getName().equals(""))
        {
            return ServerResponse.createServerResponseByFail(1,"更新品类名字失败");
        }
        Category category=categoryMapper.selectByPrimaryKey(record.getId());
        int result=categoryMapper.exsitsCategoryname(record.getName());
        if (result>0&&!category.getName().equals(record.getName()))
        {
            return ServerResponse.createServerResponseByFail(1,"更新品类名字失败");
        }
        result= categoryMapper.updateByPrimaryKey(record);
        if (result>0)
        {
            return ServerResponse.createServerResponseBySuccess("更新品类名字成功");
        }
        return ServerResponse.createServerResponseByFail(1,"更新品类名字失败");
    }

    @Override
    public List<Category> findAll() throws MyException {

        return categoryMapper.selectAll();


    }

    @Override
    public Category findCategoryById(int categoryId) {

        return  categoryMapper.selectByPrimaryKey(categoryId);

    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Category record) {
        if(record.getName()==null||record.getName().equals(""))
        {
            throw new MyException("类别名不能为空","");
        }
        if(record.getSortOrder()==null||record.getSortOrder().equals(""))
        {
            throw new MyException("类别SortOrder不能为空","");
        }
        int result=categoryMapper.exsitsCategoryname(record.getName());
        if (result>0)
        {
            throw new MyException("类名已存在","");
        }
        return categoryMapper.insert(record);
    }

    @Override
    public ServerResponse get_category(int categoryId) {
        int result =categoryMapper.exsitsCategoryId(categoryId);
        if (result<1&&categoryId!=0)
        {
            return ServerResponse.createServerResponseByFail(1,"未找到该品类");
        }

        List<Category> categoryList=categoryMapper.get_category(categoryId);
        List<CategoryVO> categoryVOList= Lists.newArrayList();
        if (categoryList!=null&&categoryList.size()>0)
        {

            for (Category c:categoryList) {
                CategoryVO categoryVO=new CategoryVO();
                categoryVO.assembleCategoryVO(c);
                categoryVOList.add(categoryVO);

            }
        }

        return ServerResponse.createServerResponseBySuccess(null,categoryVOList);

    }

    @Override
    public ServerResponse add_category(int parentId, String categoryName) {
        if (categoryName==null||categoryName.equals(""))
        {
            return ServerResponse.createServerResponseByFail(1,"添加品类失败");
        }
        Category category=new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        int result=categoryMapper.exsitsCategoryname(categoryName);
        if (result>0)
        {
            return ServerResponse.createServerResponseByFail(1,"添加品类失败");
        }
        if(parentId!=0)
        {
            result=categoryMapper.exsitsCategoryId(parentId);
            if (result<1)
            {
                return ServerResponse.createServerResponseByFail(1,"添加品类失败");
            }
        }

        result=categoryMapper.insert(category);
        if (result>0)
        {
            return ServerResponse.createServerResponseBySuccess("添加品类成功");
        }
        return ServerResponse.createServerResponseByFail(1,"添加品类失败");
    }

    @Override
    public ServerResponse get_deep_category(Integer categoryId) {

        if (categoryId==null)
        {
            return ServerResponse.createServerResponseByFail(100,"类别Id不能为空");
        }
        Set<Category> categorySet = Sets.newHashSet();
        categorySet= findAllChildCategory(categorySet,categoryId);
        Set<Integer> integerSet=Sets.newHashSet();
        Iterator<Category> categoryIterator=categorySet.iterator();
        while(categoryIterator.hasNext())
        {
            Category category=categoryIterator.next();
            integerSet.add(category.getId());
        }

        return ServerResponse.createServerResponseBySuccess(null,integerSet);
    }

    private Set<Category> findAllChildCategory(Set<Category> categorySet,Integer categoryId)
    {
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null)
        {
            categorySet.add(category);
        }
        List<Category> categoryList=categoryMapper.get_category(categoryId);
        if (categoryList!=null&&categoryList.size()>0)
        {
            for (Category category1:categoryList)
            {
                findAllChildCategory(categorySet,category1.getId());
            }
        }
        return categorySet;


    }
}
