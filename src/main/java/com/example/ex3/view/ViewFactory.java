package com.example.ex3.view;


import com.example.ex3.MyApplication;
import com.example.ex3.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;

public class ViewFactory
{
    public static final String AUCTION_MAIN = "auction_main";
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private AuctionViewController auctionViewController;
    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
    }

    public Region loadConvertView()
    {
        if (auctionViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AuctionView.fxml"));
            try {
                Region root = loader.load();
                auctionViewController = loader.getController();
                auctionViewController.init(viewHandler, viewModelFactory.getAuctionViewModel(), root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        auctionViewController.reset();
        return auctionViewController.getRoot();
    }

    public Region load(String id)
    {
        return switch (id)
                {
                    case AUCTION_MAIN -> loadConvertView();
                    default -> throw new IllegalArgumentException("Unknown view: " + id);
                };
    }
}
