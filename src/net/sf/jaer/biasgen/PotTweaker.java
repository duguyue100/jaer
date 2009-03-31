/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PotTweaker.java
 *
 * Created on Mar 27, 2009, 9:46:12 PM
 */
package net.sf.jaer.biasgen;

import java.awt.Container;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoableEditSupport;

/**
 * This visual component bean allows control of biases or parameters around their nominal
 * values to adjust them according to some customized method.
 * This component can be added to a GUI builder palette and customized.
 * Intended for user-friendly control of Chip bias values.
 * @author tobi
 */
public class PotTweaker extends javax.swing.JPanel implements PreferenceChangeListener, Observer, ChangeListener, StateEditable {

    static Logger log = Logger.getLogger("PotTweaker");
    protected int maxSlider,  halfMaxSlider;
    private String name = "<name>";
    private String tooltip = null;
    TitledBorder border = BorderFactory.createTitledBorder("");

    // undo support
    StateEdit edit = null;
    UndoableEditSupport editSupport = new UndoableEditSupport();
    private boolean addedUndoListener = false;

    public PotTweaker() {
        initComponents();
        setBorder(border);
        maxSlider = getSlider().getMaximum();
        halfMaxSlider = maxSlider / 2;
    }

    private Hashtable<Integer,JComponent> labelTable=new Hashtable();

    private void setLabels(){
        labelTable.put(0,new JLabel(lessDescription));
        labelTable.put(100,new JLabel(moreDescription));
        labelTable.put(50,new JLabel("Nominal"));
        slider.setLabelTable(labelTable);
    }

    public String prefsKey() {
        return this.getClass().getName();
    }

    /**
     * Adds a ChangeListener to the slider.
     *
     * @param l the ChangeListener to add
     * @see #fireStateChanged
     * @see #removeChangeListener
     */
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    /**
     * Removes a ChangeListener from the slider.
     *
     * @param l the ChangeListener to remove
     * @see #fireStateChanged
     * @see #addChangeListener

     */
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    /** Relative slider value.
     *
     * @return float from -1:1 depending on slider relative to middle.
     */
    protected float sliderValue() {
        return ((float) getSlider().getValue() - halfMaxSlider) / halfMaxSlider;
    }

