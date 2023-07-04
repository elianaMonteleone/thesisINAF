package org.cameo.ui;


import com.nomagic.uml2.ext.jmi.helpers.CoreHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import org.cameo.element.Issue;
import org.cameo.element.Structure;
import org.cameo.redmine.RedmineApi;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        String[] priorities = {"1 - Low", "2 - Normal", "3 - High", "4 - Urgent" , "5 - Immediate"};
        priorityBox = new JComboBox(priorities);
        priority = new JLabel();
        String [] trackers = {"1 - Anomaly", "2 - Evolution", "3 - Assistance"};
        trackerBox = new JComboBox(trackers);
        tracker = new JLabel();









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

        priority.setText("Priority");

        tracker.setText("Tracker");

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
                                                .addComponent(descriptionArea, 300, GroupLayout.PREFERRED_SIZE, 300))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(priority)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(priorityBox))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(tracker)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(trackerBox))
                                )
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
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(priority)
                                        .addComponent(priorityBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tracker)
                                        .addComponent(trackerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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

                showDialog();
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
                        api.createIssue(taskNameField, descriptionArea, priorityBox, trackerBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 2, TimeUnit.SECONDS);

            exec.schedule(new Runnable() {
                public void run() {
                    CoreHelper.setComment(Structure.stereotype, api.getIssueFromList());

                    //adding values to the properties
                    List<Property> propertyList = new ArrayList<>();
                    Issue issueProperty = api.getProperties();
                    Structure.property.setName("Status:" + " " + issueProperty.getStatus());
                    Structure.propertyAuthor.setName("Author:" + " " + issueProperty.getAuthor());
                    Structure.propertyStartDate.setName("Start Date : " + " " + issueProperty.getStartDate());
                    Structure.propertyTracker.setName("Tracker:" + "  " + issueProperty.getTracker());
                    Structure.propertyPriority.setName("Priority: " + " " + issueProperty.getPriority());
                    Structure.propertySubject.setName("Subject:" + " " + issueProperty.getSubject());
                    propertyList.add(Structure.property);
                    propertyList.add(Structure.propertyAuthor);
                    propertyList.add(Structure.propertyStartDate);
                    propertyList.add(Structure.propertyTracker);
                    propertyList.add(Structure.propertyPriority);
                    propertyList.add(Structure.propertySubject);
                    for (Property prop:
                            propertyList ) {
                        prop.setOwner(Structure.stereotype);
                    }
                }
            }, 3, TimeUnit.SECONDS);
            exec.shutdown();
            window.dispose();

        }


    }

    /**
     * Method when pressing "Cancel" button
     */
    private void onCancel() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if(JOptionPane.showConfirmDialog(null, "Do you want to close the operation?") == JOptionPane.OK_OPTION){
            window.dispose();
        }
    }


    /**
     * Dialog to allow the user to insert the subject and search for the desired issue
     */

    private void showDialog(){
        JFrame window = new JFrame();
        window.setSize(400,400);
        window.setLocationRelativeTo(null);

        window.setTitle("Search Issue");

        JButton jb = new JButton();
        jb.setText("OK");
        JLabel labelIssue = new JLabel("Insert the subject:");
        JTextField subject = new JTextField(20);
        String[] priorities = {"", "Low", "Normal", "High", "Urgent" , "Immediate"};
        JComboBox priorityIssue = new JComboBox(priorities);
        JLabel labelAuthor = new JLabel("Insert the author:");
        JTextField author = new JTextField(50);




        //JTextField doesn't accept white spaces, so I replace them with "-"
        Document doc = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet attr)
                    throws BadLocationException {
                String newstr = str.replaceAll(" ", "-");  // could use "\\s" instead of " "
                super.insertString(offs, newstr, attr);
            }

            @Override
            public void replace(int offs, int len, String str, AttributeSet attr)
                    throws BadLocationException {
                String newstr = str.replaceAll(" ", "-");  // could use "\\s" instead of " "
                super.replace(offs, len, newstr, attr);
            }
        };
        author.setDocument(doc);

        createLayout(window, labelIssue,subject,priority,priorityIssue,labelAuthor,author,jb);

        window.setVisible(true);

        jb.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent actionEvent) {
                                      showIssue(subject, priorityIssue,author);
                                  }
                              }
        );

    }


    /**
     * Layout of the Issue's dialog
     * @param frame
     * @param arg
     */
    private void createLayout(JFrame frame, JComponent... arg) {

        Container pane = frame.getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addComponent(arg[3])
                .addComponent(arg[4])
                .addComponent(arg[5])
                .addComponent(arg[6])

        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
                .addComponent(arg[1], GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(arg[2])
                .addComponent(arg[3], GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(arg[4])
                .addComponent(arg[5], GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(arg[6])
        );
    }




    /**
     * Method when pressing "Show Ticket" button, to show the ticket by subject
     */

    private void showIssue(JTextField subject, JComboBox priorityBox, JTextField author){
        Window window = SwingUtilities.getWindowAncestor(this);

        if(!subject.getText().isEmpty() || !author.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "Do you want to see the ticket?");
            CoreHelper.setComment(Structure.stereotype, api.getIssueBySpecifiedProperties(subject,priorityBox,author));
            //adding values to the properties
            List<Property> propertyList = new ArrayList<>();
            Issue issueProperty = api.getPropertiesByIssue(subject,priorityBox,author);
            Structure.property.setName("Status:" + " " + issueProperty.getStatus());
            Structure.propertyAuthor.setName("Author:" + " " + issueProperty.getAuthor());
            Structure.propertyStartDate.setName("Start Date : " + " " + issueProperty.getStartDate());
            Structure.propertyTracker.setName("Tracker:" + "  " + issueProperty.getTracker());
            Structure.propertyPriority.setName("Priority: " + " " + issueProperty.getPriority());
            Structure.propertySubject.setName("Subject:" + " " + issueProperty.getSubject());
            propertyList.add(Structure.property);
            propertyList.add(Structure.propertyAuthor);
            propertyList.add(Structure.propertyStartDate);
            propertyList.add(Structure.propertyTracker);
            propertyList.add(Structure.propertyPriority);
            propertyList.add(Structure.propertySubject);
            for (Property prop:
                    propertyList ) {
                prop.setOwner(Structure.stereotype);
            }
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
    private JComboBox priorityBox;
    private JLabel priority;
    private JLabel tracker;
    private JComboBox trackerBox;



}
