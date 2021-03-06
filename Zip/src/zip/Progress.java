/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zip;

import javax.swing.JOptionPane;

/**
 *
 * @author Ahmed
 */
public class Progress extends javax.swing.JFrame {

    /**
     * Creates new form Progress
     */
    public Progress() {
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

        l = new javax.swing.JLabel();
        prg = new javax.swing.JProgressBar();
        time = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(new java.awt.Point(300, 150));

        l.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        l.setText("processing");

        prg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        prg.setForeground(new java.awt.Color(153, 0, 153));
        prg.setStringPainted(true);

        time.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        time.setText("processing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(time)
                    .addComponent(l)
                    .addComponent(prg, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(l)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(prg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(time)
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Progress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Progress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Progress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Progress.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Progress().setVisible(true);
            }
        });
    }
    
    public void  runprogress(Client client, Progress p) {
        Runnable runp = new Runnable() {
            @Override
            public  void run() {
                p.setVisible(true);
                int progress = 0;
                long total = client.total;
                long recieve = client.recieve;
                p.prg.setMinimum(0);
                p.prg.setMaximum(100);
                long diff = 0;                
                long end = 0;
                long time = 0;                
                long start = System.currentTimeMillis();
                while (recieve < total) {
                    
                    recieve = client.recieve;
                    progress = (int) Math.round(((double) recieve / (double) total) * 100);
                    p.l.setText("processing... " + (recieve / (1024 * 1024)) + "MB  of " + (total / (1024 * 1024)) + "MB");
                    p.prg.setValue(progress);
                    
                    try {
                       Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                    end = System.currentTimeMillis();
                    diff = end - start;
                    //diff/recieve=time/total
                    time = (int) Math.round((((double) diff / 1000) * (double) total) / (double) recieve);
                    p.time.setText(FormateTime((long) ((double) time - (double) diff / 1000)) + "  left");
                }
                JOptionPane.showMessageDialog(p, "Done \n Time Elapsed  = " + FormateTime((long) ((double) diff / 1000)));
                p.setVisible(false);
                p.dispose();
            }
        };
        Thread thread = new Thread(runp);
        thread.start();
        thread.setPriority(Thread.MIN_PRIORITY);
    }
    
    public String FormateTime(long time) {
        int count = 0;
        if (time >= 3600) {
            while (time >= 3600) {
                count++;
                time -= 3600;
            }
            return count + " hours :" + FormateTime(time);
        } else if (time >= 60) {
            while (time >= 60) {
                count++;
                time -= 60;
            }
            return count + " minutes :" + FormateTime(time);
        }
        
        return time + " seconds";
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel l;
    public javax.swing.JProgressBar prg;
    public javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}