    /** Called when pot changes bit value.
     *
     * @param o the pot.
     * @param arg the new value.
     */
    public void update(Observable o, Object arg) {
        log.info("Observable=" + o + " Object=" + arg);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        slider = new javax.swing.JSlider();
        tweakDescriptionLabel = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setPreferredSize(new java.awt.Dimension(300, 30));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        setLayout(new java.awt.BorderLayout());

        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setToolTipText("slide to tweak pot value around the preference value");
        slider.setMinimumSize(new java.awt.Dimension(36, 10));
        slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sliderMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sliderMouseReleased(evt);
            }
        });
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        add(slider, java.awt.BorderLayout.CENTER);

        tweakDescriptionLabel.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        tweakDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tweakDescriptionLabel.setText("Description of control");
        add(tweakDescriptionLabel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
        stateChanged(evt);
}//GEN-LAST:event_sliderStateChanged

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        if (addedUndoListener) {
            return;
        }
        addedUndoListener = true;
        if (evt.getComponent() instanceof Container) {
            Container anc = (Container) evt.getComponent();
            while (anc != null && anc instanceof Container) {
                if (anc instanceof UndoableEditListener) {
                    editSupport.addUndoableEditListener((UndoableEditListener) anc);
                    break;
                }
                anc = anc.getParent();
            }
        }
    }//GEN-LAST:event_formAncestorAdded

    private void sliderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sliderMousePressed
        startEdit();
    }//GEN-LAST:event_sliderMousePressed

    private void sliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sliderMouseReleased
        endEdit();
    }//GEN-LAST:event_sliderMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel tweakDescriptionLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the slider
     */
    public javax.swing.JSlider getSlider() {
        return slider;
    }

    /** Called when preferences are loaded or stored. Resets the slider to middle.
     *
     * @param evt
     */
    public void preferenceChange(PreferenceChangeEvent evt) {
        log.info(evt.toString());
        getSlider().setValue(50);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * The title of the tweak, shown as titled border.
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        super.setName(name);
        this.name = name;
        border.setTitle(name);
        revalidate();
    }

    /**
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip the tooltip to set
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
        slider.setToolTipText(tooltip);
       revalidate();
    }
    /**
     * Only one <code>ChangeEvent</code> is needed per slider instance since the
     * event's only (read-only) state is the source property.  The source
     * of events generated here is always "this". The event is lazily
     * created the first time that an event notification is fired.
     *
     * @see #fireStateChanged
     */
    protected transient ChangeEvent changeEvent = null;

    /**
     * Send a {@code ChangeEvent}, whose source is this {@code JSlider}, to
     * all {@code ChangeListener}s that have registered interest in
     * {@code ChangeEvent}s.
     * This method is called each time a {@code ChangeEvent} is received from
     * the model.
     * <p>
     * The event instance is created if necessary, and stored in
     * {@code changeEvent}.
     *
     * @see #addChangeListener
     * @see EventListenerList
     */
    public void stateChanged(ChangeEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
            }
        }
    }

    /** The float value from -1 to 1.
     * 
     * @return value from -1:1
     */
    public float getValue() {
        return ((float) slider.getValue() - halfMaxSlider) / maxSlider;
    }


    private String lessDescription = "Less";

    /**
     * Get the value of lessDescription
     *
     * @return the value of lessDescription
     */
    public String getLessDescription() {
        return lessDescription;
    }

    /**
     * Set the value of lessDescription, shown to left of slider.
     *
     * @param lessDescription new value of lessDescription
     */
    public void setLessDescription(String lessDescription) {
        this.lessDescription = lessDescription;
//        lessDescriptionLabel.setText(lessDescription);
        setLabels();
   }



    private String moreDescription = "More";

    /**
     * Get the value of moreDescription, shown to right of slider.
     *
     * @return the value of moreDescription
     */
    public String getMoreDescription() {
        return moreDescription;
    }

    /**
     * Set the value of moreDescription, shown to right of slider.
     *
     * @param moreDescription new value of moreDescription
     */
    public void setMoreDescription(String moreDescription) {
        this.moreDescription = moreDescription;
//        moreDescriptionLabel.setText(moreDescription);
         setLabels();
    }
    private String tweakDescription = "Description of tweak";

    /**
     * Get the value of tweakDescription
     *
     * @return the value of tweakDescription
     */
    public String getTweakDescription() {
        return tweakDescription;
    }

    /**
     * Set the value of tweakDescription, shown as help text above slider.
     *
     * @param tweakDescription new value of tweakDescription
     */
    public void setTweakDescription(String tweakDescription) {
        this.tweakDescription = tweakDescription;
         tweakDescriptionLabel.setText(tweakDescription);
   }





    //**********************************************************************************************/
    // undo support
    int oldSliderValue = 0;

    void startEdit() {
        edit = new MyStateEdit(this, "pot change");
        oldSliderValue = slider.getValue();
    }

    void endEdit() {
        if (oldSliderValue == slider.getValue()) {
            return;
        }
        if (edit != null) {
            edit.end();
        }
        editSupport.postEdit(edit);
    }
    String STATE_KEY = "sllderValue";

    public void restoreState(Hashtable<?, ?> hashtable) {
        if (hashtable == null) {
            throw new RuntimeException("null hashtable");
        }
        if (hashtable.get(STATE_KEY) == null) {
            System.err.println("slider " + slider + " not in hashtable " + hashtable + " with size=" + hashtable.size());
            return;
        }
        int v = (Integer) hashtable.get(STATE_KEY);
        slider.setValue(v);
    }

    public void storeState(Hashtable<Object, Object> hashtable) {
        hashtable.put(STATE_KEY, new Integer(slider.getValue()));
    }

    class MyStateEdit extends StateEdit {

        public MyStateEdit(StateEditable o, String s) {
            super(o, s);
        }

        // overrides this to actually get a state stored!!
        protected void removeRedundantState() {
        }
    }
}
