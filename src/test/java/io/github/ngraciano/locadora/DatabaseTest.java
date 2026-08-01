package io.github.ngraciano.locadora;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
     static Connection connection;

     @BeforeAll
     static  void setUpDataBase() throws Exception{
        connection= DriverManager.getConnection("jdbc:h2:mem:testdb","sa","");
        connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR)");
     }

     @BeforeEach
    void insertUserTest() throws Exception{
         connection.createStatement().execute("insert into users(id,name) values(1,'Jose')");
     }

     @Test
     @DisplayName("Testar se usuario foi cadastrado!")
     void testUserExists() throws Exception {
      var result= connection.createStatement().executeQuery("SELECT * FROM users WHERE id=1");
         Assertions.assertTrue(result.next());
     }
     @AfterAll
     static void closeDateBase() throws Exception{
         connection.close();
     }
}
