module.exports = app => {
  const libro = require("../../controllers/libros/libro.controller");

  var router = require("express").Router();

  router.post("/create", libro.create);
  router.get("/list", libro.getAll);
  router.get("/bycode/:codlibro", libro.getByCode);
  router.delete("/delete/:codlibro", libro.deleteByCode);
  router.put("/update/:codlibro", libro.updateByCode);
  router.get("/getDetalle", libro.getxCodigo);

  app.use('/api/libros', router);
};
