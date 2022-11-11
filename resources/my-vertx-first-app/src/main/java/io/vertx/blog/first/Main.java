package io.vertx.blog.first;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		 Vertx vertx = Vertx.vertx();

		    

		    DeploymentOptions options = new DeploymentOptions()
		        .setConfig(new JsonObject().put("http.port", 8081)
		        );

		    // We pass the options as the second parameter of the deployVerticle method.
		    vertx.deployVerticle(MyFirstVerticle.class.getName(), options);

	}

}
