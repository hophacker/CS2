/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;

/**
 *
 * @author J
 */
public class Add_Panel extends javax.swing.JPanel {

    /**
     * Creates new form Add_Panel
     */
    public Add_Panel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        add1 = new client.Add();
        fileLocTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        GenerateB = new javax.swing.JButton();
        hintL = new javax.swing.JLabel();
        keyL = new javax.swing.JLabel();
        keywordTF = new javax.swing.JTextField();

        fileLocTF.setText("C:/Users/J/NetBeansProjects/addFiles");
        fileLocTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileLocTFActionPerformed(evt);
            }
        });

        jLabel1.setText("Adding Files Directory");

        GenerateB.setFont(new java.awt.Font("����", 2, 18)); // NOI18N
        GenerateB.setText("OK");
        GenerateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateBActionPerformed(evt);
            }
        });

        hintL.setFont(new java.awt.Font("����", 0, 24)); // NOI18N
        hintL.setForeground(new java.awt.Color(204, 51, 0));

        keyL.setText("keywods File");

        keywordTF.setText("D:/addFiles");
        keywordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keywordTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(GenerateB, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keyL))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keywordTF)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileLocTF, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                        .addGap(69, 69, 69))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(hintL, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLocTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keywordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keyL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hintL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GenerateB, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileLocTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileLocTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileLocTFActionPerformed

    private void GenerateBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateBActionPerformed
        // TODO add your handling code here:
        File fileDir = new File(fileLocTF.getText());
        File kwFile = new File(keywordTF.getText());
        if (fileDir.exists() && fileDir.isDirectory() && kwFile.exists()) {
            

        } else {
            hintL.setText("Your input is not correct!");
        }
    }//GEN-LAST:event_GenerateBActionPerformed

    private void keywordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keywordTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keywordTFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton GenerateB;
    private client.Add add1;
    private javax.swing.JTextField fileLocTF;
    private javax.swing.JLabel hintL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel keyL;
    private javax.swing.JTextField keywordTF;
    // End of variables declaration//GEN-END:variables
}
