package com.example.demo3.ui.views.main.customers;

import com.example.demo3.person.Person;
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
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

//for to accept and reject people (in same div as the view)
public class PersonForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField creditScore = new TextField("Credit Score");
    TextField age = new TextField("Age");
    ComboBox<Person.Status> status = new ComboBox<>("Status");

    Button accept = new Button("Accept");
    Button reject = new Button("Reject");
    Button close = new Button("Cancel");

    Binder<Person> binder = new BeanValidationBinder<>(Person.class);
    private Person person;

    public PersonForm(){
        addClassName("contact-form");
        binder.forField( creditScore )
                .withNullRepresentation( "" )
                .withConverter(
                        new StringToIntegerConverter( Integer.valueOf( 0 ), "integers only" ) )
                .bind( Person::getCreditScore, Person::setCreditScore );
        binder.forField( age )
                .withNullRepresentation( "" )
                .withConverter(
                        new StringToIntegerConverter( Integer.valueOf( 0 ), "integers only" ) )
                .bind( Person::getAge, Person::setAge );
        binder.bindInstanceFields(this);
        status.setItems(Person.Status.values());
        add(
            firstName,
            lastName,
            creditScore,
            age,
            status,
            createButtonsLayout()
        );
    }

    //create layout of buttons
    private Component createButtonsLayout() {
        accept.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reject.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        accept.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        accept.addClickListener(click -> validateAndAccept());
        reject.addClickListener(click -> validateAndReject());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> accept.setEnabled(binder.isValid()));

        return new HorizontalLayout(accept, reject, close);
    }

    //fire events that can be caught in the view
    private void validateAndAccept() {
        try {
            binder.writeBean(person);
            fireEvent(new AcceptEvent(this, person));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void validateAndReject() {
        try {
            binder.writeBean(person);
            fireEvent(new RejectEvent(this, person));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //set the contact in the binder
    public void setContact(Person contact) {
        this.person = contact;
        binder.readBean(contact);
    }

    //events class. used to communicate with the view
    public static abstract class PersonFormEvent extends ComponentEvent<PersonForm> {
        private Person contact;

        protected PersonFormEvent(PersonForm source, Person contact) {
            super(source, false);
            this.contact = contact;
        }

        public Person getContact() {
            return contact;
        }
    }

    public static class AcceptEvent extends PersonFormEvent {
        AcceptEvent(PersonForm source, Person contact) {
            super(source, contact);
        }
    }

    public static class RejectEvent extends PersonFormEvent {
        RejectEvent(PersonForm source, Person contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends PersonFormEvent {
        CloseEvent(PersonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
