package ru.otus.spring.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Сервис для работы с сообщениями.
 *
 * @author Mariya Tronina
 */
public class MessageServiceImpl implements MessageService {

    private final Scanner scanner;
    private final PrintStream out;

    public MessageServiceImpl(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    @Override
    public void outMessage(String message) {
        out.println(message);
    }

    @Override
    public void close() {
        scanner.close();
    }

    @Override
    public String getMessage() {
        return scanner.nextLine();
    }

}
