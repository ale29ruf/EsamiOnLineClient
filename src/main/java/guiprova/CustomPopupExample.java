package guiprova;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomPopupExample {
        public static void main(String[] args) {
            JFrame frame = new JFrame("Main Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton popupButton = new JButton("Mostra Popup");
            popupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCustomPopup(frame);
                }
            });

            frame.setLayout(new FlowLayout());
            frame.add(popupButton);

            frame.pack();
            frame.setVisible(true);
        }

        private static void showCustomPopup(JFrame parentFrame) {
            JDialog popupDialog = new JDialog(parentFrame, "Finestra Popup", true);
            popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JLabel messageLabel = new JLabel("Questo è un messaggio di popup!");
            JButton closeButton = new JButton("Chiudi");

            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popupDialog.dispose();
                }
            });

            JPanel popupPanel = new JPanel();
            popupPanel.add(messageLabel);
            popupPanel.add(closeButton);

            popupDialog.add(popupPanel);
            popupDialog.pack();
            popupDialog.setLocationRelativeTo(parentFrame);
            popupDialog.setVisible(true);
        }
    }
