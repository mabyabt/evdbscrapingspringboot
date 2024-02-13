package com.example.demo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {
    @GetMapping("/scrape-cars")
    public List<Car> scrapeCars() {
        List<Car> cars = new ArrayList<>();
        try {
            // URL of the page you want to scrape
            String url = "https://ev-database.org/";

            // Send a GET request to the URL and parse the HTML
            Document document = Jsoup.connect(url).get();

            // Find the specific data you're interested in
            // For example, let's say you want to extract the names and prices of electric cars within a price range
            Elements carElements = document.select("div.car-wrapper");

            // Extract the names and prices of the electric cars
            for (Element carElement : carElements) {
                Element titleElement = carElement.selectFirst("div.car-title");
                String title = titleElement.text();

                Element priceElement = carElement.selectFirst("div.price");
                String priceText = priceElement.text();
                int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));

                // Define your price range here
                int minPrice = 30000;
                int maxPrice = 60000;

                // Check if the car's price is within the specified range
                if (price >= minPrice && price <= maxPrice) {
                    cars.add(new Car(title, priceText));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
