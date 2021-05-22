package com.example.demo3.ui.views.main.stocks;

import com.example.demo3.loan.Loan;
import com.example.demo3.stock.Stock;
import com.example.demo3.ui.views.main.customers.PersonForm;
import com.example.demo3.ui.views.main.loans.LoanForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.shared.Registration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//form similar to PersonForm but to buy and sell stocks
public class StocksForm extends FormLayout {

    TextField stock = new TextField();
    TextField pricePerStock = new TextField();
    TextField amountOfStock = new TextField();

    Label stockNum = new Label();
    Label open = new Label();
    Label high = new Label();
    Label low = new Label();
    Label changePercent = new Label();

    Button buy = new Button("Buy");
    Button sell = new Button("Sell");
    Button close = new Button("Cancel");

    //sets all the textFields
    Binder<Stock> binder = new BeanValidationBinder<>(Stock.class);
    Stock stockSet;

    public StocksForm() {
        binder.forField(pricePerStock)
                .withNullRepresentation("")
                .withConverter(
                        new StringToDoubleConverter(Double.valueOf(0.0), "doubles only"))
                .bind(Stock::getPricePerStock, Stock::setPricePerStock);
        binder.bindInstanceFields(this);

        amountOfStock.setPlaceholder("Stock Amount...");

        add(stock, pricePerStock, amountOfStock, open, high, low, changePercent, stockNum, createButtonsLayout());
    }

    //sets the stock and the text fields
    public void setStock(Stock stock, String open,
                         String high, String low, String changePercent, int stockNum) throws InterruptedException, IOException, JSONException {
        this.stockSet = stock;
        binder.readBean(stockSet);
        this.open.setText("Open: " + open);
        this.high.setText("High " + high);
        this.low.setText("Low: " + low);
        this.changePercent.setText("Percent Change: " + changePercent);
        this.stockNum.setText("Amount of Stock: " + stockNum);

    }

    //create buttons
    private Component createButtonsLayout() {
        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sell.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        buy.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        buy.addClickListener(click -> validateAndBuy());
        sell.addClickListener(click -> validateAndSell());
        close.addClickListener(click -> fireEvent(new StocksForm.CloseEvent(this)));

        binder.addStatusChangeListener(evt -> buy.setEnabled(binder.isValid()));

        return new HorizontalLayout(buy, sell, close);
    }

    //buy and sell, fire events
    private void validateAndBuy(){
        //TODO: ADD BUSINESS LOGIC
        try{
            binder.writeBean(stockSet);
            fireEvent(new BuyEvent(this, stockSet));
        }catch (ValidationException e){
            e.printStackTrace();
        }
    }

    private void validateAndSell(){
        //TODO: ADD BUSINESS LOGIC
        try{
            binder.writeBean(stockSet);
            fireEvent(new SellEvent(this, stockSet));
        }catch (ValidationException e){
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    //fire events classes
    public class StockFormEvent extends ComponentEvent<StocksForm> {
        private Stock stockSet;

        public StockFormEvent(StocksForm source, Stock stock) {
            super(source, false);
            this.stockSet = stock;
        }

        public Stock getStock() {
            return stockSet;
        }

        public int getAmountStock(){
            System.out.println(amountOfStock.getValue());
            return Integer.parseInt(amountOfStock.getValue());
        }
    }

    public class BuyEvent extends StocksForm.StockFormEvent {
        BuyEvent(StocksForm source, Stock stock) {
            super(source, stock);
        }
    }

    public class SellEvent extends StocksForm.StockFormEvent {
        SellEvent(StocksForm source, Stock stock) {
            super(source, stock);
        }
    }


    public class CloseEvent extends StocksForm.StockFormEvent {
        CloseEvent(StocksForm source) {
            super(source, null);

        }
    }
}
