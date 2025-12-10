package games.board;

public class NoisyEvaluator implements StaticEvaluator {

    StaticEvaluator evaluator;
    static double noiseFactor = 0.1;

    public NoisyEvaluator(StaticEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public double evaluate(int[][] board) {
        return evaluator.evaluate(board) + noiseFactor * (Math.random() - 0.5);
    }
}
