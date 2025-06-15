import Interface.Login;
import Logic.ControllerSpa;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;


import javax.swing.*;
import java.awt.*;


public class Main {

    public static void main(String[] args) {

        FlatDarkLaf.setup();
        UIManager.put("Button.arc", 10); // Botones redondeados
        UIManager.put("Component.arc", 10); // Componentes redondeados
        UIManager.put("TextComponent.arc", 5); // Campos de texto redondeados
        FlatAnimatedLafChange.showSnapshot();

        FlatAnimatedLafChange.hideSnapshotWithAnimation();
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 13);
        UIManager.put("Button.font", modernFont);
        UIManager.put("Label.font", modernFont);
        UIManager.put("Table.font", modernFont);
        UIManager.put("TextField.font", modernFont);

        UIManager.put("Panel.background", new Color(0x2B2B2B));
        UIManager.put("Table.background", new Color(0x323232));
        UIManager.put("Table.selectionBackground", new Color(0x3A6EA5));

        UIManager.put("Button.background", new Color(0x404040));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.hoverBackground", new Color(0x4D4D4D));
        UIManager.put("Button.pressedBackground", new Color(0x333333));

        UIManager.put("Button.font", modernFont);
        UIManager.put("Label.font", modernFont);
        UIManager.put("Table.font", modernFont);
        UIManager.put("TableHeader.font", modernFont.deriveFont(Font.BOLD));
        UIManager.put("TextField.font", modernFont);

        UIManager.put("Label.foreground", new Color(0xE0E0E0));
        UIManager.put("TextComponent.foreground", Color.WHITE);

        JFrame frame = new JFrame("Spa Belleza y Relajación");
        ControllerSpa app = new ControllerSpa();
        Login.getWindow(app, frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}