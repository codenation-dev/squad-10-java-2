package br.com.codenation.log;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-it.properties")
@SpringBootTest
public class LogApplicationTests {

	private static final int PORT = 5433;
	private static final String DATABASE = "log";
	private static EmbeddedPostgres embeddedPostgres;

	@BeforeClass
	public static void initialise() throws Exception {
		Locale.setDefault(new Locale("pt", "BR"));

		if (embeddedPostgres != null) {
			return;
		}
		embeddedPostgres = EmbeddedPostgres.builder()
				.setPort(PORT).start();

		try (Connection conn = embeddedPostgres.getPostgresDatabase().getConnection()) {
			try (Statement statement = conn.createStatement()) {
				statement.execute("create database " + DATABASE);
			}
		}
	}

	@Test
	public void contextLoads() {
	}

}