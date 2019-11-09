package Test;

import java.util.*;
import java.util.regex.*;

public class SimpleBot {
    private int counter = 0;
    final String[] COMMON_PHRASES = {
            "Пока не знаю, что написать, но я придумаю!",
            "Нет ничего ценнее слов, сказанных к мету и ко времени",
            "Порой молчание может сказать больше, нежели уйма слов",
            "Вежливая и приятная речь говорит о величии души",
            "Приятно, когда текст без орфографических ошибок",
            "Записывая слова, мы удваиваем их силу",
            "Кто ясно мыслит, тот ясно излагает",
            "Боюсь, вы что-то не договариваете!",
            "Многословие - есть признак неупорядоченного ума!" };

    final String[] ELUSIVE_ANSWERS = {
            "Вопрос не простой, мне нужно подумать.",
            "Ты хочешь поговорить об этом?",
            "Не уверен, что располагаю такой информацией!",
            "Может сменим тему разговора?",
            "Прошу прощения, но это личное!",
            "Я не уверен, что вам понравится ответ.",
            "Вы действительно хотите это знать?",
            "Уверен, что вы догадались сами.",
            "Давай сохраним интригу.",
            "Зачем вам эта информация?" };

    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        //hello
        put("привет", "hello");
        put("здравствуй", "hello");
        put("добрый день", "hello");
        put("доброе утро", "hello");
        put("добрый вечер", "hello");
        put("здорово", "hello");
        put("хай", "hello");
        //who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        //name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        //howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*сам", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        //whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        //whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        //iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        //yes
        put("^да", "yes");
        put("согласен", "yes");
        //whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        //bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
        put("до\\s.*встречи", "bye");
        //mind
        put("что\\s.*чувствуешь", "mind");
        put("о чем\\s.*думаешь", "mind");
        put("о чём\\s.*думаешь", "mind");
    }};

    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Доброго времени суток");
        put("who", "Я простейший чат-бот.");
        put("name", "Моё имя Бендер.");
        put("howareyou", "Благодарю, просто великолепно.");
        put("whatdoyoudoing", "Я пробую общаться с людьми. На простейшем уровне.");
        put("whatdoyoulike", "Мне нравится, что хозяин завёл себе хобби.");
        put("iamfeelling", "Как давно это началось? Расскажи подробнее.");
        put("yes", "Рад, что ты со мной солидарен.");
        put("mind", "Я не человек, мне сложны эти понятия. Я лишь набор нулей и единиц...");
        put("bye", "Всего доброго. Ещё свидимся.");
    }};

    Pattern pattern;
    Random random;
    Date date;

    SimpleBot() {
        random = new Random();
        date = new Date();
    }

    public String sayInReturn(String msg, boolean ai) {
        String say = (msg.trim().endsWith("?")) ?
                ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)] :
                COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        if (counter == 0) {
            say = "Для начала, как твоё имя?";
            counter++;
        }
        else if (counter == 1) {
            SimpleChatBot.USER_NAME = msg;
            say = "Приятно познакомиться, " + msg + "!";
            counter++;
        }
        else if(ai) {
            String message = String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
            for(Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if(pattern.matcher(message).find()) {
                    if(o.getValue().equals("whattime")) return date.toString();
                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
                }
            }
        }

        return say;
    }
}
