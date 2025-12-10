package ca.mss.rd.test.chart;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.demos.Showcase;
import info.monitorenter.gui.chart.io.RandomDataCollectorOffset;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyMinimumViewport;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.views.ChartPanel;
import info.monitorenter.util.Range;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public final class Chart2dShowcaseDemo extends javax.swing.JApplet {

	public Chart2dShowcaseDemo() {
    }


	final class ControlPanel extends javax.swing.JPanel {

        private javax.swing.JSlider m_amountPointsSlider;
        private javax.swing.JButton m_clear;
        private javax.swing.JComboBox m_colorChooser;
        private javax.swing.JSlider m_latencyTimeSlider;
        private javax.swing.JButton m_snapshot;
        private javax.swing.JButton m_startStop;

        private void createAmountPointSlider() {
            m_amountPointsSlider = new JSlider(10, 410);
            m_amountPointsSlider.setBackground(java.awt.Color.WHITE);
            int maxPoints = getTrace().getMaxSize();
            m_amountPointsSlider.setValue(maxPoints);
            m_amountPointsSlider.setMajorTickSpacing(40);
            m_amountPointsSlider.setMinorTickSpacing(20);
            m_amountPointsSlider.setSnapToTicks(true);
            m_amountPointsSlider.setPaintLabels(true);
            m_amountPointsSlider.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Amount of points.", 1, 3));
            m_amountPointsSlider.setPaintTicks(true);
            m_amountPointsSlider.addChangeListener(new javax.swing.event.ChangeListener() {

                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    javax.swing.JSlider source = (javax.swing.JSlider)e.getSource();
                    if(!source.getValueIsAdjusting()) {
                        int value = source.getValue();
                        getTrace().setMaxSize(value);
                    }
                }
            });
        }

        private void createClearButton() {
            m_clear = new JButton("clear");
            m_clear.setBackground(java.awt.Color.WHITE);
            m_clear.setBackground(java.awt.Color.WHITE);
            m_clear.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    clearTrace();
                }
            });
        }

        private void createColorChooserButton() {
            m_colorChooser = new JComboBox();
            m_colorChooser.setBackground(java.awt.Color.WHITE);
            final class ColorItem extends java.awt.Color {

                private java.lang.String m_name;

                public java.lang.String toString() {
                    return m_name;
                }

                public ColorItem(java.awt.Color c, java.lang.String name) {
                    super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                    m_name = name;
                }
            }

            m_colorChooser.addItem(new ColorItem(java.awt.Color.BLACK, "black"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.BLUE, "blue"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.CYAN, "cyan"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.DARK_GRAY, "darg gray"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.GRAY, "gray"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.GREEN, "green"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.LIGHT_GRAY, "light gray"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.MAGENTA, "magenta"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.ORANGE, "orange"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.PINK, "pink"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.RED, "red"));
            m_colorChooser.addItem(new ColorItem(java.awt.Color.YELLOW, "yellow"));
            m_colorChooser.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent ae) {
                    java.awt.Color color = (java.awt.Color)((javax.swing.JComboBox)ae.getSource()).getSelectedItem();
                    getTrace().setColor(color);
                }
            });
            m_colorChooser.setSelectedIndex(10);
            m_colorChooser.setMaximumSize(new Dimension(200, m_clear.getMaximumSize().height));
        }

        private void createLatencySlider() {
            m_latencyTimeSlider = new JSlider(10, 210);
            m_latencyTimeSlider.setBackground(java.awt.Color.WHITE);
            m_latencyTimeSlider.setValue((int)getCollector().getLatency());
            m_latencyTimeSlider.setMajorTickSpacing(50);
            m_latencyTimeSlider.setMinorTickSpacing(10);
            m_latencyTimeSlider.setSnapToTicks(true);
            m_latencyTimeSlider.setPaintLabels(true);
            m_latencyTimeSlider.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Latency for adding points.", 1, 3));
            m_latencyTimeSlider.setPaintTicks(true);
            m_latencyTimeSlider.addChangeListener(new javax.swing.event.ChangeListener() {

                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    javax.swing.JSlider source = (javax.swing.JSlider)e.getSource();
                    if(!source.getValueIsAdjusting()) {
                        int value = source.getValue();
                        getCollector().setLatency(value);
                    }
                }
            });
        }

        private void createSnapShotButton() {
            m_snapshot = new JButton(info.monitorenter.gui.chart.events.Chart2DActionSaveImageSingleton.getInstance(m_chart, "Save image"));
            m_snapshot.setBackground(java.awt.Color.WHITE);
        }

        private void createStartStopButton() {
            m_startStop = new JButton("start");
            m_startStop.setBackground(java.awt.Color.WHITE);
            m_startStop.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    javax.swing.JButton source = (javax.swing.JButton)e.getSource();
                    if(getCollector().isRunning()) {
                        stopData();
                        source.setText("start");
                    } else {
                        startData();
                        source.setText("stop");
                    }
                    source.invalidate();
                    source.repaint();
                }
            });
        }

        protected ControlPanel() {
            super();
            setBackground(java.awt.Color.WHITE);
            createAmountPointSlider();
            createLatencySlider();
            createStartStopButton();
            createSnapShotButton();
            createClearButton();
            createColorChooserButton();
            setLayout(new BoxLayout(this, 1));
            add(m_amountPointsSlider);
            add(m_latencyTimeSlider);
            javax.swing.JComponent stretch = new JPanel();
            stretch.setBackground(java.awt.Color.WHITE);
            stretch.setLayout(new BoxLayout(stretch, 0));
            stretch.add(javax.swing.Box.createHorizontalGlue());
            stretch.add(m_startStop);
            stretch.add(javax.swing.Box.createHorizontalGlue());
            stretch.add(m_clear);
            if(m_snapshot != null) {
                stretch.add(javax.swing.Box.createHorizontalGlue());
                stretch.add(m_snapshot);
            }
            stretch.add(javax.swing.Box.createHorizontalGlue());
            stretch.add(m_colorChooser);
            stretch.add(javax.swing.Box.createHorizontalGlue());
            add(stretch);
        }
    }


    protected info.monitorenter.gui.chart.Chart2D m_chart;
    private transient info.monitorenter.gui.chart.io.ADataCollector m_collector;
    private info.monitorenter.gui.chart.traces.Trace2DLtd m_trace;

    public static void main(java.lang.String args[]) {
        javax.swing.JFrame frame = new JFrame("Showcase");
        info.monitorenter.gui.chart.demos.Showcase showcase = new Showcase();
        showcase.init();
        frame.getContentPane().add(showcase);
        frame.setSize(400, 600);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent e) {
                java.lang.System.exit(0);
            }

        });
        frame.setVisible(true);
    }

    public synchronized void clearTrace() {
        getTrace().removeAllPoints();
    }

    public info.monitorenter.gui.chart.Chart2D getChart() {
        return m_chart;
    }

    public info.monitorenter.gui.chart.io.ADataCollector getCollector() {
        return m_collector;
    }

    public info.monitorenter.gui.chart.traces.Trace2DLtd getTrace() {
        return m_trace;
    }

    public void init() {
        super.init();
        info.monitorenter.gui.chart.Chart2D chart = new Chart2D();
        setChart(chart);
        setSize(new Dimension(600, 500));
        m_chart.getAxisX().setPaintGrid(true);
        m_chart.getAxisY().setPaintGrid(true);
        chart.getAxisY().setRangePolicy(new RangePolicyMinimumViewport(new Range(-20D, 20D)));
        chart.setGridColor(java.awt.Color.LIGHT_GRAY);
        setTrace(new Trace2DLtd(100));
        getTrace().setName("random");
        getTrace().setPhysicalUnits("Milliseconds", "random value");
        getTrace().setColor(java.awt.Color.RED);
        chart.addTrace(getTrace());
        java.awt.Container content = getContentPane();
        content.setLayout(new BoxLayout(content, 1));
        info.monitorenter.gui.chart.controls.LayoutFactory factory = info.monitorenter.gui.chart.controls.LayoutFactory.getInstance();
        info.monitorenter.gui.chart.views.ChartPanel chartpanel = new ChartPanel(chart);
        setJMenuBar(factory.createChartMenuBar(chartpanel, false));
        content.add(chartpanel);
        setCollector(new RandomDataCollectorOffset(getTrace(), 50));
        content.add(new ControlPanel());
    }

    public void setChart(info.monitorenter.gui.chart.Chart2D chart2D) {
        if(m_chart == null)
            m_chart = chart2D;
    }

    private void setCollector(info.monitorenter.gui.chart.io.RandomDataCollectorOffset collector) {
        m_collector = collector;
    }

    public void setTrace(info.monitorenter.gui.chart.traces.Trace2DLtd trace) {
        if(m_trace == null)
            m_trace = trace;
    }

    public synchronized void startData() {
        if(!getCollector().isRunning())
            getCollector().start();
    }

    public synchronized void stopData() {
        if(getCollector().isRunning())
            getCollector().stop();
    }
}
