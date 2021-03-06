package test.calculation;

import controller.CalcController;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

/**
 * JUnit 测试类,测试Calculation类
 */
public class CalculationTest {

    @Test
    public void test() {
        char[] arr = {'+', '-', '*', '/'};
        CalcController c = CalcController.getInstance();
        Assert.assertTrue(generateExpressionsAndTest(arr,c,100));
    }

    @Test
    public void testSingle(){
        CalcController c = CalcController.getInstance();
        c.updateModel("9");
    }

    private static boolean generateExpressionsAndTest(char[] operatorsArray, CalcController controller, int howManyTimes) {
        boolean returnBooleanValue = true;
        Random random = new Random(Calendar.getInstance().getTimeInMillis());
        for (int i = 0; i < howManyTimes; i++) {
            StringBuilder expression = new StringBuilder(random.nextInt(50) + 1 + "");
            int howManyInBrack = random.nextInt(9) + 2;
            for (int j = 0; j < howManyInBrack; j++) {
                if (random.nextBoolean()) {
                    int cu = random.nextInt(4);
                    expression.append(String.valueOf(operatorsArray[cu]));
                    expression.append('(');
                    int kb = random.nextInt(4) + 1;
                    boolean isFirst = true;
                    for (int k = 0; k < kb; k++) {
                        cu = random.nextInt(4);
                        if (!isFirst) {
                            expression.append(operatorsArray[cu]);
                        } else {
                            isFirst = false;
                        }
                        expression.append(random.nextInt(50) + 1);
                    }
                    expression.append(')');
                    expression.append(String.valueOf(operatorsArray[cu])).append(random.nextInt(50) + 1);
                }
                int whichSymbolToAppend = random.nextInt(4);
                expression.append(String.valueOf(operatorsArray[whichSymbolToAppend])).append(random.nextInt(50) + 1);
            }
            System.out.println(expression);
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            try {
                String jsCalcResult;
                try {
                    jsCalcResult = (new BigDecimal(String.valueOf(engine.eval(expression.toString())))).toPlainString();
                } catch (Exception e) {
                    jsCalcResult = String.valueOf(engine.eval(expression.toString()));
                }
                System.out.print(jsCalcResult + "      ");
                try {
                    controller.updateModel(expression.toString());
                    var myControllerCalcResult = controller.updateView();
                    BigDecimal jsCalcDecimal = new BigDecimal(jsCalcResult);
                    BigDecimal myControllerCalcDecimal = myControllerCalcResult;
                    returnBooleanValue = myControllerCalcDecimal.subtract(jsCalcDecimal).compareTo(new BigDecimal(0.1)) == -1;
                    System.out.println(myControllerCalcResult);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    if (jsCalcResult.contains("infinity") && e.getMessage().contains("零")) {
                        returnBooleanValue = true;
                    } else {
                        returnBooleanValue = false;
                    }
                }

            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        System.out.println(returnBooleanValue);
        return returnBooleanValue;
    }
}
