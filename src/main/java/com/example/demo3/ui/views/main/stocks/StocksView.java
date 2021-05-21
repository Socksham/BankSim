package com.example.demo3.ui.views.main.stocks;

import com.example.demo3.appuser.AppUser;
import com.example.demo3.loan.Loan;
import com.example.demo3.person.PersonService;
import com.example.demo3.stock.Stock;
import com.example.demo3.stock.StockService;
import com.example.demo3.stocktransactions.StockTransaction;
import com.example.demo3.stocktransactions.StockTransactionService;
import com.example.demo3.ui.MainLayout;
import com.example.demo3.ui.views.main.Template;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import java.util.List;

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

    public StocksView(StockTransactionService stockTransactionService, StockService stockService){
        this.stockService = stockService;
        this.stockTransactionService = stockTransactionService;

        setSizeFull();


        if(principal instanceof AppUser){
            this.appUser = ((AppUser) principal);
            this.username = appUser.getUsername();
        }
        form = new StocksForm();
        form.addListener(StocksForm.BuyEvent.class, this::buyStock);
        form.addListener(StocksForm.SellEvent.class, this::sellStock);
        form.addListener(StocksForm.CloseEvent.class, e -> closeEditor());
        configureGrid();
        Div content = new Div(grid2, form);
        content.setSizeFull();
        content.addClassName("content");
        add(getToolBar(), content);
        updateList();
        closeEditor();

    }

    private void buyStock(StocksForm.BuyEvent evt){
        stockTransactionService.save(new StockTransaction(appUser.getBank(), evt
        .getStock().getStock(), evt.getStock().getPricePerStock(), evt.getAmountStock(), StockTransaction.Status.BUY));

    }

    private void sellStock(StocksForm.SellEvent evt){
        stockTransactionService.save(new StockTransaction(appUser.getBank(), evt
                .getStock().getStock(), evt.getStock().getPricePerStock(), evt.getAmountStock(), StockTransaction.Status.SELL));

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
            form.setStock(stock, stock.getOpen(), stock.getHigh(), stock.getLow(), stock.getChangePercent());
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setStock(null, null, null, null, null);
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
            con.addRequestProperty("Referer", "google.com");        int responseCode = con.getResponseCode();
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
