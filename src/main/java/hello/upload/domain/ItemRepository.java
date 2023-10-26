package hello.upload.domain;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private AtomicLong sequence = new AtomicLong();

    public void save(Item item) {
        item.setId(sequence.incrementAndGet());
        store.put(item.getId(), item);
    }

    public Item findById(Long id) {
        return store.get(id);
    }

}
