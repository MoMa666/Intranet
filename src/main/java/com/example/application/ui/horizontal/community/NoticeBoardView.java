package com.example.application.ui.horizontal.community;

import com.example.application.backend.entities.*;
import com.example.application.backend.security.GetUserController;
import com.example.application.backend.entities.NoticeBoardOfferEntity;
import com.example.application.backend.entities.PageEntity;
import com.example.application.backend.entities.UserEntity;
import com.example.application.backend.services.noticeBoard.NoticeBoardOfferService;
import com.example.application.backend.services.pages.PageService;
import com.example.application.backend.services.roles.RoleService;
import com.example.application.backend.services.users.UserService;


import com.example.application.ui.MainView;
import com.vaadin.componentfactory.Breadcrumb;
import com.vaadin.componentfactory.Breadcrumbs;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.util.ArrayList;
import java.util.List;


/**
 *  @author Monika Martius, Jessica Reistel
 *  @version 5.0
 *  @since 19.01.2021
 *  @lastUpdated 08.02.2021 by Monika Martius
 */
@Route(value = "noticeBoard", layout = MainView.class)
@PageTitle("Schwarzes Brett")

public class NoticeBoardView extends Div {
    private NoticeBoardOfferEntity noticeBoardOfferEntity;
    private NoticeBoardOfferService noticeBoardOfferService;
    private UserService userService;
    private H1 pageTitle;
    private Paragraph pageText;
    private PageEntity pageEntity;
    private Upload uploadButton;
    private HorizontalLayout layoutSplit;
    private RadioButtonGroup radioOffer;
    private Button toAdd;
    private List<NoticeBoardOfferEntity> noticeBoardList;
    private Grid<NoticeBoardOfferEntity> noticeBoardGrid;
    private PageService pageService;
    private Component leftComponent;
    private Component rightComponent;
    private Breadcrumbs breadcrumbs;

    public NoticeBoardView(PageService pageService, NoticeBoardOfferService noticeBoardOfferService,UserService userService) {
        this.noticeBoardOfferService = noticeBoardOfferService;
        this.pageService = pageService;
        this.userService = userService;
        setId("noticeBoard");
        setClassName("pageContentPosition");
        addClassName("communityColorscheme");

        int maxId = noticeBoardOfferService.findMaxId();
        pageEntity = this.pageService.findPageById(20);
        noticeBoardList = new ArrayList<>();
        for(int i= 1;i<=maxId;i++){
            if(this.noticeBoardOfferService.findById(i) != null){
                noticeBoardOfferEntity = this.noticeBoardOfferService.findById(i);
                noticeBoardList.add(noticeBoardOfferEntity);
            }
        }
        pageTitle = new H1(pageEntity.getTitle());
        pageText = new Paragraph(pageEntity.getContent());

        breadcrumbs = new Breadcrumbs();
        breadcrumbs.add(new Breadcrumb("Home"), new Breadcrumb("Bibliothek"), new Breadcrumb(pageEntity.getTitle()));

        initializeGrid();
        initializeLeftContainer();
        initializeRightContainer();
        initializeSplitLayout();
    }

    /**
     * The method initialize a Vertical Layout with pageText,boxIcon,noticeBoardGrid
     * @return Vertical Layout
     */
    public void initializeLeftContainer() {
        GetUserController getUserController = new GetUserController();
        String username = getUserController.getUsername();
        UserEntity userEntity = userService.findByUsername(username);
        RoleEntity roleEntity = userEntity.getRole();
        int role = roleEntity.getRoleId();

        Div box = new Div();
        box.add(breadcrumbs, pageTitle);
        box.setId("layoutBox");

        Div boxIcon = new Div();
        Icon deleteIcon = new Icon(VaadinIcon.TRASH);
        deleteIcon.setId("deleteIcon");
        Icon editIcon = new Icon(VaadinIcon.EDIT);
        editIcon.setId("editIcon");
        boxIcon.add(editIcon,deleteIcon);

        if (role != 1){
            boxIcon.setVisible(false);
        }

        this.add(box);
        leftComponent = new VerticalLayout(pageText,boxIcon,noticeBoardGrid);
        leftComponent.setId("leftLayout");
    }

    /**
     * The method initialize the Grid with all offers
     * @return initializeGrid
     */
    public void initializeGrid(){
        noticeBoardGrid = new Grid<>();
        noticeBoardGrid.setItems(noticeBoardList);
        noticeBoardGrid.setSelectionMode(Grid.SelectionMode.NONE);
        noticeBoardGrid.addColumn(NoticeBoardOfferEntity::getCategory, "Suche/Biete").setHeader("Suche/Biete").setFooter("Gesamt: " + noticeBoardList.size());
        noticeBoardGrid.addColumn(NoticeBoardOfferEntity::getTitle, "Titel").setHeader("Titel");
        noticeBoardGrid.addColumn(NoticeBoardOfferEntity::getDescription).setHeader("Beschreibung");
        noticeBoardGrid.addColumn(NoticeBoardOfferEntity::getPrice, "Preis").setHeader("Preis");
        noticeBoardGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    /**
     * The method initialize a Vertical Layout with radioOffer,textTitle,textArea,price,uploadButton,toAdd
     * @return Vertical Layout
     */
    public void initializeRightContainer(){
        initializeUploadButton();
        initializeRadioButton();
        IntegerField price = new IntegerField("Preis");
        TextField textTitle = new TextField("Titel");
        TextArea textArea = new TextArea("Beschreibung");
        textArea.setId("styleTextArea");
        textArea.setPlaceholder("Anzeige aufgeben ...");
        toAdd = new Button();
        toAdd.setText("Hinzufügen");
        toAdd.setId("layoutSetID");
        rightComponent = new VerticalLayout(radioOffer,textTitle,textArea,price,uploadButton,toAdd);
        rightComponent.getElement().getStyle().set("width", "50%");
        rightComponent.setId("rightComponent");
    }

    /**
     * The method initialize a Horizontal Layout with two vertical Layouts
     * @return Horizontal Layout
     */
    public void initializeSplitLayout(){
        layoutSplit = new HorizontalLayout(leftComponent,rightComponent);
        layoutSplit.setId("splitLayout");
        this.add(layoutSplit);
    }

    /**
     * The method initialize a Upload with a button and a Span
     * @return Upload
     */
    public void initializeUploadButton(){
        MemoryBuffer buffer = new MemoryBuffer();
        uploadButton = new Upload(buffer);
        uploadButton.setUploadButton(new Button("Bild hochladen"));
        uploadButton.setDropLabel(new Span("Bild-Datei hier reinziehen"));
    }

    /**
     * The method initialize a RadioButtonGroup with a label and items
     * @return RadioButtonGroup
     */
    public void initializeRadioButton(){
        radioOffer = new RadioButtonGroup();
        radioOffer.setLabel("Was möchten Sie?");
        radioOffer.setItems("Bieten", "Suchen");
    }
}
