package org.cameo.ui;


import com.nomagic.uml2.ext.jmi.helpers.CoreHelper;
import org.cameo.element.Structure;
import org.cameo.redmine.RedmineApi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author Eliana
 */


/**
 * Class for the User Interface
 */
public class BaseTicketPanel extends JPanel {
    /**
     * Creates new form for Ticket
     */
    public BaseTicketPanel() {
        initComponents();
    }



    @SuppressWarnings("unchecked")
    private void initComponents() {

        titlePanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        contentPanel = new JPanel();
        taskName = new JLabel();
        taskNameField = new JTextField();
        description = new JLabel();
        descriptionArea = new JTextArea(10,10);
        jPanel1 = new JPanel();
        buttonPanel = new JPanel();
        cancelButton = new JButton();
        buttonOk = new JButton();
        api = new RedmineApi();
        showTicket = new JButton();

        titlePanel.setBackground(new Color(255, 255, 255));

        jLabel1.setFont(new Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Create a new ticket");

        jLabel2.setText("Give name and description for your new ticket");

        GroupLayout titlePanelLayout = new GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
                titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
                titlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addContainerGap())
        );

        taskName.setText("Task name:");

        description.setText("Description:");

        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(taskName, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(taskNameField, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(description)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(descriptionArea, 300, GroupLayout.PREFERRED_SIZE, 300)))
                                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(taskName)
                                        .addComponent(taskNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(description)
                                        .addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {

                                           @Override
                                           public void actionPerformed(ActionEvent actionEvent) {
                                               onCancel();
                                           }
                                       }
        );

        showTicket.setText("Show Ticket");
        showTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showIssue();
            }
        });

        buttonOk.setText("OK");
        buttonOk.addActionListener(new ActionListener() {
                                       @Override
                                       public void actionPerformed(ActionEvent actionEvent) {
                                           try {
                                               onOK();
                                           } catch (IOException | InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   }
        );

        GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
                buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(showTicket)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOk)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton)
                                .addContainerGap())

        );

        buttonPanelLayout.setVerticalGroup(
                buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cancelButton)
                                .addComponent(buttonOk)
                                .addComponent(showTicket))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titlePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }

    /**
     * Using ScheduledExecutorService that can schedule commands to run after a given delay for API's call
     * POST -> createIssue()
     * GET -> getIssueFromList() and save the result into Documentation/Comments in Cameo's Stereotype
     * @throws IOException
     * @throws InterruptedException
     */
    private void onOK() throws IOException, InterruptedException {
        final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        Window window = SwingUtilities.getWindowAncestor(this);
        if(JOptionPane.showConfirmDialog(null, "Do you want to save the ticket?") == JOptionPane.OK_OPTION) {
            exec.schedule(new Runnable() {
                public void run() {
                    try {
                        api.createIssue(taskNameField, descriptionArea);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 2, TimeUnit.SECONDS);

            exec.schedule(new Runnable() {
                public void run() {
                    CoreHelper.setComment(Structure.stereotype, api.getIssueFromList());
                }
            }, 4, TimeUnit.SECONDS);
            exec.shutdown();
            window.dispose();

        }


    }

    /**
     * Method when pressing "Cancel" button
     */
    private void onCancel() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if(JOptionPane.showConfirmDialog(null, "Do you want to close the operation?") == JOptionPane.OK_CANCEL_OPTION){
            window.dispose();
        }
    }

    /**
     * Method when pressing "Show Ticket" button, to show the latest ticket without creating a new one
     */

    private void showIssue(){
        Window window = SwingUtilities.getWindowAncestor(this);
        if(JOptionPane.showConfirmDialog(null, "Do you want to see the ticket?") == JOptionPane.OK_OPTION) {
            CoreHelper.setComment(Structure.stereotype, api.getIssueFromList());
        }
        window.dispose();
    }

    private JPanel buttonPanel;
    protected JButton cancelButton;
    private JPanel contentPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    public JLabel taskName;
    private JLabel description;
    private JPanel jPanel1;
    public  JTextArea descriptionArea;
    protected JTextField taskNameField;
    private JPanel titlePanel;
    protected JButton buttonOk;
    private RedmineApi api;
    private JButton showTicket;


}
