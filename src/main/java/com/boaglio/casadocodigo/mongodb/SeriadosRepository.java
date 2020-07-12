package com.boaglio.casadocodigo.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class SeriadosRepository {

	private final String host = "localhost";
	private final int port = 27017;
	private final String database = "test";
	private final String collection = "seriados";

	private MongoCollection<Document> seriadosCollection;

	public SeriadosRepository() {
		try {

			MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port);

			MongoDatabase db = mongoClient.getDatabase(database);

			System.out.println("== Collections do database " + database);
			for (String name : db.listCollectionNames()) {
				System.out.println(name);
			}

			seriadosCollection = db.getCollection(collection);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Seriado> findAll() {

		List<Seriado> seriados = new ArrayList<Seriado>();

		try (MongoCursor<Document> cursor = seriadosCollection.find().iterator()) {

			while (cursor.hasNext()) {

				Document resultElement = cursor.next();

				Seriado seriado = new Seriado();
				seriado.setId(resultElement.get("_id"));
				seriado.setNome((String) resultElement.get("nome"));
				seriado.setPersonagens((List<String>) resultElement.get("personagens"));

				seriados.add(seriado);

				System.out.println("Seriado lido = " + seriado);

			}
		}

		return seriados;
	}

	@SuppressWarnings("unchecked")
	public Seriado findById(String id) {

		Seriado seriado = new Seriado();
		System.out.println("busca por id = " + id);
		Document documento = seriadosCollection.find(eq("_id", new ObjectId(id))).first();

		System.out.println("Seriado lido = " + documento);
		seriado.setId((ObjectId) documento.get("_id"));
		seriado.setNome((String) documento.get("nome"));
		seriado.setPersonagens((List<String>) documento.get("personagens"));

		return seriado;
	}

	public void insert(Seriado seriado) {

		Document seriadoParaGravar = new Document();
		seriadoParaGravar.append("_id", seriado.getId());
		seriadoParaGravar.append("nome", seriado.getNome());
		seriadoParaGravar.append("personagens", seriado.getPersonagens());

		seriadosCollection.insertOne(seriadoParaGravar);

		System.out.println("novo seriado = " + seriado);

	}

	public void update(Seriado seriado) {

		seriadosCollection.updateOne(eq("_id", seriado.getId()), set("nome", seriado.getNome()));
		seriadosCollection.updateOne(eq("_id", seriado.getId()), set("personagens", seriado.getPersonagens()));

		System.out.println("seriado alterado = " + seriado);

	}

	public void remove(Object id) {

		seriadosCollection.deleteOne(eq("_id", id));

		System.out.println("seriado removido = " + id);

	}

}
