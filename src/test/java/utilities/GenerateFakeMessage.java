package utilities;

import com.github.javafaker.Faker;

public class GenerateFakeMessage {

    public static String getFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String getEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String getPassword() {
        Faker faker = new Faker();
        return faker.numerify("string@##");
    }

}
