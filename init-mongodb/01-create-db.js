// creating database and collections:
db = new Mongo().getDB("testdb1");
db.createCollection("transactions", {capped: false});
db.createCollection("fees", {capped: false});