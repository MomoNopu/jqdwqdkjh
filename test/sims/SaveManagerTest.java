package sims;

import sims.io.SaveManager;
import sims.model.NeedType;
import sims.sim.Sim;

import java.nio.file.Files;
import java.nio.file.Path;

public class SaveManagerTest {
    public static void main(String[] args) throws Exception {
        testSaveAndLoadRoundTrip();
        System.out.println("SaveManagerTest passed");
    }

    private static void testSaveAndLoadRoundTrip() throws Exception {
        SaveManager manager = new SaveManager();
        Sim source = new Sim("Ava");
        source.advanceTime(5);
        source.addMoney(250);
        source.adjustNeed(NeedType.HUNGER, -30);
        source.getCareer().loadState(2, 48);
        source.getRelationship().loadState(88);

        Path path = Path.of("test_savegame.txt");
        manager.save(source, path);

        Sim loaded = manager.load(path);

        if (!"Ava".equals(loaded.getName())) {
            throw new AssertionError("Name not restored");
        }
        if (loaded.getDay() != source.getDay() || loaded.getHour() != source.getHour()) {
            throw new AssertionError("Clock not restored");
        }
        if (loaded.getMoney() != source.getMoney()) {
            throw new AssertionError("Money not restored");
        }
        if (loaded.getNeed(NeedType.HUNGER) != source.getNeed(NeedType.HUNGER)) {
            throw new AssertionError("Need not restored");
        }
        if (loaded.getCareer().getLevel() != source.getCareer().getLevel()) {
            throw new AssertionError("Career level not restored");
        }
        if (loaded.getRelationship().getFriendship() != source.getRelationship().getFriendship()) {
            throw new AssertionError("Friendship not restored");
        }

        Files.deleteIfExists(path);
    }
}
