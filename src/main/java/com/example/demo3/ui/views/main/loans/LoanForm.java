package com.example.demo3.ui.views.main.loans;

import com.example.demo3.loan.Loan;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.shared.Registration;

public class LoanForm extends FormLayout {
    TextField amountOfLoan = new TextField("Loan Amount");
    ComboBox<Loan.Status> status = new ComboBox<>("Status");

    Button accept = new Button("Accept");
    Button reject = new Button("Reject");
    Button close = new Button("Cancel");

    Binder<Loan> binder = new BeanValidationBinder<>(Loan.class);
    private Loan loan;

    public LoanForm(){
        addClassName("contact-form");
        binder.forField( amountOfLoan )
                .withNullRepresentation( "" )
                .withConverter(
                        new StringToDoubleConverter( Double.valueOf( 0 ), "doubles only" ) )
                .bind( Loan::getAmountOfLoan, Loan::setAmountOfLoan );
        binder.bindInstanceFields(this);
        status.setItems(Loan.Status.values());
        add(
                amountOfLoan,
                status,
                createButtonsLayout()
        );
    }

    private Component createButtonsLayout() {
        accept.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reject.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        accept.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        accept.addClickListener(click -> validateAndAccept());
        reject.addClickListener(click -> validateAndReject());
        close.addClickListener(click -> fireEvent(new LoanForm.CloseEvent(this)));

        binder.addStatusChangeListener(evt -> accept.setEnabled(binder.isValid()));

        return new HorizontalLayout(accept, reject, close);
    }

    private void validateAndAccept() {
        try {
            binder.writeBean(loan);
            fireEvent(new LoanForm.AcceptEvent(this, loan));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void validateAndReject() {
        try {
            binder.writeBean(loan);
            fireEvent(new LoanForm.RejectEvent(this, loan));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class LoanFormEvent extends ComponentEvent<LoanForm> {
        private Loan contact;

        protected LoanFormEvent(LoanForm source, Loan contact) {
            super(source, false);
            this.contact = contact;
        }

        public Loan getContact() {
            return contact;
        }
    }

    public static class AcceptEvent extends LoanForm.LoanFormEvent {
        AcceptEvent(LoanForm source, Loan contact) {
            super(source, contact);
        }
    }

    public static class RejectEvent extends LoanForm.LoanFormEvent {
        RejectEvent(LoanForm source, Loan contact) {
            super(source, contact);
        }
    }


    public static class CloseEvent extends LoanForm.LoanFormEvent {
        CloseEvent(LoanForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setContact(Loan contact) {
        this.loan = contact;
        binder.readBean(loan);
    }
}