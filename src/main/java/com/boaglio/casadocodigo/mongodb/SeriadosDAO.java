package com.boaglio.casadocodigo.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.boaglio.casadocodigo.mongodb.dto.Seriado;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class SeriadosDAO {

	private final String host = "localhost";
	private final int port = 27017;
	private final String database = "test";
	private final String collection = "seriados";

	private DBCollection seriadosCollection;

	public Mongo mongo() throws Exception {

		MongoClient mongoClient = new MongoClient(new ServerAddress(host,port));
		return mongoClient;
	}

	public SeriadosDAO() {
		try {
			Mongo mongo = new Mongo(host,port);
			DB db = mongo.getDB(database);
			seriadosCollection = db.getCollection(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Seriado> findAll() {

		List<Seriado> seriados = new ArrayList<Seriado>();
		DBCursor cursor = seriadosCollection.find();

		while (cursor.hasNext()) {
			DBObject resultElement = cursor.next();

			Map<?,?> resultElementMap = resultElement.toMap();
			Seriado seriado = new Seriado();
			seriado.setId((ObjectId) resultElementMap.get("_id"));
			seriado.setNome((String) resultElementMap.get("nome"));
			seriado.setPersonagens((List<String>) resultElementMap.get("personagens"));

			seriados.add(seriado);

			System.out.println("Seriado lido = " + seriado);
		}

		return seriados;
	}

	@SuppressWarnings("unchecked")
	public Seriado findById(String id) {

		Seriado seriado = new Seriado();
		System.out.println("busca por id = " + id);
		DBObject dbObject = seriadosCollection.findOne(new BasicDBObject("_id",new ObjectId(id)));

		System.out.println("Seriado lido = " + dbObject);
		Map<?,?> resultElementMap = dbObject.toMap();
		seriado.setId((ObjectId) resultElementMap.get("_id"));
		seriado.setNome((String) resultElementMap.get("nome"));
		seriado.setPersonagens((List<String>) resultElementMap.get("personagens"));

		return seriado;
	}

	public void insert(Seriado seriado) {

		BasicDBObject seriadoParaGravar = new BasicDBObject();
		seriadoParaGravar.append("nome",seriado.getNome());
		seriadoParaGravar.append("personagens",seriado.getPersonagens());

		seriadosCollection.insert(seriadoParaGravar);

		System.out.println("novo seriado = " + seriado);

	}

	public void update(Seriado seriado) {

		BasicDBObject seriadoOld = new BasicDBObject("_id",seriado.getId());

		BasicDBObject seriadoParaGravar = new BasicDBObject();
		seriadoParaGravar.append("_id",seriado.getId());
		seriadoParaGravar.append("nome",seriado.getNome());
		seriadoParaGravar.append("personagens",seriado.getPersonagens());

		seriadosCollection.update(seriadoOld,seriadoParaGravar,true,false);

		System.out.println("seriado alterado = " + seriado);

	}

	public void remove(String id) {

		seriadosCollection.remove(new BasicDBObject("_id",new BasicDBObject("_id",new ObjectId(id))));
		System.out.println("seriado removido = " + id);

	}

}
