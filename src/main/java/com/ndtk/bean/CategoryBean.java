/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Category;
import com.ndtk.service.CategoryService;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name="categoryBean")
@SessionScoped
public class CategoryBean {
    private static CategoryService categorySvc = new CategoryService();
    
    public CategoryBean(){
        
    }
    
    public ArrayList<Category> getListCategory(){
        return categorySvc.getAllCategory();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Crawl Category ">
//    public void crawlCategory() throws IOException{
//        ArrayList<String> listCategory = new ArrayList<>();
//        
//        Document doc = Jsoup.connect("https://sachvui.com").get();
//        Elements elms = doc.select("body > div.container > div.jumbotron.trangchu > ul > li");
//        
//        for (int i = 0; i < elms.size(); i++) {
//            Elements row = elms.get(i).getElementsByTag("a");
//            String categoryName = row.first().text();
//            listCategory.add(categoryName);
//        }
//        
//        for (String name : listCategory) {
//            Category cate = new Category();
//            cate.setCategoryName(name);
//            
//            categorySvc.addOrSaveCategory(cate);
//        }
//    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the categorySvc
     */
    public static CategoryService getCategorySvc() {
        return categorySvc;
    }

    /**
     * @param aCategorySvc the categorySvc to set
     */
    public static void setCategorySvc(CategoryService aCategorySvc) {
        categorySvc = aCategorySvc;
    }
    // </editor-fold>
}
