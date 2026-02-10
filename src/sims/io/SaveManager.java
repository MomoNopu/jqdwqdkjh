package sims.io;

import sims.model.NeedType;
import sims.sim.Sim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveManager {
    public void save(Sim sim, Path savePath) throws IOException {
        List<String> lines = List.of(
                "name=" + sim.getName(),
                "day=" + sim.getDay(),
                "hour=" + sim.getHour(),
                "money=" + sim.getMoney(),
                "hunger=" + sim.getNeed(NeedType.HUNGER),
                "energy=" + sim.getNeed(NeedType.ENERGY),
                "hygiene=" + sim.getNeed(NeedType.HYGIENE),
                "fun=" + sim.getNeed(NeedType.FUN),
                "social=" + sim.getNeed(NeedType.SOCIAL),
                "bladder=" + sim.getNeed(NeedType.BLADDER),
                "careerLevel=" + sim.getCareer().getLevel(),
                "careerPerformance=" + sim.getCareer().getPerformance(),
                "friendship=" + sim.getRelationship().getFriendship()
        );
        Files.write(savePath, lines);
    }

    public Sim load(Path savePath) throws IOException {
        List<String> lines = Files.readAllLines(savePath);
        Map<String, String> kv = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split("=", 2);
            if (split.length == 2) {
                kv.put(split[0].trim(), split[1].trim());
            }
        }

        String name = required(kv, "name");
        Sim sim = new Sim(name);
        sim.loadState(
                parseInt(kv, "day"),
                parseInt(kv, "hour"),
                parseInt(kv, "money"),
                parseInt(kv, "hunger"),
                parseInt(kv, "energy"),
                parseInt(kv, "hygiene"),
                parseInt(kv, "fun"),
                parseInt(kv, "social"),
                parseInt(kv, "bladder"),
                parseInt(kv, "careerLevel"),
                parseInt(kv, "careerPerformance"),
                parseInt(kv, "friendship")
        );

        return sim;
    }

    private static int parseInt(Map<String, String> kv, String key) {
        return Integer.parseInt(required(kv, key));
    }

    private static String required(Map<String, String> kv, String key) {
        String value = kv.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing field in save file: " + key);
        }
        return value;
    }
}
