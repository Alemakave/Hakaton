package ru.khv1.hakaton.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.khv1.hakaton.configs.BotConfig;
import ru.khv1.hakaton.utils.ServerUtils;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfig botConfig;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText.toLowerCase()) {
                case "/start":
                    sendMessage(chatId, "Добро пожаловать!\n" +
                            "Данный бот разработан для хакатона\n" +
                            "Доступные комманды:\n" +
                            "reboot/restart - перезапуск SQL сервера\n" +
                            "clear          - отчистка дискового пространства");
                    break;
                case "restart":
                case "reboot":
                    ServerUtils.rebootServer();
                    sendMessage(chatId, "Reboot");
                    break;
                case "clear":
                    ServerUtils.clear();
                    sendMessage(chatId, "Clear");
                    break;
                default:
                    sendMessage(chatId, String.format("Команда %s не найдена", messageText));
            }
        }
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                System.out.println(stackTraceElement);
            }
        }
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
