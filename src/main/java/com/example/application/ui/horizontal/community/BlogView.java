package com.example.application.ui.horizontal.community;

import com.example.application.backend.entities.PageEntity;
import com.example.application.ui.MainView;
import com.vaadin.flow.component.html.Anchor;
import com.example.application.ui.ContentHolder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 *  Blog View shows ...
 *
 *  @author Rebecca Schirmacher
 *  @version 2.0
 *  @since 15.12.2020
 *  @lastUpdated 17.01.2021
 */

@Route(value = "blog", layout = MainView.class)
@PageTitle("Blog")
public class BlogView extends Div {
    private H1 pageTitle;
    private Span pageText;
    private PageEntity pageEntity;

    public BlogView() {
        setId("blog");
        setClassName("pageContentPosition");
        addClassName("communityColorscheme");

        pageTitle = new H1("Blog");
        pageText = new Span("zum Blog der BSAG...");

        VerticalLayout verticalLayout = new VerticalLayout();

        Anchor linkBlog = new Anchor("https://blog.bsag.de/", pageText);
        linkBlog.setTarget("https://blog.bsag.de/");

        verticalLayout.add(linkBlog);
        add(pageTitle, verticalLayout);

    }

}
