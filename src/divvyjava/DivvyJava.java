package divvyjava;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class DivvyJava extends JFrame
{ 
    private JButton expandButton;
    private JButton recieveButton;
    private JButton divvyButton;
    private JButton backButton;
    
    private JLabel hostLabel;
    private JLabel hostLiteralLabel;
    
    private ActionListener expandListener;
    private ActionListener recieveListener;
    private ActionListener divvyListener;
    private ActionListener backListener;
    private ActionListener deselectListener;
    private ActionListener menuListener;
    
    private ArrayList<String> availableRecipients;
    private ArrayList<String> availableHosts;
    private ArrayList<JCheckBoxMenuItem> recipients;
    private ArrayList<JRadioButtonMenuItem> hosts;
    
    private JMenuItem divvyStart;
    private JMenuItem recieveStart;
    private JButton exitOption;
    private JMenuItem deselectAll;
    
    private ButtonGroup hostsGroup;
    
    private JComboBox hostChoiceBox;
    
    private JMenuBar menuBar;
    private JMenu divvyMenu;
    private JMenu recieveMenu;
    
    private JPanel homePanel;
    private JPanel divvyPanel;
    private JPanel recievePanel;
    private JPanel backgroundPanel;
    
    private JFrame divvyFrame = this;

    public DivvyJava()
    {
        createComponents();
        createPanels();
        this.setSize(310, 540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void createComponents()
    {
        availableRecipients = new ArrayList(5);
        availableRecipients.add("Zack");
        availableRecipients.add("Michael");
        availableRecipients.add("Madison");
        availableRecipients.add("Drew");
        availableRecipients.add("Sarah");
        
        availableHosts = new ArrayList(3);
        availableHosts.add("Phil");
        availableHosts.add("Matt");
        availableHosts.add("Alexa");
        
        menuBar = new JMenuBar();
        divvyMenu = new JMenu("DIVVY");
        recieveMenu = new JMenu("RECIEVE");
        
        hostsGroup = new ButtonGroup();
        
        divvyStart = new JMenuItem("Divvy Launch");
        recieveStart = new JMenuItem("Recieve Launch");
        exitOption = new JButton("EXIT");
        deselectAll = new JMenuItem("Clear Selection");
        
        recipients = new ArrayList(availableRecipients.size());
        for (int i=0;i<availableRecipients.size();i++)
        {
            recipients.add(i, new JCheckBoxMenuItem(availableRecipients.get(i)));
            divvyMenu.add(recipients.get(i));
        }
        hosts = new ArrayList(availableHosts.size());
        for (int i=0;i<availableHosts.size();i++)
        {
            hosts.add(i, new JRadioButtonMenuItem(availableHosts.get(i)));
            hosts.get(i).setActionCommand(availableHosts.get(i));
            recieveMenu.add(hosts.get(i));
            hostsGroup.add(hosts.get(i));
        }
        
        divvyMenu.addSeparator();
        divvyMenu.add(divvyStart);
        
        recieveMenu.addSeparator();
        recieveMenu.add(recieveStart);
        
        menuBar.add(divvyMenu);
        menuBar.add(recieveMenu);
        
        divvyFrame.setJMenuBar(menuBar);
        
        hostChoiceBox = new JComboBox(availableHosts.toArray());
        
        class AddRemoveListener implements ActionListener
        {
            private String action;
            
            public AddRemoveListener(String action)
            {
                this.action=action.toLowerCase();
            }
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (action.equals("expand"))
                {
                    homePanel.remove(expandButton);
                    homePanel.add(recieveButton);
                    homePanel.add(divvyButton);
                    homePanel.add(hostChoiceBox);
                    recieveButton.setBounds(5, 210, 100, 30);
                    divvyButton.setBounds(5, 5, 100, 30);
                    homePanel.setBounds(30, 30, 230, 430);
                    hostChoiceBox.setBounds(5, 245, 100,20);
                }
                if (action.equals("recieve"));
                {
                    if(hostsGroup.getSelection()!=null)
                    {
                        homePanel.setVisible(false);
                        recievePanel.setVisible(true);
                        recievePanel.add(backButton);
                        backButton.setBounds(5, 5, 100, 30);
                        hostLabel.setText(hostsGroup.getSelection().getActionCommand());
                        hostLabel.setForeground(Color.RED);
                        backgroundPanel.setBackground(Color.BLUE);
                        menuBar.remove(divvyMenu);
                        menuBar.remove(recieveMenu);
                        menuBar.add(hostLiteralLabel);
                        menuBar.add(hostLabel);
                        menuBar.add(exitOption);
                    }
                }
                if (action.equals("divvy"))
                {
                    homePanel.setVisible(false);
                    divvyPanel.setVisible(true);
                    divvyPanel.add(backButton);
                    backButton.setBounds(5, 5, 100, 30);
                    backgroundPanel.setBackground(Color.GREEN);
                    menuBar.remove(recieveMenu);
                    menuBar.remove(hostLiteralLabel);
                    menuBar.remove(hostLabel);
                    divvyMenu.setText("Divvy: ACTIVE");
                    divvyMenu.setForeground(Color.RED);
                    divvyMenu.remove(divvyStart);
                    divvyMenu.add(deselectAll);
                    menuBar.add(divvyMenu);
                    menuBar.add(exitOption);
                }
                if (action.equals("back"))
                {
                    recievePanel.setVisible(false);
                    divvyPanel.setVisible(false);
                    homePanel.setVisible(true);
                    backgroundPanel.setBackground(Color.RED);
                    menuBar.removeAll();
                    divvyMenu.remove(deselectAll);
                    divvyMenu.add(divvyStart);
                    divvyMenu.setText("DIVVY");
                    divvyMenu.setForeground(recieveMenu.getForeground());
                    menuBar.add(divvyMenu);
                    menuBar.add(recieveMenu);
                }
                if (action.equals("deselect"))
                {
                    for (int i=0;i<divvyMenu.getItemCount();i++)
                    {
                        if (divvyMenu.getItem(i).isSelected())
                        {
                            divvyMenu.getItem(i).setSelected(false);
                        }
                    }
                }
                if (action.equals("keepmenuopen"))
                {
//                    if (e.getSource() instanceof JRadioButtonMenuItem)
//                    {
//                        JRadioButtonMenuItem source = (JRadioButtonMenuItem) e.getSource();
//                        recieveMenu.doClick(1);
//                    }
//                    if(e.getSource() instanceof JCheckBoxMenuItem)
//                    {
//                        JCheckBoxMenuItem source = (JCheckBoxMenuItem) e.getSource();
//                        divvyMenu.doClick(1);
//                    }
                }
            }
        }
        expandButton=new JButton("expand");
        recieveButton=new JButton("recieve");
        divvyButton=new JButton("divvy");
        backButton=new JButton("back");
        
        hostLabel=new JLabel();
        hostLiteralLabel=new JLabel("host: ");
 
        expandListener = new AddRemoveListener("expand");
        recieveListener = new AddRemoveListener("recieve");
        divvyListener = new AddRemoveListener("divvy");
        backListener = new AddRemoveListener("back");
        deselectListener = new AddRemoveListener("deselect");
        menuListener = new AddRemoveListener("keepmenuopen");
        
        expandButton.addActionListener(expandListener);
        recieveButton.addActionListener(recieveListener);
        recieveStart.addActionListener(recieveListener);
        divvyButton.addActionListener(divvyListener);        
        divvyStart.addActionListener(divvyListener);
        backButton.addActionListener(backListener);
        exitOption.addActionListener(backListener);
        deselectAll.addActionListener(deselectListener);
//        divvyMenu.addActionListener(menuListener);
//        recieveMenu.addActionListener(menuListener);
//        for (int i=0;i<divvyMenu.getItemCount();i++)
//        {
//            if (divvyMenu.getItem(i) instanceof JRadioButtonMenuItem)
//            {
//                divvyMenu.getItem(i).addActionListener(menuListener);
//            }
//        }
//        for (int i=0;i<recieveMenu.getItemCount();i++)
//        {
//            if (recieveMenu.getItem(i) instanceof JCheckBoxMenuItem)
//            {
//                recieveMenu.getItem(i).addActionListener(menuListener);
//            }
//        }
    }
    private void createPanels()
    {
        backgroundPanel=new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setVisible(true);
        backgroundPanel.setBounds(0, 0, 310, 540);
        backgroundPanel.setBackground(Color.RED);
        
        homePanel=new JPanel();
        homePanel.setLayout(null);
        homePanel.setVisible(true);
        homePanel.setBackground(Color.DARK_GRAY);
        homePanel.setBounds(30, 30, 70, 40);
        
        recievePanel=new JPanel();
        recievePanel.setLayout(null);
        recievePanel.setVisible(false);
        recievePanel.setBackground(Color.DARK_GRAY);
        recievePanel.setBounds(30, 30, 230, 430);
        
        divvyPanel=new JPanel();
        divvyPanel.setLayout(null);
        divvyPanel.setVisible(false);
        divvyPanel.setBackground(Color.DARK_GRAY);
        divvyPanel.setBounds(30, 30, 230, 430);

        homePanel.add(expandButton);
        expandButton.setBounds(0, 0, 40, 20);
        
        this.add(backgroundPanel);
        backgroundPanel.add(recievePanel);
        backgroundPanel.add(divvyPanel);
        backgroundPanel.add(homePanel);
    }
    public static void main(String[] args)
    {
        @SuppressWarnings("unused")
        JFrame divvyFrame = new DivvyJava();
    }
}