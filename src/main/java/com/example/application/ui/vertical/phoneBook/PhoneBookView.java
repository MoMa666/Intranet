package com.example.application.ui.vertical.phoneBook;

import com.example.application.backend.entities.PageEntity;
import com.example.application.backend.services.pages.PageService;
import com.example.application.ui.MainView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 *  phoneBook View shows ...
 *
 *  @author Rebecca Schirmacher
 *  @version 2.0
 *  @since 15.12.2020
 *  @lastUpdated 17.01.2021
 */
@Route(value = "phoneBook", layout = MainView.class)
@PageTitle("Telefonbuch")

public class PhoneBookView extends Div {
    private PageService pageService;
    private H1 pageTitle;
    private H2 pageText;
    private Span externTele;
    private Span internTele;
    private PageEntity pageEntity;

    public PhoneBookView(PageService pageService) {
        setId("phoneBook");
        setClassName("pageContentPosition");
        addClassName("homeColorscheme");

        pageEntity = pageService.findPageById(23);
        pageTitle = new H1(pageEntity.getTitle());
        pageText = new H2(pageEntity.getContent());

        externTele = new Span("externes Telefonbuch");
        internTele = new Span("internes Telefonbuch");

        VerticalLayout verticalLayout = new VerticalLayout();

        Anchor linkExtern = new Anchor("https://www.11880.com", externTele);
        linkExtern.setTarget("https://www.11880.com");
        Anchor linkIntern = new Anchor("https://fldsr274.bsag.local:8443/php-bin/WebClient.php?lang=de", internTele);
        linkIntern.setTarget("https://fldsr274.bsag.local:8443/php-bin/WebClient.php?lang=de");

        verticalLayout.add(linkExtern);
        verticalLayout.add(linkIntern);
        add(pageTitle,verticalLayout);

    }

}
