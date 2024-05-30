package org.MathEngine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.Arrays;

//@SuppressWarnings("ALL")
public class MathEngine {
    private static final Logger logger = LogManager.getLogger(MathEngine.class.getName());
    private static MathEngine instance;

    private MathEngine() {}

    public static MathEngine getInstance() {
        if(instance == null) {
            instance = new MathEngine();
        }
        return instance;
    }

    //  Метод прямоугольника
    public double methodRectangle(String expr, double a, double b, int n, boolean check) throws MathException {
        double[] values = new double[n];
        double dx = (b - a) / n;

        if(check) {             //С недостатком
            for (int i = 0; i < n; i++) {
                values[i] = getResultFunc(expr, a + i * dx);
            }
        } else {                //С избытком
            for (int i = 0; i < n; i++) {
                values[i] = getResultFunc(expr, a + (i+1) * dx);
            }
        }
        return dx * Arrays.stream(values).sum();
    }

    private double getResultFunc(String expr, double x) {
        try {
            ExprEvaluator util = new ExprEvaluator();
                util.eval("x= "+x);
            return util.evalf(expr);
        } catch (SyntaxError e) {
            logger.error("Произошла ошибка {}", e);
        } catch (MathException e) {
            logger.error("Произошла ошибка {}", e);
        }
        return Double.NaN;
    }

    //Метод трапеций
    public double methodTrapeze(String expr, double a, double b, int n) throws MathException {
        double[] values = new double[n+1];
        double h = (b-a)/n;
        values[0] = a;

        for (int i = 1; i <= n; i++) {
            values[i] = values[i-1] + h;
        }
        for (int i = 0; i <= n; i++) {
            values[i] = getResultFunc(expr, values[i]);
        }

        return h * ((values[0] + values[n])/2 + Arrays.stream(values).sum()-(values[0]+values[n]));
    }

    public double getAbsoluteValue(String expr, double a, double b) {
        try {
            ExprEvaluator util = new ExprEvaluator();
            return util.evalf(String.format("Integrate(%s, {x,%s,%s})", expr, a, b));
        } catch (SyntaxError e) {
            logger.error("Произошла ошибка {}", e);
        } catch (MathException e) {
            logger.error("Произошла ошибка {}", e);
        }
        return Double.NaN;
    }
}