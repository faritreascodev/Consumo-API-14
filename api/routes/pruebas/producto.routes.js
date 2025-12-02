module.exports = app => {
    const productos = require("../../controllers/pruebas/producto.controller");

    var router = require("express").Router();
  
    
    //Crea un nuevo producto
    //router.post("/", upload, productos.create);

    router.post("/save", productos.guardarsinimagen); //guarda el producto sin la imagen

    app.use('/api/productos', router);
   
  
  };