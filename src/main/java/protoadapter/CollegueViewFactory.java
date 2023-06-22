package protoadapter;

import strategyvisualizer.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Il CollegueViewFactory fornisce la strategia di visualizzazione delle liste di domande o appelli.
 */
public enum CollegueViewFactory {

    FACTORY;

    Map<Class<? extends Model>, Strategy> mappa = new HashMap<>();

    public Strategy createViewStrategy(Class<? extends Model> clazz){
        return mappa.get(clazz);
    }

    public void installView(Class<? extends Model> clazz, Strategy strategiaView){
        mappa.put(clazz,strategiaView);
    }
}
