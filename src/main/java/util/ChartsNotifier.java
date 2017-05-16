package util;

import dto.Item;

import java.util.ArrayList;
import java.util.List;

public class ChartsNotifier {


    List<CategoryListener> listeners = new ArrayList<>();

    public void notifyChange(Item item) {
        for (CategoryListener each : listeners) {
            each.onChangeCategory(item);
        }
    }

    public void addListener(CategoryListener listener) {
        listeners.add(listener);
    }

}
