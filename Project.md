# Useful Tutorial Links

Hibernate Tutorial: 
- [ ] [Full Hibernate Tutorial](https://www.youtube.com/watch?v=0KCKBv6rbkc&ab_channel=Simplilearn)
- [ ] [Hibernate & JPA Tutorial - Crash Course](https://www.youtube.com/watch?v=xHminZ9Dxm4&ab_channel=MarcoCodes)
- [ ] [Intellij Hibernate Integration](https://www.youtube.com/watch?v=QJddHc41xrM&ab_channel=IntelliJIDEAbyJetBrains)
- [ ] [Hibernate Short Tutorial](https://www.javatpoint.com/hibernate-tutorial)

Intellij Swing Gui Designer Tutorial:
- [ ] [Registration Form using Intellij Swing Gui Designer](https://www.youtube.com/watch?v=nIQatIpL_GE&ab_channel=BoostMyTool)
- [ ] [IntelliJ - How to use GUI Designer](https://www.youtube.com/watch?v=aIdIXsi1qTU&ab_channel=BoostMyTool)

# Project Details

- Swagger link : http://localhost:8080/swagger-ui/
- In `POO_Project_30223.postman_collection.json` are all request tested in Postman at class that can be imported
- Stored procedure in mysql used at class:
```
create
    definer = root@localhost procedure logic_cinema()
BEGIN
    UPDATE adopter set salary=salary * 2 where length(name) < 5;
    INSERT INTO animal (name, price)
    SELECT animal.name, 2 * animal.price
    from animal
             inner join adoption on adoption.movie_id = animal.id
             inner join user_reservation on user_reservation.User_id = adoption.user_id
             inner join adopter on adopter.id = user_reservation.User_id
    where length(adopter.name) < 5;

    UPDATE my_address inner join adopter on adopter.address_id = my_address.id
    set my_address.number=my_address.number + 1
    where length(adopter.name) < 5
      AND (adopter.id % 2) = 0;
END;
```

# Project Specifications

1. Create a Java application using Hibernate as ORM. Application ca be anything that will work with database. Eg. Cinema Reservation Apps, E-commerce Apps, Social Media Apps, etc.
2. Nr. of tables between 5-10
3. CRUD for all entities
4. Score:
   1. GUI application - 0.75p
   2. REST application with testing from Swagger/Postman - 1p
   3. REST App + CSV operation for 2 entities (import + export) - 1.25p
   4. REST App + CSV + Performance (Java vs SQL) for 2 flows of logic - 1.5p