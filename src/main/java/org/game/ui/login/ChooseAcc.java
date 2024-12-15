package org.game.ui.login;

import org.game.account.Account;
import org.game.game.Game;
import org.game.ui.utils.CustomRenderer;
import org.game.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class ChooseAcc {
    public static void setupChooseAcc(JPanel panel, Game game, CardLayout cardLayout) {
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BorderLayout());

        DefaultListModel<Account> accountModel = new DefaultListModel<>();
        for (Account account : game.getAccounts()) {
            accountModel.addElement(account);
        }

        JList<Account> accountList = new JList<>(accountModel);
        accountList.setCellRenderer(new CustomRenderer<>());
        accountList.setBackground(new Color(30, 30, 30));

        accountList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Account selectedAccount = accountList.getSelectedValue();
                if (selectedAccount != null) {
                    game.setCurrAccount(selectedAccount);

                    JPanel chooseCharacterPanel = new JPanel();
                    ChooseCharacter.setupChooseCharacter(chooseCharacterPanel, game);

                    panel.getParent().add(chooseCharacterPanel, "ChooseCharacter");
                    cardLayout.show(panel.getParent(), "ChooseCharacter");
                }
            }
        });

        // Center the JList in its container
        JPanel listWrapper = UIUtils.createListWrapper(accountList);

        panel.add(listWrapper, BorderLayout.CENTER);
    }
}
