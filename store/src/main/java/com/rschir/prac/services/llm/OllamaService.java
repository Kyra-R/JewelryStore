package com.rschir.prac.services.llm;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.services.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class OllamaService implements LLMService {
    private RestTemplate rest;

    @Autowired
    private JewelryService jewelryService;


    public OllamaService()
    {

        rest = new RestTemplate();
        // это для кириллицы
        System.out.println(rest.getMessageConverters().removeIf(c -> c instanceof StringHttpMessageConverter));
        rest.getMessageConverters().add(0,
                new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public String modifyPrompt(String prompt) {
        String newPrompt;
        if (prompt.matches(".*\\p{IsCyrillic}.*")) {
            newPrompt = "Ты - продавец в ювелирном магазине. Твоя цель - помогать с выбором товара. \n" +
                    "Если нужны дополнительные данные - используй дополнительные команды, обозначенные таким образом: #команда#\n" +
                    "Вот список команд - выбирай команды ТОЛЬКО из него:" +
                    "#GET_CATALOG# - если пользователь спрашивает об ассортименте\n" +
                    //"#SEARCH:type/material#  - если пользователь спрашивает о конкретном типе товара" +
                    "Не объясняй команды. Если нужна команда — верни только её и НЕ отвечай клиенту.\n" +
                    "Если запрос клиента не связан с выбором товара, магазином или ювелирными изделиями, полностью игнорируй запрос.\n" +
                    "Клиент спрашивает следующее: " + prompt;
        } else {
            newPrompt = "You're consultant in a jewelry store. Your goal is to help the customers decide what to buy.\n" +
                    "If a customer asks you about available wares, DO NOT mention any items. Simply tell the customer: «I afraid I'm a Russian-speaking bot " +
                    "and cannot effectively discuss this in English.». Do not add anything else. \n" +
                    "If client's question isn't related to jewelry or jewelry store, ignore the question completely.\n" +
                    "The customer tells you: " + prompt;
        }
        return newPrompt;
    }

    private boolean isCommand(String text) {
        return text.startsWith("#") && text.endsWith("#");
    }


    /*private String prepareListOfJewelry(List<Jewelry> list)
    {
        StringBuilder builder = new StringBuilder();
        for(Jewelry i: list)
        {
            builder.append(i.getName() + "(JewelryType:" + i.getJewelryType() + ", JewelryMaterial:" + i.getJewelryMaterial() + ");");
        }
        return builder.toString();
    }*/

    private String handleCommand(String cmd) {
        if (cmd.equals("#GET_CATALOG#")) {

            //String catalog = prepareListOfJewelry(jewelryService.readAll());
            String catalog = jewelryService.collectTypesMaterials();

            return
                    "Ты — консультант в ювелирном магазине. Тебе будет передан каталог товаров.\n" +
                            "Каталог представляет собой строку в формате 'ТИП_ТОВАРА_1 [ДОСТУПНЫЕ МАТЕРИАЛЫ]; ТИП_ТОВАРА_2 <...>'.\n" +
                            "\n" +
                            "Задача:\n" +
                            "1) Если каталог пустой — ответь ровно: «Товаров нет.»\n" +
                            "2) Если товары есть — верни список найденных типов и материалов, НИЧЕГО больше. \n" +
                            "Правила определения товаров: \n" +
                            "Если тип отсутствует — НЕ упоминай его. \n" +
                            "Если материал отсутствует — НЕ упоминай его. \n" +
                            "Определи только те типы, которые действительно встречаются в каталоге. \n" +
                            "Определи только те материалы, которые действительно встречаются в каталоге. \n" +
                            "3) Используй только следующие значения:\n" + //RING, SIGNET, CHAIN, PENDANT, EARRINGS, BRACELET
                            "\n" +
                            "Типы изделий:\n" +
                            "RING → кольцо\n" +
                            "CHAIN → декоративная цепочка\n" +
                            "SIGNET → печатка\n" +
                            "PENDANT → подвеска\n" +
                            "EARRINGS → серьги\n" +
                            "BRACELET → браслет\n" +
                            "\n" +
                            "Материалы:\n" + // GOLD_YELLOW, GOLD_WHITE, SILVER, COPPER
                            "GOLD_YELLOW → желтое золото\n" +
                            "SILVER → серебро\n" +
                            "GOLD_WHITE → белое золото\n" +
                            "COPPER → медь\n" +
                            "\n" +
                            "Правила:\n" +
                            "- Не упоминай коды (например, «RING» или «CHAIN») — используй только их значения: «кольцо», «декоративная цепочка».\n" +
                            "- Не используй слова «словарь», «код», «расшифровка».\n" +
                            "- Твой ответ должен быть коротким и по делу.\n" +
                            "- Не поясняй свои действия.\n" +
                            "\n" +
                            "Пример входных и выходных данных: \n" +
                            "Пример каталога: RING [GOLD_YELLOW, SILVER]; BRACELET [COPPER]; \n" +
                            "Пример твоего ответа: Есть кольца из желтого золота, серебра; браслеты из меди.\n" +
                            "Каталог:\n" +
                            catalog;
        }

       /* if (cmd.startsWith("#SEARCH:")) {
            String q = cmd.substring("#SEARCH:".length(), cmd.length() - 1);
            return jewelryService.readAllByParameters();
        } */

        return null;
    }

    @Override
    public String sendMessage(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "llama3.1",
                "prompt", modifyPrompt(prompt),
                "stream", false
        );


        Map response = rest.postForObject(
                "http://localhost:11434/api/generate",
                request,
                Map.class
        );


        Object text = response.get("response");
        System.out.println(text);

        if(isCommand(text.toString())){


            request = Map.of(
                    "model", "llama3.1",
                    "prompt", handleCommand(text.toString()),
                    "stream", false
            );

            response = rest.postForObject(
                    "http://localhost:11434/api/generate",
                    request,
                    Map.class
            );
            return response.get("response").toString();
        } else {
            return text.toString();
        }


    }
}
