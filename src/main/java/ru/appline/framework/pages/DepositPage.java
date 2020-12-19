package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.*;
import static ru.appline.framework.managers.DriverManager.getDriver;


public class DepositPage extends BasePage {
    // флаг переключения фрейма
    private boolean frameFlag = false;

    @FindBy(xpath = "//label[text()='Сумма вклада']/..//input")
    WebElement sumDepositElement;

    @FindBy(xpath = "//label[text()='Ежемесячное пополнение']/..//input")
    WebElement monthlyTopUpElement;

    @FindBy(xpath = "//span[text()='Рубли']")
    WebElement rubleElement;

    @FindBy(xpath = "//span[text()='Доллары США']")
    WebElement dollarElement;

    @FindBy(xpath = "//label[text() = 'На срок']/..//select")
    WebElement periodDepositElement;

    @FindBy(xpath = "//label[text() = 'На срок']/..//div[@class = 'jq-selectbox__select-text']")
    WebElement checkPeriodDepositElement;

    @FindBy(id ="iFrameResizer0")
    WebElement iFrame;

    @FindBy(xpath = "//h1")
    WebElement depositHeaderElement;

    @FindBy(xpath = "//span[text() = 'Ежемесячная капитализация']/../..//div[@class ='jq-checkbox calculator__check']")
    private WebElement monthlyCapitalizationCheckBox;

    @FindBy(xpath = "//span[text() = 'Частичное снятие']/../..//div[@class ='jq-checkbox calculator__check']")
    private WebElement partTakeOffCheckBox;

    @FindBy(xpath = "//span[text() = 'Ежемесячная капитализация']/../..//div[contains(@class, 'checked')]")
    private List<WebElement> monthlyCapitalizationChecked;

    @FindBy(xpath = "//span[text() = 'Частичное снятие']/../..//div[contains(@class, 'checked')]")
    private List<WebElement> partTakeOffChecked;

    @FindBy(xpath = "//td[text() = 'Начислено %:']/..//span[@class = 'js-calc-earned']")
    private WebElement calculationPercentElement;

    @FindBy(xpath = "//td[text() = 'Пополнение за ']/..//span[@class = 'js-calc-replenish']")
    private WebElement topUpElement;

    @FindBy(xpath = "//div[text() = 'К снятию через ']/..//span[@class = 'js-calc-result']")
    private WebElement totalSumElement;

    /**
     * Метод проверки перехода на страницу
     *
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Проверяем переход на страницу 'Вклады'")
    public DepositPage checkActualPage() {
        elementToBeVisible(depositHeaderElement);
        assertEquals("Заголовок отсутствует/не соответствует требуемому", "Вклады", depositHeaderElement.getText());
        return this;
    }

    /**
     * Метод выбора валюты
     *
     * @param currency - название валюты Рубли или Доллары США
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Выбираем валюту '{currency}'")
    public DepositPage choiceCurrency(String currency) {
        WebElement element = null;
        switch (currency) {
            case "Рубли":
                element = rubleElement;
                break;
            case "Доллары США":
                element = dollarElement;
                break;
            default:
                fail("Выбор валюты '" + currency + "' отсутствует на странице 'Вклады'");
        }
        elementToBeClickable(element).click();
        return this;
    }

    /**
     * Метод заполнения полей
     *
     * @param nameField - имя веб элемента, поля ввода
     * @param value     - значение вводимое в поле
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Заполняем поле '{nameField}' значение '{value}'")
    public DepositPage fillField(String nameField, Integer value) {
        WebElement element = null;
        switch (nameField) {
            case "Сумма вклада":
                fillInputField(sumDepositElement, value);
                element = sumDepositElement;
                break;
            case "Ежемесячное пополнение":
                fillInputField(monthlyTopUpElement, value);
                element = monthlyTopUpElement;
                break;
            default:
                fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Вклады'");

        }
        assertEquals("Поле '" + nameField + "' было заполнено некорректно", value, utilParsInteger(element.getAttribute("value")));
        return this;
    }

    /**
     * Метод заполнения срока вклада
     *
     * @param value     - срок вклада формата '9 месяцев'
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Выбираем срок вклада со значением '{value}'")
    public DepositPage fillPeriodDeposit(String value) {
        Select select = new Select(periodDepositElement);
        select.selectByVisibleText(value);
        assertEquals("Срок вклада был заполнен некорректно", value, checkPeriodDepositElement.getText());
        return this;
    }

    /**
     * Метод заполнения чекбоксов
     *
     * @param nameField - имя веб элемента, поля ввода
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Заполняем поле '{nameField}' значением true")
    public DepositPage fillCheckbox(String nameField) {
        List <WebElement> elements = null;
        switch (nameField) {
            case "Ежемесячная капитализация":
                monthlyCapitalizationCheckBox.click();
                elements = monthlyCapitalizationChecked;
                break;
            case "Частичное снятие":
                partTakeOffCheckBox.click();
                elements = partTakeOffChecked;
                break;
            default:
                fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Вклады'");

        }
        assertFalse("Поле '" + nameField + "' было заполнено некорректно", elements.isEmpty());
        return this;
    }

    /**
     * Метод проверки результирующих значений
     *
     * @param nameElement - имя веб элемента, поля ввода
     * @param value     - значение актуальное
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Сравниваем элемент '{nameElement}' значение '{value}'")
    public DepositPage checkFieldValue(String nameElement, Double value) {
        switch (nameElement) {
            case "Начислено %:":
                checkValueElement(calculationPercentElement, value, nameElement);
                break;
            case "Пополнение за 6 месяцев:":
                checkValueElement(topUpElement, value, nameElement);
                break;
            case "К снятию через 6 месяцев":
                checkValueElement(totalSumElement, value, nameElement);
                break;
            case "Пополнение за 12 месяцев:":
                checkValueElement(topUpElement, value, nameElement);
                break;
            case "К снятию через 12 месяцев":
                checkValueElement(totalSumElement, value, nameElement);
                break;
            default:
                fail("Поле с наименованием '" + nameElement + "' отсутствует на странице.");
                break;
        }
        return this;
    }
}
