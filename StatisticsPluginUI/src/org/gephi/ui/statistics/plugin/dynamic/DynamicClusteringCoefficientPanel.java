/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.ui.statistics.plugin.dynamic;

import org.gephi.graph.api.GraphController;
import org.openide.util.Lookup;

/**
 *
 * @author Mathieu Bastian
 */
public class DynamicClusteringCoefficientPanel extends javax.swing.JPanel {

    /** Creates new form DynamicClusteringCoefficientPanel */
    public DynamicClusteringCoefficientPanel() {
        initComponents();

        //Disable directed if the graph is undirecteds
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        if (graphController.getModel().isUndirected()) {
            directedRadioButton.setEnabled(false);
        }
    }

    public boolean isDirected() {
        return directedRadioButton.isSelected();
    }

    public void setDirected(boolean directed) {
        directedButtonGroup.setSelected(directed ? directedRadioButton.getModel() : undirectedRadioButton.getModel(), true);
        if (!directed) {
            directedRadioButton.setEnabled(false);
        }
    }

    public boolean isAverageOnly() {
        return averageOnlyCheckbox.isSelected();
    }

    public void setAverageOnly(boolean averageOnly) {
        averageOnlyCheckbox.setSelected(averageOnly);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directedButtonGroup = new javax.swing.ButtonGroup();
        header = new org.jdesktop.swingx.JXHeader();
        directedRadioButton = new javax.swing.JRadioButton();
        undirectedRadioButton = new javax.swing.JRadioButton();
        averageOnlyCheckbox = new javax.swing.JCheckBox();

        header.setDescription(org.openide.util.NbBundle.getMessage(DynamicClusteringCoefficientPanel.class, "DynamicClusteringCoefficientPanel.header.description")); // NOI18N
        header.setTitle(org.openide.util.NbBundle.getMessage(DynamicClusteringCoefficientPanel.class, "DynamicClusteringCoefficientPanel.header.title")); // NOI18N

        directedButtonGroup.add(directedRadioButton);
        directedRadioButton.setText(org.openide.util.NbBundle.getMessage(DynamicClusteringCoefficientPanel.class, "DynamicClusteringCoefficientPanel.directedRadioButton.text")); // NOI18N

        directedButtonGroup.add(undirectedRadioButton);
        undirectedRadioButton.setText(org.openide.util.NbBundle.getMessage(DynamicClusteringCoefficientPanel.class, "DynamicClusteringCoefficientPanel.undirectedRadioButton.text")); // NOI18N

        averageOnlyCheckbox.setText(org.openide.util.NbBundle.getMessage(DynamicClusteringCoefficientPanel.class, "DynamicClusteringCoefficientPanel.averageOnlyCheckbox.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(header, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(undirectedRadioButton)
                            .add(directedRadioButton))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(averageOnlyCheckbox))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(header, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(directedRadioButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(undirectedRadioButton)
                .add(18, 18, 18)
                .add(averageOnlyCheckbox)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox averageOnlyCheckbox;
    private javax.swing.ButtonGroup directedButtonGroup;
    protected javax.swing.JRadioButton directedRadioButton;
    private org.jdesktop.swingx.JXHeader header;
    protected javax.swing.JRadioButton undirectedRadioButton;
    // End of variables declaration//GEN-END:variables
}