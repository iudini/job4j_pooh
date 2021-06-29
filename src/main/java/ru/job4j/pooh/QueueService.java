package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("GET")) {
            var q = queue.get(req.name());
            if (q == null) {
                return new Resp("", 400);
            }
            String text = q.poll();
            return new Resp(text, 200);
        }
        if (req.method().equals("POST")) {
            ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
            q.add(req.text());
            queue.putIfAbsent(req.name(), q);
            return new Resp("", 200);
        }
        return new Resp("", 400);
    }
}