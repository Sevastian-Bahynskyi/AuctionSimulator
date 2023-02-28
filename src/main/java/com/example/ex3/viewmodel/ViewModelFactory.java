package com.example.ex3.viewmodel;

import com.example.ex3.model.AuctionModel;
import com.example.ex3.view.ViewHandler;

public class ViewModelFactory
{
    private AuctionViewModel auctionViewModel;


    public ViewModelFactory(AuctionModel model)
    {
        this.auctionViewModel = new AuctionViewModel(model);
    }


    public AuctionViewModel getAuctionViewModel()
    {
        return auctionViewModel;
    }
}
