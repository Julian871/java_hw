package com.education.hw.streamApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Customer> customers = getCustomersWithOrders();

        // 1. Получите список продуктов из категории "Books" с ценой более 100.
        List<Product> productsBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0)
                .distinct()
                .toList();

        // 2. Получите список заказов с продуктами из категории "Children's products".
        List<Order> orders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equals("Children's products")))
                .toList();

        // 3. Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех продуктов.
        BigDecimal sum = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Toys"))
                .map(product -> product.getPrice().multiply(new BigDecimal("0.9")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        LocalDate startDate = LocalDate.of(2021, 2, 1);
        LocalDate endDate = LocalDate.of(2021, 4, 1);

        List<Product> productByList = customers.stream()
                .filter(customer -> customer.getLevel() == 2L)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> {
                    LocalDate orderDate = order.getOrderDate();
                    return (orderDate.isEqual(startDate) || orderDate.isAfter(startDate)) &&
                            (orderDate.isBefore(endDate) || orderDate.isEqual(endDate));
                })
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();

        // 5. Получите топ 2 самые дешевые продукты из категории "Books".
        List<Product> topBook = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();

        // 6. Получите 3 самых последних сделанных заказа.
        List<Order> lastOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        // 7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните список их продуктов.
        LocalDate newDate = LocalDate.of(2021, 3, 15);

        List<Product> productList = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> {
                    LocalDate orderDate = order.getOrderDate();
                    return orderDate.isEqual(newDate);
                })
                .peek(order -> System.out.println(order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();

        // 8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        LocalDate startDate2 = LocalDate.of(2021, 1, 31);
        LocalDate startDate3 = LocalDate.of(2021, 3, 1);
        BigDecimal sumOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> {
                    LocalDate orderDate = order.getOrderDate();
                    return (orderDate.isAfter(startDate2) || orderDate.isBefore(startDate3));
                })
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        LocalDate date4 = LocalDate.of(2021, 3, 14);
        List<Order> ordersOnMarch14 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(date4))
                .toList();

        List<BigDecimal> orderSums = ordersOnMarch14.stream()
                .map(order -> order.getProducts().stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .toList();

        BigDecimal averagePayment = orderSums.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(orderSums.size()), 2, RoundingMode.HALF_UP);

        // 10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество)
        // для всех продуктов категории "Книги".
        DoubleSummaryStatistics booksStats = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .mapToDouble(product -> product.getPrice().doubleValue())
                .summaryStatistics();

        // 11. Получите данные 11. Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> orderProductCount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size()
                ));

        // 12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        Map<Customer, List<Order>> customerListMap = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> customer.getOrders().stream().toList()
                ));

        // 13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> orderDoubleMap = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(
                        Function.identity(),
                        order -> order.getProducts().stream()
                                .mapToDouble(product -> product.getPrice().doubleValue())
                                .sum()
                ));

        // 14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> stringListMap = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(
                                Product::getName,
                                Collectors.toList()
                        )
                ));

        // 15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> expensiveProduct = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                optionalProduct -> optionalProduct.orElse(null)
                        )
                ));
    }

    public static List<Product> getProducts() {
        return List.of(
                // Books (Книги)
                new Product(1L, "Java для начинающих", "Books", new BigDecimal("45.50")),
                new Product(2L, "Программирование на Python", "Books", new BigDecimal("120.00")),
                new Product(3L, "Алгоритмы и структуры данных", "Books", new BigDecimal("150.00")),
                new Product(4L, "Чистый код", "Books", new BigDecimal("110.00")),
                new Product(5L, "Совершенный код", "Books", new BigDecimal("95.00")),
                new Product(6L, "Дизайн-паттерны", "Books", new BigDecimal("130.00")),
                new Product(7L, "Микросервисы", "Books", new BigDecimal("180.00")),
                new Product(8L, "Spring в действии", "Books", new BigDecimal("85.00")),
                new Product(9L, "JavaScript для детей", "Children's products", new BigDecimal("40.00")),
                new Product(10L, "Конструктор LEGO", "Toys", new BigDecimal("200.00")),
                new Product(11L, "Мягкая игрушка Мишка", "Toys", new BigDecimal("35.00")),
                new Product(12L, "Развивающая пирамидка", "Children's products", new BigDecimal("25.00")),
                new Product(13L, "Детская книга со звуками", "Children's products", new BigDecimal("30.00")),
                new Product(14L, "Радиоуправляемая машина", "Toys", new BigDecimal("150.00")),
                new Product(15L, "Настольная игра Монополия", "Toys", new BigDecimal("80.00")),
                new Product(16L, "Пазл 1000 деталей", "Toys", new BigDecimal("45.00")),
                new Product(17L, "Детский конструктор", "Toys", new BigDecimal("120.00")),
                new Product(18L, "Электроника для детей", "Children's products", new BigDecimal("55.00")),
                new Product(19L, "Ноутбук", "Electronics", new BigDecimal("1200.00")),
                new Product(20L, "Смартфон", "Electronics", new BigDecimal("800.00"))
        );
    }

    public static List<Order> getOrders() {
        List<Product> productList = getProducts();

        return List.of(
                // Заказы для клиента 1 (Иван Петров, уровень 1)
                new Order(1L,
                        LocalDate.of(2021, 2, 5),
                        LocalDate.of(2021, 2, 10),
                        "Доставлен",
                        Set.of(productList.get(0), productList.get(1), productList.get(9))),
                new Order(2L,
                        LocalDate.of(2021, 2, 15),
                        LocalDate.of(2021, 2, 20),
                        "Доставлен",
                        Set.of(productList.get(2), productList.get(10))),
                new Order(3L,
                        LocalDate.of(2021, 3, 10),
                        LocalDate.of(2021, 3, 15),
                        "Доставлен",
                        Set.of(productList.get(11), productList.get(12), productList.get(13))),
                new Order(4L,
                        LocalDate.of(2021, 3, 14),
                        LocalDate.of(2021, 3, 18),
                        "Доставлен",
                        Set.of(productList.get(3), productList.get(4))),
                new Order(5L,
                        LocalDate.of(2021, 3, 15),
                        LocalDate.of(2021, 3, 20),
                        "В пути",
                        Set.of(productList.get(14), productList.get(15))),

                // Заказы для клиента 2 (Мария Сидорова, уровень 2)
                new Order(6L,
                        LocalDate.of(2021, 2, 8),
                        LocalDate.of(2021, 2, 12),
                        "Доставлен",
                        Set.of(productList.get(5), productList.get(16))),
                new Order(7L,
                        LocalDate.of(2021, 2, 20),
                        LocalDate.of(2021, 2, 25),
                        "Доставлен",
                        Set.of(productList.get(1), productList.get(6), productList.get(17))),
                new Order(8L,
                        LocalDate.of(2021, 3, 14),
                        LocalDate.of(2021, 3, 19),
                        "Доставлен",
                        Set.of(productList.get(2), productList.get(7), productList.get(18))),
                new Order(9L,
                        LocalDate.of(2021, 3, 15),
                        null,
                        "Ожидает оплаты",
                        Set.of(productList.get(0), productList.get(8))),
                new Order(10L,
                        LocalDate.of(2021, 3, 20),
                        null,
                        "В пути",
                        Set.of(productList.get(9), productList.get(10), productList.get(11))),

                // Заказы для клиента 3 (Алексей Иванов, уровень 1)
                new Order(11L,
                        LocalDate.of(2021, 2, 12),
                        LocalDate.of(2021, 2, 18),
                        "Доставлен",
                        Set.of(productList.get(12), productList.get(13))),
                new Order(12L,
                        LocalDate.of(2021, 2, 25),
                        LocalDate.of(2021, 3, 1),
                        "Доставлен",
                        Set.of(productList.get(3), productList.get(14))),
                new Order(13L,
                        LocalDate.of(2021, 3, 5),
                        LocalDate.of(2021, 3, 10),
                        "Доставлен",
                        Set.of(productList.get(15), productList.get(16), productList.get(17))),
                new Order(14L,
                        LocalDate.of(2021, 3, 14),
                        LocalDate.of(2021, 3, 18),
                        "Доставлен",
                        Set.of(productList.get(0), productList.get(4))),
                new Order(15L,
                        LocalDate.of(2021, 3, 15),
                        LocalDate.of(2021, 3, 22),
                        "Доставлен",
                        Set.of(productList.get(1), productList.get(5))),

                // Заказы для клиента 4 (Елена Козлова, уровень 3)
                new Order(16L,
                        LocalDate.of(2021, 2, 1),
                        LocalDate.of(2021, 2, 6),
                        "Доставлен",
                        Set.of(productList.get(6), productList.get(7))),
                new Order(17L,
                        LocalDate.of(2021, 2, 18),
                        LocalDate.of(2021, 2, 23),
                        "Доставлен",
                        Set.of(productList.get(2), productList.get(8), productList.get(18))),
                new Order(18L,
                        LocalDate.of(2021, 3, 14),
                        LocalDate.of(2021, 3, 17),
                        "Доставлен",
                        Set.of(productList.get(9), productList.get(10))),
                new Order(19L,
                        LocalDate.of(2021, 3, 15),
                        LocalDate.of(2021, 3, 21),
                        "В пути",
                        Set.of(productList.get(11), productList.get(12))),
                new Order(20L,
                        LocalDate.of(2021, 3, 22),
                        null,
                        "Обрабатывается",
                        Set.of(productList.get(13), productList.get(14))),

                // Заказы для клиента 5 (Дмитрий Соколов, уровень 2)
                new Order(21L,
                        LocalDate.of(2021, 2, 10),
                        LocalDate.of(2021, 2, 15),
                        "Доставлен",
                        Set.of(productList.get(3), productList.get(15))),
                new Order(22L,
                        LocalDate.of(2021, 2, 28),
                        LocalDate.of(2021, 3, 4),
                        "Доставлен",
                        Set.of(productList.get(0), productList.get(1), productList.get(16))),
                new Order(23L,
                        LocalDate.of(2021, 3, 14),
                        LocalDate.of(2021, 3, 19),
                        "Доставлен",
                        Set.of(productList.get(2), productList.get(4), productList.get(17))),
                new Order(24L,
                        LocalDate.of(2021, 3, 15),
                        null,
                        "Ожидает оплаты",
                        Set.of(productList.get(5), productList.get(6))),
                new Order(25L,
                        LocalDate.of(2021, 3, 25),
                        LocalDate.of(2021, 3, 30),
                        "В пути",
                        Set.of(productList.get(7), productList.get(8), productList.get(9)))
        );
    }

    public static List<Customer> getCustomersWithOrders() {
        List<Order> allOrders = getOrders();

        return List.of(
                new Customer(1L, "Иван Петров", 1L, Set.of(
                        allOrders.get(0), allOrders.get(1), allOrders.get(2),
                        allOrders.get(3), allOrders.get(4)
                )),
                new Customer(2L, "Мария Сидорова", 2L, Set.of(
                        allOrders.get(5), allOrders.get(6), allOrders.get(7),
                        allOrders.get(8), allOrders.get(9)
                )),
                new Customer(3L, "Алексей Иванов", 1L, Set.of(
                        allOrders.get(10), allOrders.get(11), allOrders.get(12),
                        allOrders.get(13), allOrders.get(14)
                )),
                new Customer(4L, "Елена Козлова", 3L, Set.of(
                        allOrders.get(15), allOrders.get(16), allOrders.get(17),
                        allOrders.get(18), allOrders.get(19)
                )),
                new Customer(5L, "Дмитрий Соколов", 2L, Set.of(
                        allOrders.get(20), allOrders.get(21), allOrders.get(22),
                        allOrders.get(23), allOrders.get(24)
                ))
        );
    }
}