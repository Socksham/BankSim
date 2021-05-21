package com.example.demo3.ui.views.main.stocks;

import com.example.demo3.accounts.investingaccount.InvestingAccount;
import com.example.demo3.accounts.investingaccount.InvestingAccountRepository;
import com.example.demo3.accounts.investingaccount.InvestingAccountService;
import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.person.PersonService;
import com.example.demo3.stock.Stock;
import com.example.demo3.stock.StockService;
import com.example.demo3.stocktransactions.StockTransaction;
import com.example.demo3.stocktransactions.StockTransactionService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Component
@Scope("prototype")
@Route(value = "/stocks", layout = MainLayout.class)
@PageTitle("Bank | Stocks")
public class StocksView extends Template {
    TextField filterText = new TextField();
    StockTransactionService stockTransactionService;
    StockService stockService;
    Grid<Stock> grid2 = new Grid<>(Stock.class);
    StocksForm form;
    String username;
    AppUser appUser;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Button refresh = new Button("Refresh");
    Label portfolioValue = new Label();
    InvestingAccountService investingAccountService;

    public StocksView(StockTransactionService stockTransactionService, StockService stockService, InvestingAccountService investingAccountService) throws InterruptedException, JSONException, IOException {
        this.stockService = stockService;
        this.stockTransactionService = stockTransactionService;
        this.investingAccountService = investingAccountService;

        setSizeFull();

        if(principal instanceof AppUser){
            this.appUser = ((AppUser) principal);
            this.username = appUser.getUsername();
        }

        form = new StocksForm();
        form.addListener(StocksForm.BuyEvent.class, this::buyStock);
        form.addListener(StocksForm.SellEvent.class, this::sellStock);
        form.addListener(StocksForm.CloseEvent.class, e -> closeEditor());

        portfolioValue.setText("Portfolio Value: $" + value());

        configureGrid();

        refresh.addClickListener(click -> refreshAll());

        Div content = new Div(grid2, form);
        content.setSizeFull();
        content.addClassName("content");

        add(getToolBar(), portfolioValue, content);
        updateList();
        closeEditor();

    }

    private void buyStock(StocksForm.BuyEvent evt) {
        stockTransactionService.save(new StockTransaction(appUser.getBank(), evt
                .getStock().getStock(), evt.getStock().getPricePerStock(), evt.getAmountStock(), StockTransaction.Status.BUY));

        closeEditor();
    }

    private void sellStock(StocksForm.SellEvent evt){
        stockTransactionService.save(new StockTransaction(appUser.getBank(), evt
                .getStock().getStock(), evt.getStock().getPricePerStock(), evt.getAmountStock(), StockTransaction.Status.SELL));

        closeEditor();
    }

//    private double getPortfolioValue() throws IOException, InterruptedException, JSONException {
//        List<String> stockNames = new LinkedList<>();
//        List<Integer> numShares = new LinkedList<>();
//        List<Double> allStocksPrice = new LinkedList<>();
//        JSONObject object = new JSONObject();
//        List<StockTransaction> transactions = stockTransactionService.findAll(appUser.getBank());
//        for(int i = 0; i < transactions.size(); i++){
//            if(!stockNames.contains(transactions.get(i).getStock())) {
//                stockNames.add(transactions.get(i).getStock());
//                numShares.add(i, numShares.get(i) + 1);
//            }else{
//                if(transactions.get(i).getTransactionRole() == StockTransaction.Status.BUY){
//                    numShares.add(i, numShares.get(i) + 1);
//                }else{
//                    numShares.add(i, numShares.get(i) - 1);
//                }
//            }
//            while (true){
//                try {
//                    HttpRequest request = HttpRequest.newBuilder()
//                            .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + transactions.get(i).getStock() + "&token=bs14jlnrh5r8enj768q0"))
//                            .method("GET", HttpRequest.BodyPublishers.noBody())
//                            .build();
//                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//                    System.out.println(response.body());
//                    object = new JSONObject(response.body());
//                    break;
//                }catch (Error | IOException | InterruptedException | JSONException r){
//                    HttpRequest request = HttpRequest.newBuilder()
//                            .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + transactions.get(i).getStock() + "&token=bs14jlnrh5r8enj768q0"))
//                            .method("GET", HttpRequest.BodyPublishers.noBody())
//                            .build();
//                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//                    System.out.println(response.body());
//                    object = new JSONObject(response.body());
//                    break;
//                }
//            }
//            allStocksPrice.add(i, (Double) object.get("c"));
//
//        }
//        double val = 0;
//
//        for(int i = 0; i < numShares.size(); i++){
//            val += allStocksPrice.get(i) * numShares.get(i);
//        }
//        return val;
//    }

