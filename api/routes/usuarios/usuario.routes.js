module.exports = app => {
  const usuario = require("../../controllers/usuarios/usuario.controller");
  const router = require("express").Router();

  router.post("/create", usuario.create);
  router.get("/list",  usuario.getAll);

  app.use("/api/usuarios", router);
};
