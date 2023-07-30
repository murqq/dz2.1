import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCardTest {

    @BeforeEach
    public void setUp() {
            Selenide.open("http://localhost:9999");
    }

        @Test
        public void shouldSuccessfullySendForm () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("+79040000000");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }

        @Test
        public void shouldThrowMessageIfNameIsLatinWords () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Ivan");
            form.$("[data-test-id=phone] input").setValue("+79040000000");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        public void shouldThrowMessageIfNameIsEmpty () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("");
            form.$("[data-test-id=phone] input").setValue("+79040000000");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        public void shouldThrowMessageIfPhoneIsShort () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("+7904000");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        public void shouldThrowMessageIfPhoneIsLong () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("+790400000000");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        public void shouldThrowMessageIfPhoneIsEmpty () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("");
            form.$("[data-test-id=agreement]").click();
            form.$(".button").click();
            $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        }

        @Test
        public void shouldPaintAgreementMessageRedIfCheckboxEmpty () {
            SelenideElement form = $(".form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("+79040000000");
            form.$(".button").click();
            $("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));
        }

    }