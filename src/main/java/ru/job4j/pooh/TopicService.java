package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("GET")) {
            var topic = topics.putIfAbsent(req.id(), new ConcurrentHashMap<>());
            if (topic == null || topic.get(req.name()) == null || topic.get(req.name()).isEmpty()) {
                return new Resp("", 400);
            }
            String text = topic.get(req.name()).poll();
            return new Resp(text, 200);
        }
        if (req.method().equals("POST")) {
            for (var value : topics.values()) {
                value.putIfAbsent(req.name(), new ConcurrentLinkedQueue<>());
                var queue = value.get(req.name());
                queue.add(req.text());
            }
            return new Resp("OK", 200);
        }
        return new Resp("", 400);
    }
}