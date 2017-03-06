import client.Client;
import server.Server;
import service.DBPlayers;
import service.DaoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Taras on 02.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        verse1();

    }


    public static void verse1() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Choose server  \" S \" or client \" C \"  ");

        while (true) {
            String answer = "";
            try {
                answer = reader.readLine().toLowerCase();

                if (answer.equals("s")) {
                    new Server();
                    break;
                } else if (answer.equals("c")) {
                    new Client();
                    break;
                } else
                    System.out.println("Wrong symbol. Please, enter correct symbol ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Сервер грузится собирает в себя кеш с бд
//    метод провайдер
    // новый юзер обращается к серверу для регистрации или логина

    private static void getUsersFromDB() {
        DBPlayers.playersList = new DaoService().getAllPlayers();

        System.out.println(DBPlayers.playersList);
    }
}
/*
-стартует сервер и забирает в себя кеш БД.


2.юзер получает от сервера "Введите логин и пароль"
3. вводит и отправляет данные на сервер
- сервер проверяет есть ли в кеше такой юзер и таким паролем
- если есть - оповещает все конекшены что добавился новый персонаж, если нет то создает и оповещает

 */