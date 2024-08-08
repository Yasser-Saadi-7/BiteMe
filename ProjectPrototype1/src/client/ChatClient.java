package client;

import ocsf.client.*;
import logic.CreateAccount;
import logic.Dish;
import logic.MealsType;
import logic.Message1;
import logic.MessageType;
import logic.Order;
import logic.Restaurant;
import logic.Selection;


import java.io.*;
import java.util.ArrayList;

import common.ChatIF;

public class ChatClient extends AbstractClient {
    // Instance variables **********************************************
    ChatIF clientUI; 
    public static Order o1 = new Order(null, null, null, null, null);
    public static boolean awaitResponse = true;
    public static String logIn = "error";
    public static String CheckUserIdResponse = null;
    public static ArrayList<Order> list;
    public static ArrayList<Restaurant> resList;
    public static ArrayList<MealsType> mealsTypeList;
    public static ArrayList<Dish> dishesList;
    public static ArrayList<Selection> selectionsList;
    public static ArrayList<CreateAccount> accountList;
    // Constructors ****************************************************
    public ChatClient(String host, int port, ChatIF clientUI) 
        throws IOException {
        super(host, port); // Call the superclass constructor
        this.clientUI = clientUI;
    }

    // Instance methods ************************************************
    public void handleMessageFromServer(Object msg) {
        System.out.println("--> handleMessageFromServer");
        awaitResponse = false;
        Message1 m = (Message1) msg;
        
        switch (m.getMessageType()) {
            case searchOrder:
                Order order = (Order) m.getObject();
                if (order != null) {
                    o1.setListNumber(order.getListNumber());
                    o1.setOrderAddress(order.getOrderAddress());
                    o1.setOrederNumber(order.getOrederNumber());
                    o1.setRestaurant(order.getRestaurant());
                    o1.setTprice(order.getTprice());
                } else {
                    o1.setOrederNumber("Error");
                }
                break;
            case viewOrdersList:
                list = (ArrayList<Order>) m.getObject();
                break;
            case logIn:
                logIn = (String) m.getObject();
                break;
            case showRestaurant:
                resList = (ArrayList<Restaurant>) m.getObject();
                break;
            case mealsType:
                mealsTypeList = (ArrayList<MealsType>) m.getObject();
                break;
            case dishes:
                dishesList = (ArrayList<Dish>) m.getObject();
                break;
            case selections:
                selectionsList = (ArrayList<Selection>) m.getObject();
                break;
                
            case createAccount: 
                // Store the server response for account creation
                CheckUserIdResponse = (String) m.getObject();
                System.out.println("Server response: " + CheckUserIdResponse); // DEBUG
                awaitResponse = false; // Signal that the response has been received
                clientUI.display(CheckUserIdResponse); // Display the response to the user
                break;
                
            default:
                break;
        }
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            openConnection(); // in order to send more than one message
            awaitResponse = true;
            sendToServer(message);
            // Wait for response
            while (awaitResponse) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    public void createAccount(CreateAccount account) {
        handleMessageFromClientUI(new Message1(MessageType.createAccount, account));
    }


    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {}
        System.exit(0);
    }
}
