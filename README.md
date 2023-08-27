# Showcase-of-goods
Showcase-of-goods REST-сервис использует Java 8, Spring boot, Hibernate, PostgreSQL, Liquibase
## Описание
Тестовое задание от Лидер-ИТ.
Необходимо создать REST-сервис для хранения данных о витринах товаров в магазинах бытовой техники.
Полное тестовое задание: https://github.com/PetrFateyev/Showcase-of-goods/blob/master/showcase-of-goods/doc/Тестовое.pdf

## Запуск приложения
Для запуска потребуются: Java 8, PostgreSQL и пользователь с соответствующими правами.     

Сборка проекта: Maven  

Сборка и запуск на Windows:  
```
mvn spring-boot:run
```  

Настройки по умолчанию:  
```
порт сервера: 5000  
хост БД:      localhost   
порт БД:      5432  
название БД:  postgres 
пользователь: postgres  
пароль:       123   
```
Можно изменить в файле src/main/resources/application.properties.

Таблицы в базе данных будут сформированы во время сборки. 

## Описание методов

  1. Метод для получения всех витрин.
   Позволяет получить все витрины с возможностью фильтрации по типу, адресу, за период по дате создания и за период по дате последней актуализации.
```java
@GetMapping("/showcases")
    public  Page<Showcase> getAll(
            @And({
                    @Spec(path = "type", spec = Equal.class),
                    @Spec(path = "address", spec = Equal.class),
                    @Spec(
                            path = "createdDate",
                            params = {"afterCreation", "beforeCreation"},
                            spec = Between.class
                    ),
                    @Spec(
                            path = "modifiedDate",
                            params = {"afterModified", "beforeModified"},
                            spec = Between.class
                    )
            }) Specification<Showcase> showcaseSpecification, Pageable pageable){
        return showcaseService.findAll(showcaseSpecification, pageable);
    }
```
2. Метод для получения всех товаров витрины.
   Позволяет получить все товары витрины с фильтрацией по типу и дипозону цен.
   ```java
@GetMapping("showcases/{id}/products")
    public Page<Product> findAll(
            @Join(path = "showcases", alias = "s")
            @And({
                @Spec(path = "type", spec = Equal.class),
                @Spec(
                        path = "price",
                        params = {"min", "max"},
                        spec = Between.class
                )
    }) Specification<Product> productSpecificationpecification, Pageable pageable, @PathVariable(value = "id")UUID showcaseId){
        return productService.findAll(productSpecificationpecification, pageable, showcaseId);
    }
``
3. Метод для добавления витрины.
   Позволяет добавить витрину.
```java
@PostMapping("/showcases")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Showcase showcase){
        showcaseService.save(showcase);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
```
4. Метод для добавления товара на витрину.
   Позволяет добавить товар на витрину.
```java
@PostMapping("showcases/{id}/products")
    public ResponseEntity<HttpStatus> create(@PathVariable("id") UUID id, @RequestBody @Valid Product product){
        productService.save(id, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
5. Метод для изменения витрины.
   Позволяет изменить витрину.
```java
@PutMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id, @RequestBody @Valid Showcase showcase) {
        showcaseService.update(id, showcase);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
6. Метод для изменения товара.
   Позволяет изменить товар.
```java
@PutMapping("showcases/{id}/products/{productId}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id,
                                             @PathVariable("productId") UUID productId, @RequestBody @Valid Product product){
        productService.update(id, productId, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
7. Метод для удаления витрины.
   Позволяет удалить витрину.
```java
@DeleteMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id) {
        showcaseService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```
8. Метод для удаления товара.
   Позволяет удалить товар.
```java
@DeleteMapping("showcases/{id}/products/{productId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("productId") UUID id){
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
```       
