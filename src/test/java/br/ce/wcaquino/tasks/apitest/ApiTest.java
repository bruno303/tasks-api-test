package br.ce.wcaquino.tasks.apitest;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
//		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
		RestAssured.basePath = "tasks-backend";
		RestAssured.port = 8001;
	}
	
	@Test
	public void test() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}

	@Test
	public void adicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
