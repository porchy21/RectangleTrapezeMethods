import org.MathEngine.MathEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matheclipse.parser.client.math.MathException;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

//@SuppressWarnings("ALL")
public class GUI extends JFrame {
    private static final Logger logger = LogManager.getLogger(GUI.class.getName());
    private final MathEngine mathEngine = MathEngine.getInstance();
    private JPanel mainPanel;
    private JTextField exprField;
    private JTextField stepField;
    private JTextField upBoundField;
    private JTextField downBoundField;
    private JRadioButton rectangleMethod;
    private JRadioButton trapezeMethod;
    private JCheckBox deficitCheckBox;
    private JCheckBox surplusCheckBox;
    private JButton runButton;

    public GUI() {
        super("MathEngine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(525, 300);
        this.setVisible(true);
        this.setContentPane(mainPanel);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        rectangleMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                trapezeMethod.setSelected(false);
                deficitCheckBox.setEnabled(true);
                surplusCheckBox.setEnabled(true);
                if(!deficitCheckBox.isSelected() && !surplusCheckBox.isSelected()) {
                    deficitCheckBox.setSelected(true);
                }
            }
        });
        trapezeMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rectangleMethod.setSelected(false);
                deficitCheckBox.setEnabled(false);
                surplusCheckBox.setEnabled(false);
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double result,
                       absoluteValue;
                try {
                    //NumberFormatException
                    String expr = exprField.getText().replaceAll(" ", "");
                    double a = Double.parseDouble(downBoundField.getText().replace(",", "."));
                    double b = Double.parseDouble(upBoundField.getText().replace(",", "."));
                    int step = Integer.parseInt(stepField.getText());

                    //MathException
                    if(rectangleMethod.isSelected()) {
                        result = mathEngine.methodRectangle(expr, a, b, step, deficitCheckBox.isSelected());
                    } else {
                        result = mathEngine.methodTrapeze(expr, a, b, step);
                    }
                    absoluteValue = mathEngine.getAbsoluteValue(expr, a, b);
//                    if(Double.isNaN(result)||Double.isNaN(absoluteValue)) {
//                        throw new MathException();
//                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Введены некорректные данные", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Произошла ошибка {}", e);
                    return;
                } catch (MathException e) {
                    JOptionPane.showMessageDialog(null, "Введена неккоректная функция", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Произошла ошибка {}", e);
                    return;
                }
                if(Double.isNaN(absoluteValue))
                    JOptionPane.showMessageDialog(null, String.format("Результат: %.4f%n", result), "Result", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, String.format("Результат: %.4f%n" +
                                                                                   "Абсолютная погрешность: %.3f%n" +
                                                                                   "Относительная погрешность: %.3f%%",
                        result, Math.abs(absoluteValue - result), Math.abs(absoluteValue - result) / Math.abs(absoluteValue)*100), "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        KeyListener decimalListener = new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String textField = ((JTextField)e.getSource()).getText();
                if(!(Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.' || e.getKeyChar() == '-' || e.getKeyChar() == ',')) {
                    e.consume();
                } else {
                    if(e.getKeyChar() == '.' || e.getKeyChar() == ',') {
                        if(textField.contains(".")) {
                            e.consume();
                        }
                    }
                    if(e.getKeyChar() == '-' && !textField.isEmpty()) {
                        e.consume();
                    }
                }
            }
        };
        downBoundField.addKeyListener(decimalListener);
        upBoundField.addKeyListener(decimalListener);
        stepField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }
}
