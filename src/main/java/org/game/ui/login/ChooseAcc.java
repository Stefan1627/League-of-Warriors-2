package org.game.ui.login;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

@Setter
@Getter
class Book {
    private String name;
    private String author;
    private String iconName;

    public Book(String name, String author, String iconName) {
        this.name = name;
        this.author = author;
        this.iconName = iconName;
    }

    public String getIconPath() {
        return iconName;
    }

    @Override
    public String toString() {
        return iconName + name + author;
    }
}

// Custom Renderer
class BookRenderer extends JPanel implements ListCellRenderer<Book> {
    private final JLabel titleLabel = new JLabel();
    private final JLabel authorLabel = new JLabel();
    private final JLabel iconLabel = new JLabel();

    public BookRenderer() {
        setLayout(new BorderLayout(10, 10));
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(titleLabel);
        textPanel.add(authorLabel);
        add(iconLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
        textPanel.setOpaque(false);
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Book> list, Book book, int index, boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText(book.getName());
        authorLabel.setText(book.getAuthor());
        titleLabel.setForeground(Color.WHITE);
        authorLabel.setForeground(Color.LIGHT_GRAY);

        // Load icon
        if (!book.getIconPath().isEmpty()) {
            iconLabel.setIcon(new ImageIcon(book.getIconPath()));
        } else {
            iconLabel.setIcon(null);
        }

        // Apply background colors
        if (isSelected) {
            setBackground(Color.RED);
        } else if (index % 2 == 0) {
            setBackground(new Color(30, 30, 30));
        } else {
            setBackground(new Color(45, 45, 45));
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return this;
    }
}

public class ChooseAcc {
    public static void setupChooseAcc(JPanel panel) {
        // Set background color
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BorderLayout());

        // Sample books
        Book[] books = {
                new Book("C++ Programming", "Author1", "c++.png"),
                new Book("Java Programming", "Author2", "java.png"),
                new Book("C# Programming", "Author3", "csharp.png"),
                new Book("iOS Programming", "Author4", "ios.png"),
                new Book("Windows Phone Programming", "Author5", "windows.png"),
                new Book("Android Programming", "Author6", "android.png")
        };

        DefaultListModel<Book> bookModel = new DefaultListModel<>();
        for (Book book : books) {
            bookModel.addElement(book);
        }

        JList<Book> bookJList = new JList<>(bookModel);
        bookJList.setCellRenderer(new BookRenderer());
        bookJList.setBackground(new Color(30, 30, 30));

        // Center the JList in its container
        JPanel listWrapper = new JPanel(new GridBagLayout());
        listWrapper.setBackground(new Color(30, 30, 30));
        JScrollPane scrollPane = new JScrollPane(bookJList);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        // Remove the border of the JScrollPane
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));

        // Add the JScrollPane to the center of the wrapper
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        listWrapper.add(scrollPane, gbc);

        // Details Panel
        JTextField nameField = new JTextField(20);
        nameField.setEditable(false);
        nameField.setBackground(new Color(50, 50, 50));
        nameField.setForeground(Color.WHITE);
        nameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField authorField = new JTextField(20);
        authorField.setEditable(false);
        authorField.setBackground(new Color(50, 50, 50));
        authorField.setForeground(Color.WHITE);
        authorField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setForeground(Color.WHITE);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        detailsPanel.setBackground(new Color(30, 30, 30));
        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);
        detailsPanel.add(authorLabel);
        detailsPanel.add(authorField);

        // Main Panel with BorderLayout
        panel.add(listWrapper, BorderLayout.CENTER);
        panel.add(detailsPanel, BorderLayout.SOUTH);
    }
}
