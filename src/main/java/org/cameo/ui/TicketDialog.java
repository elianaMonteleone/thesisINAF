package org.cameo.ui;



import javax.swing.*;
import java.awt.*;

public class TicketDialog extends JDialog {


    private final BaseTicketPanel panel;

    public TicketDialog(Frame parent) {
        super(parent, "Create ticket dialog", false);
        this.panel = new BaseTicketPanel();

        this.setContentPane(panel);
        this.pack();

        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
