package netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.By.cssSelector;

public class CardOrderTest {
private WebDriver driver;
    @BeforeAll
    public static void setUpAll () {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp(){
    ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
    driver = new ChromeDriver(options);
    }
    @AfterEach
    public void tearDown (){
        driver.quit();
        driver = null;
    }
    @Test
    void shouldTestHappyPath() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов Сергей");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79166719635");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.strip());
    }

    @Test
    void shouldTestEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }
    @Test
    void shouldTestWithoutName() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79166719635");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }
    @Test
    void shouldTestWithoutPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys(" Смирнов Сергей");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }
    @Test
    void shouldTestWithoutCheckBox() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов Сергей");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79166719635");
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", message.strip());
    }
    @Test
    void shouldTestInvalidName() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Smirnov Sergey");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79166719635");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", message.strip());
    }
    @Test
    void shouldTestInvalidName2() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов_Сергей!");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79166719635");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", message.strip());
    }
    @Test
    void shouldTestInvalidPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов Сергей");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+7916671963");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.strip());
    }
    @Test
    void shouldTestInvalidPhone2() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Смирнов Сергей");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("Рыба");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.strip());
    }
}
