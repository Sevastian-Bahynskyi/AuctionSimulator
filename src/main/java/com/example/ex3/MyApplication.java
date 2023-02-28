package com.example.ex3;

import com.example.ex3.model.AuctionModel;
import com.example.ex3.model.AuctionModelManager;
import com.example.ex3.model.Bidder;
import com.example.ex3.view.ViewHandler;
import com.example.ex3.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MyApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ArrayList<Bidder> botBidders = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
        {
            String name = "Bot " + i;
            BotBidder botBidder = new BotBidder(name, 1_000_000);
            botBidders.add(botBidder);
            threads.add(new Thread(botBidder));
        }



        AuctionModel model = new AuctionModelManager(Data.auction(), botBidders);

        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);

        for (Thread t:threads)
        {
            t.start();
        }
    }
}
