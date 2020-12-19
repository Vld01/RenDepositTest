package ru.appline.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Test;
import ru.appline.base.BaseTests;

public class RenAllureTest extends BaseTests {

    @Epic("Тест для Ренесанс")
    @Feature(value = "Тест вклада в Рублях")
    @Test
    public void depositInRenTest1(){
        app.getStartPage()
                .choiceDeposit()
                .checkActualPage()
                .choiceCurrency("Рубли")
                .fillField("Сумма вклада", 300_000)
                .fillPeriodDeposit("6 месяцев")
                .fillField("Ежемесячное пополнение", 50_000)
                .fillCheckbox("Ежемесячная капитализация")
                .checkFieldValue("Начислено %:", 9132.17)
                .checkFieldValue("Пополнение за 6 месяцев:", 250000.)
                .checkFieldValue("К снятию через 6 месяцев", 559132.17);
    }

    @Epic("Тест для Ренесанс")
    @Feature(value = "Тест вклада в Долларах США")
    @Test
    public void depositInRenTest2(){
        app.getStartPage()
                .choiceDeposit()
                .checkActualPage()
                .choiceCurrency("Доллары США")
                .fillField("Сумма вклада", 500_000)
                .fillPeriodDeposit("12 месяцев")
                .fillField("Ежемесячное пополнение", 30_000)
                .fillCheckbox("Ежемесячная капитализация")
                .checkFieldValue("Начислено %:", 1003.59)
                .checkFieldValue("Пополнение за 12 месяцев:", 330000.)
                .checkFieldValue("К снятию через 12 месяцев", 831003.59);
    }
}
