package Test;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatBot extends JFrame implements ActionListener {

    final String TITLE_OF_PROGRAM = "Bender";
    static String USER_NAME = "Неизвестный";
    final int WINDOW_WIDTH = 350;
    final int WINDOW_HEIGHT = 450;

    JTextArea dialogue; // Чат
    JCheckBox ai;       // Включение/Выключение AI
    JTextField message; // Поле для сообщения
    SimpleBot sbot;     // Объект класса SimpleBot

    public static void main(String[] args) {
        new SimpleChatBot();
    }

    SimpleChatBot() {
        setTitle(TITLE_OF_PROGRAM);                   // Название окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);      // Действие при закрытии
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);         // Размеры окна
        setLocationRelativeTo(null);                  // Позиция окна (центр экрана);

        // Область чата =================================================================
        dialogue = new JTextArea();                   // Создание диалогового окна
        dialogue.setLineWrap(true);                   // Строки будут переноситься
        dialogue.setEditable(false);                  // Нельзя печатать в поле диалогового окна
        JScrollPane scrollBar = new JScrollPane(dialogue); // Область вертикального скроллинга
        add(BorderLayout.CENTER, scrollBar);          // Размещение области скроллбара по центру окна

        // Область чекбокса, поля для ввода и кнопки======================================
        JPanel bp = new JPanel();                     // Создание панели для размещения элементов
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS)); // Размещение панели с растягиванием по X (параметр X_AXIS)
        ai = new JCheckBox("AI");                // Создание чекбокса
        ai.doClick();                                 // Регистрация выбора чекбокса
        message = new JTextField();                   // Создание поля для ввода
        message.addActionListener(this);           // Регистрация события нажатия на ENTER на клавиатуре, находясь в поле ввода
        JButton enter = new JButton("ENTER");   // Создание кнопки
        enter.addActionListener(this);             // Регистрация события нажатия на кнопку
        bp.add(ai);                                   // Добавление на панель чекбокса
        bp.add(message);                              // Добавление на панель панели для ввода
        bp.add(enter);                                // Добавление на панель кнопки
        add(BorderLayout.SOUTH, bp);                  // Добавление панели внизу окна

        setVisible(true);                             // Отображение содержимого окна
        message.requestFocus();                       // Установка фокуса на панель ввода (ТОЛЬКО после setVisible())

        sbot = new SimpleBot();                       // Создание бота
    }
    
        // Переопределение метода для обработки событий===================================
    @Override
    public void actionPerformed(ActionEvent event) {
        if(message.getText().trim().length() > 0) {   // Если длина текста больше 0 и это не пробелы, то:
            dialogue.append(USER_NAME + ": " + message.getText() + "\n");// Добавление введённого текста в область чата
            dialogue.append(TITLE_OF_PROGRAM + ": " +
                    sbot.sayInReturn(message.getText(), ai.isSelected()) + "\n");// Ответ бота
        }
        message.setText("");                          // Обнуление текста в панели ввода
    }
}
