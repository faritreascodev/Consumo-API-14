const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
require('dotenv').config();

const app = express();

// Solo necesitas CORS una vez, sin restricciones para desarrollo
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

const db = require("./models");

// --- Bloque de Conexión a MongoDB ---
db.mongoose
    .connect(db.url, {
        useNewParser: true,
        useUnifiedTopology: true
    })
    .then(() => {
        console.log("Connected to the database!");
    })
    .catch(err => {
        console.log("Cannot connect to the database!", err);
        process.exit();
    });

// simple route
app.get("/", (req, res) => {
    res.json({message: "Welcome API de pruebas."});
});

require("./routes/pruebas/producto.routes")(app);
require("./routes/libros/libro.routes")(app);
require("./routes/usuarios/usuario.routes")(app);

const PORT = process.env.PORT || 8080;

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
});
