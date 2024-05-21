package com.RouteBus.client.gui;

import javax.swing.*;
import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.NationalityDTO;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("serial")
public abstract class MultilingualLoadingWindow extends ParentWindow {
	
	private static final String messagesPath = "multilingual/messages";

    protected ResourceBundle messages;
    protected JPanel mainPanel;
    protected JMenu languageMenu;

	public MultilingualLoadingWindow(String languageToLoad) {
        super();
        Locale currentLocale;
        if (languageToLoad == null) {
            currentLocale = Locale.getDefault();
        } else {
            currentLocale = new Locale(languageToLoad);
        }
        messages = ResourceBundle.getBundle(messagesPath, currentLocale);

        this.setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Loading panel with progress bar
        JPanel loadingPanel = new JPanel(new BorderLayout());
        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loadingPanel.add(loadingLabel, BorderLayout.NORTH);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        progressBar.setForeground(colorSecondary);
        loadingPanel.add(progressBar, BorderLayout.CENTER);
        add(loadingPanel, BorderLayout.CENTER);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(colorBackground);

        loadPanelsAsync(loadingPanel, progressBar);

        initializeMenuBar();
    }

    private void loadPanelsAsync(JPanel loadingPanel, JProgressBar progressBar) {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish(0);
                loadPanels();
                publish(100);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int progress : chunks) {
                    progressBar.setValue(progress);
                }
            }

            @Override
            protected void done() {
                try {
                    get();
                    remove(loadingPanel);
                    add(mainPanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorSecondary);

        languageMenu = new JMenu(messages.getString("languageMenu"));

        menuBar.add(languageMenu);
        setJMenuBar(menuBar);

        loadLanguages();
        SwingUtilities.invokeLater(this::applyMenuColors);
    }

    private void loadLanguages() {
        List<NationalityDTO> nationalities = UserController.getInstance().getNationalities();
        for (NationalityDTO nationality : nationalities) {
            JMenuItem languageItem = new JMenuItem(nationality.getName());
            languageItem.addActionListener(e -> changeLanguage(nationality.getLanguage()));
            languageMenu.add(languageItem);
        }
    }

    protected void changeLanguage(String selectedLocale) {
        Locale locale = new Locale(selectedLocale);
        messages = ResourceBundle.getBundle(messagesPath, locale);

        updateTexts();
        applyMenuColors();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    protected void applyMenuColors() {
        JMenuBar menuBar = getJMenuBar();
        for (Component component : menuBar.getComponents()) {
            component.setBackground(colorSecondary);
        }
    }

    protected abstract void loadPanels();

    protected abstract void updateTexts();
}