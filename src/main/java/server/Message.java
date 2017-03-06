package server;

import java.io.Serializable;

/**
 * Created by Taras on 06.03.2017.
 * ДОделать чтобы чат общался абстрактными Меседжами а не текстовиками
 */
public class Message implements Serializable{
    private String login;
    private String message;
    private String[] users;

    //Конструктор, которым будет пользоваться клиент
    public Message(String login, String message){
        this.login = login;
        this.message = message;
    }

    //Конструктор, которым будет пользоваться сервер
    public Message(String login, String message, String[] users){
        this.login = login;
        this.message = message;
        this.users = users;
    }

    public void setOnlineUsers(String[] users) {
        this.users = users;
    }

    public String getLogin() {
        return this.login;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getUsers() {
        return this.users;
    }

}

