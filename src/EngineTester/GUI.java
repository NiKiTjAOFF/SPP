package EngineTester;

import Particles.ParticleSystem;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener, ItemListener, ChangeListener {
    //---Отображение излучателя частиц---
    public static Canvas canvas;

    //---Панель настроек свойств и параметров частицы---
    public static JPanel settingsPanel;

    //---Панель выбора направления излучателя---
    public static JPanel directionPanel;
    //Координата X направления излучателя
    public static JLabel directionXLabel;
    public static JSlider directionXSlider;
    public static JSpinner directionXSpinner;
    //Координата Y направления излучателя
    public static JLabel directionYLabel;
    public static JSlider directionYSlider;
    public static JSpinner directionYSpinner;
    //Координата Z направления излучателя
    public static JLabel directionZLabel;
    public static JSlider directionZSlider;
    public static JSpinner directionZSpinner;
    private static Vector3f direction;
    public static JLabel directionErrorLabel;
    public static JSlider directionErrorSlider;
    public static JSpinner directionErrorSpinner;
    private static float directionError;

    //---Панель выбора позиции излучателя---
    public static JPanel spawnPointPanel;
    //Координата X позиции излучателя
    public static JLabel spawnPointXLabel;
    public static JSlider spawnPointXSlider;
    public static JSpinner spawnPointXSpinner;
    //Координата Y позиции излучателя
    public static JLabel spawnPointYLabel;
    public static JSlider spawnPointYSlider;
    public static JSpinner spawnPointYSpinner;
    //Координата Z позиции излучателя
    public static JLabel spawnPointZLabel;
    public static JSlider spawnPointZSlider;
    public static JSpinner spawnPointZSpinner;
    private static Vector3f particleEmittingCenter;

    //---Панель выбора верхних границ зоны спавна---
    public static JPanel positiveBoundsPanel;
    //Координата X верхней границы зоны спавна
    public static JLabel positiveBoundsXLabel;
    public static JSlider positiveBoundsXSlider;
    public static JSpinner positiveBoundsXSpinner;
    //Координата Y верхней границы зоны спавна
    public static JLabel positiveBoundsYLabel;
    public static JSpinner positiveBoundsYSpinner;
    public static JSlider positiveBoundsYSlider;
    //Координата Z верхней границы зоны спавна
    public static JLabel positiveBoundsZLabel;
    public static JSpinner positiveBoundsZSpinner;
    public static JSlider positiveBoundsZSlider;
    private static Vector3f particlePositiveBounds;

    //---Панель выбора нижних границ зоны спавна---
    public static JPanel negativeBoundsPanel;
    //Координата X нижней границы зоны спавна
    public static JLabel negativeBoundsXLabel;
    public static JSlider negativeBoundsXSlider;
    public static JSpinner negativeBoundsXSpinner;
    //Координата Y нижней границы зоны спавна
    public static JLabel negativeBoundsYLabel;
    public static JSlider negativeBoundsYSlider;
    public static JSpinner negativeBoundsYSpinner;
    //Координата Z нижней границы зоны спавна
    public static JLabel negativeBoundsZLabel;
    public static JSlider negativeBoundsZSlider;
    public static JSpinner negativeBoundsZSpinner;
    private static Vector3f particleNegativeBounds;

    //---Панель выбора точки притяжения---
    public static JPanel pullCenterPanel;
    //Координата X точки притяжения
    public static JLabel pullCenterXLabel;
    public static JSpinner pullCenterXSpinner;
    public static JSlider pullCenterXSlider;
    //Координата Y точки притяжеиния
    public static JLabel pullCenterYLabel;
    public static JSlider pullCenterYSlider;
    public static JSpinner pullCenterYSpinner;
    //Координата Z точки притяжения
    public static JLabel pullCenterZLabel;
    public static JSlider pullCenterZSlider;
    public static JSpinner pullCenterZSpinner;
    private static Vector3f pullCenter;

    //---Панель выбора свойств частицы---
    public static JPanel particlePropsPanel;
    //Количество частиц в секунду
    public static JPanel ppsPanel;
    public static JLabel ppsLabel;
    public static JSlider ppsSlider;
    public static JSpinner ppsSpinner;
    private static float pps;
    //Скорость частиц
    public static JPanel speedPanel;
    public static JLabel speedLabel;
    public static JSlider speedSlider;
    public static JSpinner speedSpinner;
    private static float speed;
    public static JPanel speedErrorPanel;
    public static JLabel speedErrorLabel;
    public static JSlider speedErrorSlider;
    public static JSpinner speedErrorSpinner;
    private static float speedError;
    //Влияние гравитации на частицу
    public static JPanel gravityComplientPanel;
    public static JLabel gravityComplientLabel;
    public static JSlider gravityComplientSlider;
    public static JSpinner gravityComplientSpinner;
    private static float gravityComplient;
    //Время жизни частицы
    public static JPanel lifeLengthPanel;
    public static JLabel lifeLengthLabel;
    public static JSlider lifeLengthSlider;
    public static JSpinner lifeLengthSpinner;
    private static float lifeLength;
    public static JPanel lifeLengthErrorPanel;
    public static JLabel lifeLengthErrorLabel;
    public static JSlider lifeLengthErrorSlider;
    public static JSpinner lifeLengthErrorSpinner;
    private static float lifeLengthError;
    //Множитель размера частицы
    public static JPanel scalePanel;
    public static JLabel scaleLabel;
    public static JSlider scaleSlider;
    public static JSpinner scaleSpinner;
    private static float scale;
    public static JPanel scaleErrorPanel;
    public static JLabel scaleErrorLabel;
    public static JSlider scaleErrorSlider;
    public static JSpinner scaleErrorSpinner;
    private static float scaleError;
    //Количество пульсаций в секунду:
    public static JPanel particlePulsesASecondPanel;
    public static JLabel particlePulsesASecondLabel;
    public static JSlider particlePulsesASecondSlider;
    public static JSpinner particlePulsesASecondSpinner;
    private static float particlePulsesASecond;


    //---Панель добавления/удаления цвета
    public static JPanel colorChooserPanel;
    //Кнопки добавления или удаления цвета
    public static JButton colorChooserButtonAdd;
    public static JButton colorChooserButtonRemove;
    private static ArrayList<Vector3f> colors;

    //---Панель управления состоянием излучателя---
    public static JPanel spawnPropertiesPanel;
    //
    public static JCheckBox randomRotationCheckBox;
    private static boolean randomRotation;
    //Тип излучателя
    public static JComboBox<String> particleEmittingTypeComboBox;
    private static int particleEmittingType;
    //Тип зоны спавна
    public static JComboBox<String> spawnAreaTypeComboBox;
    private static int spawnAreaType;
    //Тип границ спавна
    public static JComboBox<String> spawnBoundsComboBox;
    private static int spawnBounds;
    //Тип направления излучения
    public static JComboBox<String> directionTypeComboBox;
    private static int directionType;

    public GUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(255, 229, 180));
        this.setLayout(new GridLayout(1, 2, 0, 0));

        //---Панель настроек---
        settingsPanel = createPanel();

        //---Панель управления состоянием излучателя---
        spawnPropertiesPanel = createPanel(2, 3, 2, 2);
        //Случайный поворот
        randomRotationCheckBox = new JCheckBox("Turn on random particles rotation");
        randomRotationCheckBox.addItemListener(this);
        //Тип излучателя
        particleEmittingTypeComboBox = new JComboBox<>(new String[]{"Pulse", "Constant Emitting", });
        particleEmittingTypeComboBox.addActionListener(this);
        //Тип зоны спавна
        spawnAreaTypeComboBox = new JComboBox<>(new String[]{"Hard Area", "Soft area", "From Dot"});
        spawnAreaTypeComboBox.addActionListener(this);
        //Тип границ спавна
        spawnBoundsComboBox = new JComboBox<>(new String[]{"In Bounds"});
        spawnBoundsComboBox.addActionListener(this);
        //Тип направления излучения
        directionTypeComboBox = new JComboBox<>(new String[]{"To Dot", "Cone Direction", "Out Of Dot"});
        directionTypeComboBox.addActionListener(this);
        spawnPropertiesPanel.add(randomRotationCheckBox);
        spawnPropertiesPanel.add(particleEmittingTypeComboBox);
        spawnPropertiesPanel.add(spawnAreaTypeComboBox);
        spawnPropertiesPanel.add(spawnBoundsComboBox);
        spawnPropertiesPanel.add(directionTypeComboBox);

        //---Панель выбора направления движения---
        directionPanel = createPanel(4, 3, 2, 2);
        //Координата X направления излучателя
        directionXLabel = new JLabel("Direction X:");
        directionXSlider = createSlider(-1000, 1000, 0, 1, 25);
        directionXSlider.addChangeListener(this);
        directionXSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        directionPanel.add(directionXLabel);
        directionPanel.add(directionXSlider);
        directionPanel.add(directionXSpinner);
        //Координата Y направления излучателя
        directionYLabel = new JLabel("Direction Y:");
        directionYSlider = createSlider(-1000, 1000, 0, 1, 25);
        directionYSlider.addChangeListener(this);
        directionYSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        directionPanel.add(directionYLabel);
        directionPanel.add(directionYSlider);
        directionPanel.add(directionYSpinner);
        //Координата Z направления излучателя
        directionZLabel = new JLabel("Direction Z:");
        directionZSlider = createSlider(-1000, 1000, 0, 1, 25);
        directionZSlider.addChangeListener(this);
        directionZSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        directionPanel.add(directionZLabel);
        directionPanel.add(directionZSlider);
        directionPanel.add(directionZSpinner);
        directionErrorLabel = new JLabel("direction error:");
        directionErrorSlider = createSlider(0, 3141, 0, 1, 25);
        directionErrorSlider.addChangeListener(this);
        directionErrorSpinner = createSpinner(0.000f, 0.000f, 3.141f, 0.001f, 3);
        directionPanel.add(directionErrorLabel);
        directionPanel.add(directionErrorSlider);
        directionPanel.add(directionErrorSpinner);


        //---Панель выбора стартовой точки---
        spawnPointPanel = createPanel(3, 3, 2, 2);
        //Координата X позиции излучателя
        spawnPointXLabel = new JLabel("SpawnPoint X:");
        spawnPointXSlider = createSlider(-1000, 1000, 0, 1, 25);
        spawnPointXSlider.addChangeListener(this);
        spawnPointXSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        spawnPointPanel.add(spawnPointXLabel);
        spawnPointPanel.add(spawnPointXSlider);
        spawnPointPanel.add(spawnPointXSpinner);
        //Координата Y позиции излучателя
        spawnPointYLabel = new JLabel("SpawnPoint Y:");
        spawnPointYSlider = createSlider(-1000, 1000, 0, 1, 25);
        spawnPointYSlider.addChangeListener(this);
        spawnPointYSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        spawnPointPanel.add(spawnPointYLabel);
        spawnPointPanel.add(spawnPointYSlider);
        spawnPointPanel.add(spawnPointYSpinner);
        //Координата Z позиции излучателя
        spawnPointZLabel = new JLabel("SpawnPoint Z:");
        spawnPointZSlider = createSlider(-1000, 1000, 0, 1, 25);
        spawnPointZSlider.addChangeListener(this);
        spawnPointZSpinner = createSpinner(0f, -1.000f, 1.000f, 0.001f, 3);
        spawnPointPanel.add(spawnPointZLabel);
        spawnPointPanel.add(spawnPointZSlider);
        spawnPointPanel.add(spawnPointZSpinner);


        //---Панель выбора верхних границ зоны спавна---
        positiveBoundsPanel = createPanel(3, 3, 2, 2);
        //Координата X верхней границы зоны спавна
        positiveBoundsXLabel = new JLabel("Positive Bound X:");
        positiveBoundsXSlider = createSlider(-20000, 20000, 0, 1, 25);
        positiveBoundsXSlider.addChangeListener(this);
        positiveBoundsXSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        positiveBoundsPanel.add(positiveBoundsXLabel);
        positiveBoundsPanel.add(positiveBoundsXSlider);
        positiveBoundsPanel.add(positiveBoundsXSpinner);
        //Координата Y верхней границы зоны спавна
        positiveBoundsYLabel = new JLabel("Positive Bound Y:");
        positiveBoundsYSlider = createSlider(-20000, 20000, 0, 1, 25);
        positiveBoundsYSlider.addChangeListener(this);
        positiveBoundsYSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        positiveBoundsPanel.add(positiveBoundsYLabel);
        positiveBoundsPanel.add(positiveBoundsYSlider);
        positiveBoundsPanel.add(positiveBoundsYSpinner);
        //Координата Z верхней границы зоны спавна
        positiveBoundsZLabel = new JLabel("Positive Bound Z:");
        positiveBoundsZSlider = createSlider(-20000, 20000, 0, 1, 25);
        positiveBoundsZSlider.addChangeListener(this);
        positiveBoundsZSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        positiveBoundsPanel.add(positiveBoundsZLabel);
        positiveBoundsPanel.add(positiveBoundsZSlider);
        positiveBoundsPanel.add(positiveBoundsZSpinner);


        //---Панель выбора нижних границ зоны спавна---
        negativeBoundsPanel = createPanel(3, 3, 2, 2);
        //Координата X нижней границы зоны спавна
        negativeBoundsXLabel = new JLabel("Negative Bound X:");
        negativeBoundsXSlider = createSlider(-20000, 20000, 0, 1, 25);
        negativeBoundsXSlider.addChangeListener(this);
        negativeBoundsXSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        negativeBoundsPanel.add(negativeBoundsXLabel);
        negativeBoundsPanel.add(negativeBoundsXSlider);
        negativeBoundsPanel.add(negativeBoundsXSpinner);
        //Координата Y нижней границы зоны спавна
        negativeBoundsYLabel = new JLabel("Negative Bound Y:");
        negativeBoundsYSlider = createSlider(-20000, 20000, 0, 1, 25);
        negativeBoundsYSlider.addChangeListener(this);
        negativeBoundsYSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        negativeBoundsPanel.add(negativeBoundsYLabel);
        negativeBoundsPanel.add(negativeBoundsYSlider);
        negativeBoundsPanel.add(negativeBoundsYSpinner);
        //Координата Z нижней границы зоны спавна
        negativeBoundsZLabel = new JLabel("Negative Bound Z:");
        negativeBoundsZSlider = createSlider(-20000, 20000, 0, 1, 25);
        negativeBoundsZSlider.addChangeListener(this);
        negativeBoundsZSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        negativeBoundsPanel.add(negativeBoundsZLabel);
        negativeBoundsPanel.add(negativeBoundsZSlider);
        negativeBoundsPanel.add(negativeBoundsZSpinner);


        //---Панель выбора точки притяжения---
        pullCenterPanel = createPanel(3, 3, 2, 2);
        //Координата X точки притяжения
        pullCenterXLabel = new JLabel("Pull Center X:");
        pullCenterXSlider = createSlider(-50000, 50000, 0, 1, 25);
        pullCenterXSlider.addChangeListener(this);
        pullCenterXSpinner = createSpinner(0f, -50.000f, 50.000f, 0.001f, 3);
        pullCenterPanel.add(pullCenterXLabel);
        pullCenterPanel.add(pullCenterXSlider);
        pullCenterPanel.add(pullCenterXSpinner);
        //Координата Y точки притяжения
        pullCenterYLabel = new JLabel("Pull Center Y:");
        pullCenterYSlider = createSlider(-50000, 50000, 0, 1, 25);
        pullCenterYSlider.addChangeListener(this);
        pullCenterYSpinner = createSpinner(0f, -50.000f, 50.000f, 0.001f, 3);
        pullCenterPanel.add(pullCenterYLabel);
        pullCenterPanel.add(pullCenterYSlider);
        pullCenterPanel.add(pullCenterYSpinner);
        //Координата Z точки притяжения
        pullCenterZLabel = new JLabel("Pull Center Z:");
        pullCenterZSlider = createSlider(-50000, 50000, 0, 1, 25);
        pullCenterZSlider.addChangeListener(this);
        pullCenterZSpinner = createSpinner(0f, -50.000f, 50.000f, 0.001f, 3);
        pullCenterPanel.add(pullCenterZLabel);
        pullCenterPanel.add(pullCenterZSlider);
        pullCenterPanel.add(pullCenterZSpinner);


        //---Панель выбора свойств частицы---
        particlePropsPanel = createPanel(9, 1, 0, 2);
        //Количество частиц в секунду
        ppsPanel = createPanel(1, 3, 2, 0);
        ppsLabel = new JLabel("Particles per second:");
        ppsSlider = createSlider(1, 5000, 1, 1, 25);
        ppsSlider.addChangeListener(this);
        ppsSpinner = createSpinner(1, 1, 5000, 1);
        ppsPanel.add(ppsLabel);
        ppsPanel.add(ppsSlider);
        ppsPanel.add(ppsSpinner);
        particlePropsPanel.add(ppsPanel);
        //Скорость частиц
        speedPanel = createPanel(1, 3, 2, 0);
        speedLabel = new JLabel("Particle speed:");
        speedSlider = createSlider(0, 50000, 0, 1, 25);
        speedSlider.addChangeListener(this);
        speedSpinner = createSpinner(0f, 0.000f, 50.000f, 0.001f, 3);
        speedErrorPanel = createPanel(1, 3, 2,0);
        speedErrorLabel = new JLabel("Particle speed error:");
        speedErrorSlider = createSlider(0, 5000, 0, 1, 25);
        speedErrorSlider.addChangeListener(this);
        speedErrorSpinner = createSpinner(0.000f, 0.000f, 5.000f, 0.001f, 3);
        speedPanel.add(speedLabel);
        speedPanel.add(speedSlider);
        speedPanel.add(speedSpinner);
        speedErrorPanel.add(speedErrorLabel);
        speedErrorPanel.add(speedErrorSlider);
        speedErrorPanel.add(speedErrorSpinner);
        particlePropsPanel.add(speedPanel);
        particlePropsPanel.add(speedErrorPanel);
        //Влияние гравитации на частицу
        gravityComplientPanel = createPanel(1, 3, 2, 0);
        gravityComplientLabel = new JLabel("Gravity effect on a particle:");
        gravityComplientSlider = createSlider(-20000, 20000, 0, 1, 25);
        gravityComplientSlider.addChangeListener(this);
        gravityComplientSpinner = createSpinner(0f, -20.000f, 20.000f, 0.001f, 3);
        gravityComplientPanel.add(gravityComplientLabel);
        gravityComplientPanel.add(gravityComplientSlider);
        gravityComplientPanel.add(gravityComplientSpinner);
        particlePropsPanel.add(gravityComplientPanel);
        //Время жизни частицы
        lifeLengthPanel = createPanel(1, 3, 2, 0);
        lifeLengthLabel = new JLabel("Particle's lifetime:");
        lifeLengthSlider = createSlider(1, 10000, 1000, 1, 25);
        lifeLengthSlider.addChangeListener(this);
        lifeLengthSpinner = createSpinner(1.000f, 0.001f, 10.000f, 0.001f, 3);
        lifeLengthErrorPanel = createPanel(1, 3, 2 ,0);
        lifeLengthErrorLabel = new JLabel("Particle's lifetime error:");
        lifeLengthErrorSlider = createSlider(0, 5000, 0, 1, 25);
        lifeLengthErrorSlider.addChangeListener(this);
        lifeLengthErrorSpinner = createSpinner(0.000f, 0.000f, 5.000f, 0.001f, 3);
        lifeLengthPanel.add(lifeLengthLabel);
        lifeLengthPanel.add(lifeLengthSlider);
        lifeLengthPanel.add(lifeLengthSpinner);
        lifeLengthErrorPanel.add(lifeLengthErrorLabel);
        lifeLengthErrorPanel.add(lifeLengthErrorSlider);
        lifeLengthErrorPanel.add(lifeLengthErrorSpinner);
        particlePropsPanel.add(lifeLengthPanel);
        particlePropsPanel.add(lifeLengthErrorPanel);
        //Множитель размера частицы
        scalePanel = createPanel(1, 3, 2, 0);
        scaleLabel = new JLabel("Size scale:");
        scaleSlider = createSlider(1, 10000, 1000, 1, 25);
        scaleSlider.addChangeListener(this);
        scaleSpinner = createSpinner(1.000f, 0.001f, 10.000f, 0.001f, 3);
        scaleErrorPanel = createPanel(1, 3, 2, 0);
        scaleErrorLabel = new JLabel("Size scale error:");
        scaleErrorSlider = createSlider(0, 5000, 0, 1, 25);
        scaleErrorSlider.addChangeListener(this);
        scaleErrorSpinner = createSpinner(0.000f, 0.000f, 5.000f, 0.001f, 3);
        scalePanel.add(scaleLabel);
        scalePanel.add(scaleSlider);
        scalePanel.add(scaleSpinner);
        scaleErrorPanel.add(scaleErrorLabel);
        scaleErrorPanel.add(scaleErrorSlider);
        scaleErrorPanel.add(scaleErrorSpinner);
        particlePropsPanel.add(scalePanel);
        particlePropsPanel.add(scaleErrorPanel);
        //Количество пульсаций в секунду
        particlePulsesASecondPanel = createPanel(1, 3, 2,0);
        particlePulsesASecondLabel = new JLabel("Pulses a second:");
        particlePulsesASecondSlider = createSlider(1, 10000, 1000, 1, 25);
        particlePulsesASecondSlider.addChangeListener(this);
        particlePulsesASecondSpinner = createSpinner(1.000f, 0.001f, 10.000f, 0.001f, 3);
        particlePulsesASecondPanel.add(particlePulsesASecondLabel);
        particlePulsesASecondPanel.add(particlePulsesASecondSlider);
        particlePulsesASecondPanel.add(particlePulsesASecondSpinner);
        particlePropsPanel.add(particlePulsesASecondPanel);

        //---Панель добавления/удаления цвета
        colorChooserPanel = createPanel(1, 3, 2,0);
        //Кнопки добавления или удаления цвета
        colorChooserButtonAdd = new JButton("Add color");
        colorChooserButtonAdd.addActionListener(this);
        colorChooserButtonRemove = new JButton("Delete color");
        colorChooserButtonRemove.addActionListener(this);
        colorChooserPanel.add(colorChooserButtonAdd);
        colorChooserPanel.add(colorChooserButtonRemove);

        settingsPanel.add(spawnPropertiesPanel);
        settingsPanel.add(directionPanel);
        settingsPanel.add(spawnPointPanel);
        settingsPanel.add(positiveBoundsPanel);
        settingsPanel.add(negativeBoundsPanel);
        settingsPanel.add(pullCenterPanel);
        settingsPanel.add(pullCenterPanel);
        settingsPanel.add(particlePropsPanel);
        settingsPanel.add(colorChooserPanel);

        this.add(settingsPanel);
        canvas = new Canvas();
        canvas.setSize(DisplayManager.WIDTH, DisplayManager.HEIGHT);
        this.add(canvas);

        this.pack();
        this.setVisible(true);
        setDefaultValues();
    }

    private JSlider createSlider(int min, int max, int startValue, int minorTick, int majorTick) {
        JSlider slider = new JSlider(min, max, startValue);
        slider.setMinorTickSpacing(minorTick);
        slider.setMajorTickSpacing(majorTick);
        slider.setOpaque(false);
        return slider;
    }

    private JSpinner createSpinner(float startValue, float min, float max, float stepSize, int fractionDigits) {
        SpinnerNumberModel model = new SpinnerNumberModel(startValue, min, max, stepSize);
        JSpinner spinner = new JSpinner(model);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.000");
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(fractionDigits);
        spinner.setEditor(editor);
        return spinner;
    }
    private JSpinner createSpinner(int startValue, int min, int max, int stepSize) {
        SpinnerNumberModel model = new SpinnerNumberModel(startValue, min, max, stepSize);
        JSpinner spinner = new JSpinner(model);

        return spinner;
    }

    private JPanel createPanel(int rows, int cols, int hgap, int vgap) {
        JPanel panel = new JPanel(new GridLayout(rows, cols, hgap, vgap));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private void setDefaultValues() {
        direction = new Vector3f(directionXSlider.getValue() / 1000f, directionYSlider.getValue() / 1000f,
                directionZSlider.getValue() / 1000f);
        directionError = directionErrorSlider.getValue() / 1000f;
        particleEmittingCenter = new Vector3f(spawnPointXSlider.getValue() / 1000f,
                spawnPointYSlider.getValue() / 1000f, spawnPointZSlider.getValue() / 1000f);
        particlePositiveBounds = new Vector3f(positiveBoundsXSlider.getValue() / 1000f,
                positiveBoundsYSlider.getValue() / 1000f, positiveBoundsZSlider.getValue() / 1000f);
        particleNegativeBounds = new Vector3f(negativeBoundsXSlider.getValue() / 1000f,
                negativeBoundsYSlider.getValue() / 1000f, negativeBoundsZSlider.getValue() / 1000f);
        pullCenter = new Vector3f(pullCenterXSlider.getValue() / 1000f, pullCenterYSlider.getValue() / 1000f,
                pullCenterZSlider.getValue() / 1000f);
        pps = ppsSlider.getValue();
        speed = speedSlider.getValue() / 1000f;
        speedError = speedErrorSlider.getValue() / 1000f;
        gravityComplient = gravityComplientSlider.getValue() / 1000f;
        lifeLength = lifeLengthSlider.getValue() / 1000f;
        lifeLengthError = lifeLengthErrorSlider.getValue() / 1000f;
        scale = scaleSlider.getValue() / 1000f;
        scaleError = scaleErrorSlider.getValue() / 1000f;
        particlePulsesASecond = particlePulsesASecondSlider.getValue() / 1000f;
        colors = new ArrayList<>();
        particleEmittingType = particleEmittingTypeComboBox.getSelectedIndex();
        spawnAreaType = spawnAreaTypeComboBox.getSelectedIndex();
        spawnBounds = spawnBoundsComboBox.getSelectedIndex();
        directionType = directionTypeComboBox.getSelectedIndex();
        randomRotation = randomRotationCheckBox.isSelected();
    }

    public static void setParticle(ParticleSystem system) {
        system.setParticle(pps, speed, gravityComplient, lifeLength, scale, colors);
    }

    public static void setUtils(ParticleSystem system) {
        system.setUtils(direction, directionError, randomRotation, speedError, lifeLengthError, scaleError,
                particlePulsesASecond, particleEmittingCenter, particlePositiveBounds, particleNegativeBounds, pullCenter,
                particleEmittingType, spawnAreaType, spawnBounds, directionType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == particleEmittingTypeComboBox) {
            particleEmittingType = particleEmittingTypeComboBox.getSelectedIndex();
        }
        else if (e.getSource() == spawnAreaTypeComboBox) {
            spawnAreaType = spawnAreaTypeComboBox.getSelectedIndex();
        }
        else if (e.getSource() == spawnBoundsComboBox){
            spawnBounds = spawnBoundsComboBox.getSelectedIndex();
        }
        else if (e.getSource() == directionTypeComboBox) {
            directionType = directionTypeComboBox.getSelectedIndex();
        }
        else if (e.getSource() == colorChooserButtonAdd) {
            if (colors.size() > 10) {
                JOptionPane.showMessageDialog(this, "You can't add more, than 10 colors");
                return;
            }
            Color color = JColorChooser.showDialog(null, "Pick a color", Color.white);
            if (color == null) {
                return;
            }
            float r, g, b;
            r = (float) color.getRed() / 255;
            g = (float) color.getGreen() / 255;
            b = (float) color.getBlue() / 255;
            colors.add(new Vector3f(r, g, b));
        }
        else if (e.getSource() == colorChooserButtonRemove) {
            if(colors.isEmpty()) {
                JOptionPane.showMessageDialog(this, "There's no colors to remove");
                return;
            }
            colors.removeLast();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == randomRotationCheckBox) {
            if(randomRotationCheckBox.isSelected()) {
                randomRotation = true;
            }
            else {
                randomRotation = false;
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == directionXSlider) {
            directionXSpinner.setValue(directionXSlider.getValue() / 1000f);
            direction.x = directionXSlider.getValue() / 1000f;
        }
        else if (e.getSource() == directionYSlider) {
            directionYSpinner.setValue(directionYSlider.getValue() / 1000f);
            direction.y = directionYSlider.getValue() / 1000f;
        }
        else if (e.getSource() == directionZSlider) {
            directionZSpinner.setValue(directionZSlider.getValue() / 1000f);
            direction.z = directionZSlider.getValue() / 1000f;
        }
        else if (e.getSource() == directionErrorSlider) {
            directionErrorSpinner.setValue(directionErrorSlider.getValue() / 1000f);
            directionError = directionErrorSlider.getValue() / 1000f;
        }
        else if (e.getSource() == spawnPointXSlider) {
            spawnPointXSpinner.setValue(spawnPointXSlider.getValue() / 1000f);
            particleEmittingCenter.x = spawnPointXSlider.getValue() / 1000f;
        }
        else if (e.getSource() == spawnPointYSlider) {
            spawnPointYSpinner.setValue(spawnPointYSlider.getValue() / 1000f);
            particleEmittingCenter.y = spawnPointYSlider.getValue() / 1000f;
        }
        else if (e.getSource() == spawnPointZSlider) {
            spawnPointZSpinner.setValue(spawnPointZSlider.getValue() / 1000f);
            particleEmittingCenter.z = spawnPointZSlider.getValue() / 1000f;
        }
        else if (e.getSource() == positiveBoundsXSlider) {
            positiveBoundsXSpinner.setValue(positiveBoundsXSlider.getValue() / 1000f);
            particlePositiveBounds.x = positiveBoundsXSlider.getValue() / 1000f;
        }
        else if (e.getSource() == positiveBoundsYSlider) {
            positiveBoundsYSpinner.setValue(positiveBoundsYSlider.getValue() / 1000f);
            particlePositiveBounds.y = positiveBoundsYSlider.getValue() / 1000f;
        }
        else if (e.getSource() == positiveBoundsZSlider) {
            positiveBoundsZSpinner.setValue(positiveBoundsZSlider.getValue() / 1000f);
            particlePositiveBounds.z = positiveBoundsZSlider.getValue() / 1000f;
        }
        else if (e.getSource() == negativeBoundsXSlider) {
            negativeBoundsXSpinner.setValue(negativeBoundsXSlider.getValue() / 1000f);
            particleNegativeBounds.x = negativeBoundsXSlider.getValue() / 1000f;
        }
        else if (e.getSource() == negativeBoundsYSlider) {
            negativeBoundsYSpinner.setValue(negativeBoundsYSlider.getValue() / 1000f);
            particleNegativeBounds.y = negativeBoundsYSlider.getValue() / 1000f;
        }
        else if (e.getSource() == negativeBoundsZSlider) {
            negativeBoundsZSpinner.setValue(negativeBoundsZSlider.getValue() / 1000f);
            particleNegativeBounds.z = negativeBoundsZSlider.getValue() / 1000f;
        }
        else if (e.getSource() == pullCenterXSlider) {
            pullCenterXSpinner.setValue(pullCenterXSlider.getValue() / 1000f);
            pullCenter.x = pullCenterXSlider.getValue() / 1000f;
        }
        else if (e.getSource() == pullCenterYSlider) {
            pullCenterYSpinner.setValue(pullCenterYSlider.getValue() / 1000f);
            pullCenter.y = pullCenterYSlider.getValue() / 1000f;
        }
        else if (e.getSource() == pullCenterZSlider) {
            pullCenterZSpinner.setValue(pullCenterZSlider.getValue() / 1000f);
            pullCenter.z = pullCenterZSlider.getValue() / 1000f;
        }
        else if (e.getSource() == ppsSlider) {
            ppsSpinner.setValue(ppsSlider.getValue());
            pps = ppsSlider.getValue();
        }
        else if (e.getSource() == speedSlider) {
            speedSpinner.setValue(speedSlider.getValue() / 1000f);
            speed = speedSlider.getValue() / 1000f;
            if(speedError > speed) {
                speedErrorSlider.setValue((int) (speed * 1000));
                speedErrorSpinner.setValue(speed);
                speedError = speed;
            }
        }
        else if (e.getSource() == speedErrorSlider) {
            speedErrorSpinner.setValue(speedErrorSlider.getValue() / 1000f);
            speedError = speedErrorSlider.getValue() / 1000f;
            if(speedError > speed) {
                speedErrorSlider.setValue((int) (speed * 1000));
                speedErrorSpinner.setValue(speed);
                speedError = speed;
            }
        }
        else if (e.getSource() == gravityComplientSlider) {
            gravityComplientSpinner.setValue(gravityComplientSlider.getValue() / 1000f);
            gravityComplient = gravityComplientSlider.getValue() / 1000f;
        }
        else if (e.getSource() == lifeLengthSlider) {
            lifeLengthSpinner.setValue(lifeLengthSlider.getValue() / 1000f);
            lifeLength = lifeLengthSlider.getValue() / 1000f;
            if(lifeLengthError > lifeLength - 0.5) {
                lifeLengthErrorSlider.setValue((int) ((lifeLength - 0.5) * 1000));
                lifeLengthErrorSpinner.setValue(lifeLength - 0.5);
                lifeLengthError = lifeLength - 0.5f;
            }
        }
        else if (e.getSource() == lifeLengthErrorSlider) {
            lifeLengthErrorSpinner.setValue(lifeLengthErrorSlider.getValue() / 1000f);
            lifeLengthError = lifeLengthErrorSlider.getValue() / 1000f;
            if(lifeLengthError > lifeLength - 0.5) {
                lifeLengthErrorSlider.setValue((int) ((lifeLength - 0.5) * 1000));
                lifeLengthErrorSpinner.setValue(lifeLength - 0.5);
                lifeLengthError = lifeLength - 0.5f;
            }
        }
        else if (e.getSource() == scaleSlider) {
            scaleSpinner.setValue(scaleSlider.getValue() / 1000f);
            scale = scaleSlider.getValue() / 1000f;
            if(scaleError > scale - 0.5) {
                scaleErrorSlider.setValue((int) ((scale - 0.5) * 1000));
                scaleErrorSpinner.setValue(scale - 0.5);
                scaleError = scale - 0.5f;
            }
        }
        else if (e.getSource() == scaleErrorSlider) {
            scaleErrorSpinner.setValue(scaleErrorSlider.getValue() / 1000f);
            scaleError = scaleErrorSlider.getValue() / 1000f;
            if(scaleError > scale - 0.5) {
                scaleErrorSlider.setValue((int) ((scale - 0.5) * 1000));
                scaleErrorSpinner.setValue(scale - 0.5);
                scaleError = scale - 0.5f;
            }
        }
        else if (e.getSource() == particlePulsesASecondSlider) {
            particlePulsesASecondSpinner.setValue(particlePulsesASecondSlider.getValue() / 1000f);
            particlePulsesASecond = particlePulsesASecondSlider.getValue() / 1000f;
        }
    }
}