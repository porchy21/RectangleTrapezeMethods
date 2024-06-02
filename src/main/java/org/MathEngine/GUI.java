package org.MathEngine;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matheclipse.parser.client.math.MathException;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
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
    private JPanel panelBounds;
    private JPanel panelMethods;
    private JLabel FuncLabel;
    private JLabel a;
    private JLabel b;
    private JLabel n;

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
                if (!deficitCheckBox.isSelected() && !surplusCheckBox.isSelected()) {
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
                    if (rectangleMethod.isSelected()) {
                        result = mathEngine.methodRectangle(expr, a, b, step, deficitCheckBox.isSelected());
                    } else {
                        result = mathEngine.methodTrapeze(expr, a, b, step);
                    }
                    absoluteValue = mathEngine.getAbsoluteValue(expr, a, b);
                    if (Double.isNaN(result)) {
                        throw new MathException();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Введены некорректные данные", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Произошла ошибка {}", e);
                    return;
                } catch (MathException e) {
                    JOptionPane.showMessageDialog(null, "Введена неккоректная функция", "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Произошла ошибка {}", e);
                    return;
                }
                if (Double.isNaN(absoluteValue))
                    JOptionPane.showMessageDialog(null, String.format("Результат: %.4f%n", result), "Result", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, String.format("Результат: %.4f%n" +
                                    "Абсолютная погрешность: %.3f%n" +
                                    "Относительная погрешность: %.3f%%",
                            result, Math.abs(absoluteValue - result), Math.abs(absoluteValue - result) / Math.abs(absoluteValue) * 100), "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        KeyListener decimalListener = new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String textField = ((JTextField) e.getSource()).getText();
                if (!(Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.' || e.getKeyChar() == '-' || e.getKeyChar() == ',')) {
                    e.consume();
                } else {
                    if (e.getKeyChar() == '.' || e.getKeyChar() == ',') {
                        if (textField.contains(".")) {
                            e.consume();
                        }
                    }
                    if (e.getKeyChar() == '-' && !textField.isEmpty()) {
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
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(450, 500));
        mainPanel.setMinimumSize(new Dimension(450, 500));
        mainPanel.setPreferredSize(new Dimension(525, 300));
        FuncLabel = new JLabel();
        Font FuncLabelFont = this.$$$getFont$$$("Montserrat ExtraBold", Font.BOLD, 16, FuncLabel.getFont());
        if (FuncLabelFont != null) FuncLabel.setFont(FuncLabelFont);
        FuncLabel.setText("F =");
        mainPanel.add(FuncLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(27, 25), null, 0, false));
        exprField = new JTextField();
        Font exprFieldFont = this.$$$getFont$$$("Montserrat SemiBold", -1, 16, exprField.getFont());
        if (exprFieldFont != null) exprField.setFont(exprFieldFont);
        mainPanel.add(exprField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(10, 25), new Dimension(150, 25), null, 0, false));
        panelBounds = new JPanel();
        panelBounds.setLayout(new GridBagLayout());
        mainPanel.add(panelBounds, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 25), null, 0, false));
        a = new JLabel();
        Font aFont = this.$$$getFont$$$("Montserrat ExtraBold", Font.BOLD, 16, a.getFont());
        if (aFont != null) a.setFont(aFont);
        a.setText("a =");
        a.setVerticalAlignment(0);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelBounds.add(a, gbc);
        downBoundField = new JTextField();
        Font downBoundFieldFont = this.$$$getFont$$$("Montserrat Black", -1, 16, downBoundField.getFont());
        if (downBoundFieldFont != null) downBoundField.setFont(downBoundFieldFont);
        downBoundField.setMinimumSize(new Dimension(25, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBounds.add(downBoundField, gbc);
        b = new JLabel();
        Font bFont = this.$$$getFont$$$("Montserrat ExtraBold", Font.BOLD, 16, b.getFont());
        if (bFont != null) b.setFont(bFont);
        b.setText("b =");
        b.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelBounds.add(b, gbc);
        upBoundField = new JTextField();
        Font upBoundFieldFont = this.$$$getFont$$$("Montserrat Black", -1, 16, upBoundField.getFont());
        if (upBoundFieldFont != null) upBoundField.setFont(upBoundFieldFont);
        upBoundField.setMinimumSize(new Dimension(25, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBounds.add(upBoundField, gbc);
        n = new JLabel();
        Font nFont = this.$$$getFont$$$("Montserrat ExtraBold", Font.BOLD, 16, n.getFont());
        if (nFont != null) n.setFont(nFont);
        n.setText("n =");
        n.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelBounds.add(n, gbc);
        stepField = new JTextField();
        Font stepFieldFont = this.$$$getFont$$$("Montserrat Black", -1, 16, stepField.getFont());
        if (stepFieldFont != null) stepField.setFont(stepFieldFont);
        stepField.setMinimumSize(new Dimension(25, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBounds.add(stepField, gbc);
        panelMethods = new JPanel();
        panelMethods.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panelMethods, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rectangleMethod = new JRadioButton();
        Font rectangleMethodFont = this.$$$getFont$$$("Montserrat ExtraBold", -1, 16, rectangleMethod.getFont());
        if (rectangleMethodFont != null) rectangleMethod.setFont(rectangleMethodFont);
        rectangleMethod.setInheritsPopupMenu(false);
        rectangleMethod.setText("Метод прямоугольника");
        rectangleMethod.setVisible(true);
        panelMethods.add(rectangleMethod, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deficitCheckBox = new JCheckBox();
        deficitCheckBox.setEnabled(false);
        Font deficitCheckBoxFont = this.$$$getFont$$$("Montserrat ExtraBold", -1, 16, deficitCheckBox.getFont());
        if (deficitCheckBoxFont != null) deficitCheckBox.setFont(deficitCheckBoxFont);
        deficitCheckBox.setText("С недостатком");
        panelMethods.add(deficitCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        surplusCheckBox = new JCheckBox();
        surplusCheckBox.setEnabled(false);
        Font surplusCheckBoxFont = this.$$$getFont$$$("Montserrat ExtraBold", -1, 16, surplusCheckBox.getFont());
        if (surplusCheckBoxFont != null) surplusCheckBox.setFont(surplusCheckBoxFont);
        surplusCheckBox.setText("С избытком");
        panelMethods.add(surplusCheckBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        trapezeMethod = new JRadioButton();
        trapezeMethod.setEnabled(true);
        Font trapezeMethodFont = this.$$$getFont$$$("Montserrat ExtraBold", -1, 16, trapezeMethod.getFont());
        if (trapezeMethodFont != null) trapezeMethod.setFont(trapezeMethodFont);
        trapezeMethod.setSelected(true);
        trapezeMethod.setText("Метод трапеций");
        trapezeMethod.setVisible(true);
        panelMethods.add(trapezeMethod, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        runButton = new JButton();
        Font runButtonFont = this.$$$getFont$$$("Montserrat ExtraBold", -1, 16, runButton.getFont());
        if (runButtonFont != null) runButton.setFont(runButtonFont);
        runButton.setLabel("Вычислить");
        runButton.setText("Вычислить");
        panelMethods.add(runButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, 25), null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rectangleMethod);
        buttonGroup.add(rectangleMethod);
        buttonGroup.add(trapezeMethod);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(deficitCheckBox);
        buttonGroup.add(deficitCheckBox);
        buttonGroup.add(surplusCheckBox);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
