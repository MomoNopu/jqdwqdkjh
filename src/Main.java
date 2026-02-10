import sims.engine.SimsGame;
import sims.io.ConsoleIO;

public class Main {
    public static void main(String[] args) {
        SimsGame game = new SimsGame(new ConsoleIO());
        game.run();
    }
}
