package ru.job4j.pooh;

public class Req {
    private final String method;
    private final String mode;
    private final String name;
    private final String id;
    private final String text;

    private Req(String method, String mode, String name, String id, String text) {
        this.method = method;
        this.mode = mode;
        this.name = name;
        this.id = id;
        this.text = text;
    }

    public static Req of(String content) {
        String[] splitContent = content.split(" ");
        String[] splitContent1 = content.split("\n");
        String method = splitContent[0];
        String mode = splitContent[1].split("/")[1];
        String name = splitContent[1].split("/")[2];
        String id = "";
        String text = "";
        if (method.equals("POST")) {
            text = splitContent1[splitContent.length - 1];
        }
        if (mode.equals("topic") && method.equals("GET")) {
            id = splitContent[1].split("/")[3];
        }
        return new Req(method, mode, name, id, text);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String name() {
        return name;
    }

    public String id() {
        return id;
    }

    public String text() {
        return text;
    }
}