    private double value() throws InterruptedException, IOException, JSONException {
        System.out.println(appUser.getBank().getCashLeft());
        double cashLeft = appUser.getBank().getCashLeft();
        List<StockTransaction> transactions = stockTransactionService.findAll(appUser.getBank());
        for(StockTransaction transaction : transactions){
            if(transaction.getTransactionRole() == StockTransaction.Status.BUY){
                cashLeft -= (transaction.getAmount_of_stock() * transaction.getPricePerStock());
            }else{
                cashLeft += (transaction.getAmount_of_stock() * transaction.getPricePerStock());
            }
        }
        List<String> stockNames = new ArrayList<>();
        List<Double> stockPrices = new ArrayList<>();
        List<Integer> stockAmounts = new ArrayList<>();
        for(int i = 0; i < transactions.size(); i++){
            if(stockNames.contains((String) transactions.get(i).getStock())){
                stockNames.add(transactions.get(i).getStock());
                stockPrices.add(getStockPrice(transactions.get(i).getStock()));
                stockAmounts.add(transactions.get(i).getAmount_of_stock());
            }else{
                if(transactions.get(i).getTransactionRole() == StockTransaction.Status.BUY){
                    stockAmounts.set(i, stockAmounts.get(i) + transactions.get(i).getAmount_of_stock());
                }else{
                    stockAmounts.set(i, stockAmounts.get(i) - transactions.get(i).getAmount_of_stock());
                }
            }
        }
        for(int i = 0; i < stockNames.size(); i++){
            cashLeft += (stockAmounts.get(i) * stockPrices.get(i));
        }
        return cashLeft;
    }

    private double getStockPrice(String stock) throws JSONException, IOException, InterruptedException {
        JSONObject object;
        while (true) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + stock.toUpperCase(Locale.ROOT) + "&token=bs14jlnrh5r8enj768q0"))
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                object = new JSONObject(response.body());
                return (double) object.get("c");
            } catch (Error | IOException | InterruptedException | JSONException r) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + stock.toUpperCase(Locale.ROOT) + "&token=bs14jlnrh5r8enj768q0"))
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                object = new JSONObject(response.body());
                return (double) object.get("c");
            }
        }
    }

    private HorizontalLayout getToolBar(){
        filterText.setPlaceholder("Find stocks...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> {
            try {
                updateList(filterText.getValue());
            } catch (IOException | JSONException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void refreshAll(){
    }

    private void configureGrid(){
        grid2.setSizeFull();
        grid2.setColumns("stock", "pricePerStock");
        grid2.asSingleSelect().addValueChangeListener(evt -> showStock(evt.getValue()));
    }

    private void showStock(Stock stock){
        if(stock == null){
            closeEditor();
            //TODO: show notification
        }else{
            List<StockTransaction> transactions = stockTransactionService.findAll(appUser.getBank());
            int stockNum = 0;
            for(int j = 0; j < transactions.size(); j++){
                if(transactions.get(j).getTransactionRole() == StockTransaction.Status.BUY && transactions.get(j).getStock().equals(stock.getStock())){
                    stockNum += 1;
                }else if(transactions.get(j).getTransactionRole() == StockTransaction.Status.SELL && transactions.get(j).getStock().equals(stock.getStock())){
                    stockNum -= 1;
                }
            }
            form.setStock(stock, stock.getOpen(), stock.getHigh(), stock.getLow(), stock.getChangePercent(), stockNum);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setStock(null, null, null, null, null, 0);
        form.setVisible(false);
        updateList();
        removeClassName("editing");
    }

    private void updateList(String name) throws IOException, JSONException, InterruptedException {
        //TODO: get all stocks from api and if stock not in database then add it
        JSONArray objectArray = callApiTextSearch(name);
//        List<Stock> stocksInDatabase = stockService.findAll();
        for (int i = 0; i < objectArray.length(); i++)
        {
            JSONObject objectInArray = objectArray.getJSONObject(i);

            String symbol = (String) objectInArray.get("symbol");
            List<Stock> checkStock = stockService.findByName(symbol);
            if(checkStock.size() <= 0){
                JSONObject quote = (JSONObject) callApiPriceGetter(symbol).get("Global Quote");
                double price = Double.parseDouble((String) quote.get("05. price"));
                stockService.save(new Stock(symbol, price, (String)quote.get("02. open"),
                        (String)quote.get("03. high"), (String)quote.get("04. low"),
                        (String)quote.get("10. change percent")));
                System.out.println("ADDED STOCK " + symbol);
            }

        }
        grid2.setItems(stockService.findByName(name));
    }

    private void updateList() {
        grid2.setItems(stockService.findAll());
    }

    private JSONArray callApiTextSearch(String KEYWORD) throws IOException, JSONException {
        if(!KEYWORD.equals("")){
            String url = "https://ticker-2e1ica8b9.now.sh/keyword/" + KEYWORD;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            con.addRequestProperty("User-Agent", "Mozilla");
            con.addRequestProperty("Referer", "google.com");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            System.out.println(response.toString());
            //Read JSON response and print

            return new JSONArray(response.toString());
        }
        return null;
    }

    private JSONObject callApiPriceGetter(String KEYWORD) throws IOException, InterruptedException, JSONException {
        if(!KEYWORD.equals("")){
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + KEYWORD))
                        .header("x-rapidapi-key", "f4d6869ce0mshf4d311faa5eed26p1f9bd7jsn669ab8478190")
                        .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                return new JSONObject(response.body());
            }catch (Error e){
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + KEYWORD))
                        .header("x-rapidapi-key", "bcd2fe1ed7mshf603d481a9ebf09p17fb38jsn4167d3b93a31")
                        .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                return new JSONObject(response.body());
            }
        }
        return null;
    }

}
