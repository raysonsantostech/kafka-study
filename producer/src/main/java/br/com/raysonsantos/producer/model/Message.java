package br.com.raysonsantos.producer.model;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Message {

    private String identifier = UUID.randomUUID().toString();
    public String message;
    private final List<String> names = List.of("João", "Maria", "Pedro", "Ana", "Carlos");
    private final Random random = new Random();

    public Message() {
        this.message = "Hello, " + getName();
    }

    public Message(String message) {
        this.message = message;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getMessage() {
        return message;
    }

    private String getName() {
        return names.get(random.nextInt(names.size()));
    }


}
