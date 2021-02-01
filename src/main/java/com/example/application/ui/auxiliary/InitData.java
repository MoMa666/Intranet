package com.example.application.ui.auxiliary;

import com.example.application.backend.entities.PageEntity;
import com.example.application.backend.entities.UserEntity;
import com.example.application.backend.services.files.ImageService;
import com.example.application.backend.services.pages.PageService;
import com.example.application.backend.utils.images.Image;
import com.example.application.backend.utils.images.ImagesManager;
import com.vaadin.componentfactory.Breadcrumb;
import com.vaadin.componentfactory.Breadcrumbs;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * InitData is a class that contains the setData method for the View classes
 *
 * @author Laura Neuendorf
 * @version 1.0
 * @since 01.02.2021
 */

@CssImport("./styles/views/main/content.css")
public class InitData {
    public static PageService pageService;
    public static PageEntity pageEntity;

    public static UserEntity userEntity;

    public static ImagesManager imagesManager;
    public static ImageService imageService;

    public static H1 pageTitle;
    public static Paragraph pageContent;

    public InitData(PageService pageService){

        this.pageService = pageService;
    }

    /*
     * This method fetches the data from the database
     * and displays it on the corresponding page
     *
     * @param int pageId
     * @return dataSet
     */
    public static Component setData(int pageId){

        HorizontalLayout dataSet = new HorizontalLayout();
        dataSet.setId("dataSetLayout");

        pageEntity = pageService.findPageById(pageId);
        pageTitle = new H1(pageEntity.getTitle());
        pageContent = new Paragraph();
        pageContent.getElement().setProperty("innerHTML", pageEntity.getContent());

        Breadcrumbs breadcrumbs = new Breadcrumbs();
        breadcrumbs.add(new Breadcrumb("Home"), new Breadcrumb("Unser Unternehmen"), new Breadcrumb(pageEntity.getTitle()));

        dataSet.add(breadcrumbs, pageTitle, pageContent);
        return dataSet;
    }
}
