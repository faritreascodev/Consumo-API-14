const db = require("../../models");
const Libro = db.libros;


//agrega un nuevo libro
exports.create = async (req, res) => {
  
  const {codlibro, nombre, autor, precio} = req.body;

  if(!codlibro)
    return res.status(400).send({status:false, message:'Falta el parámetro codlibro.'});
  if(!nombre)
    return res.status(400).send({status:false, message:'Falta el parámetro nombre.'});
  if(!autor)
    return res.status(400).send({status:false, message:'Falta el parámetro autor.'});
  if(!precio)
    return res.status(400).send({status:false, message:'Falta el parámetro precio.'});

  const existe = await Libro.aggregate([
    {
        $match: {
            "codlibro": new RegExp('^'+ codlibro + '$', "i"),
        }
    }
  ]);

  if (existe.length > 0 )
  {
    return res.send({status:true, message:"El libro ya existe."});
  }

  //agrega el nuevo registro
  const registro = new Libro({
    codlibro, nombre, autor, precio
  });

  registro
  .save(registro)
  .then(data => {
      res.send({status:true, message:'Registro creado con éxito.'});
  })
  .catch(err => {
    res.status(500).send({ status:false, message: err.message});
  });
}

//listado de libros
exports.getAll = async (req, res) => {
    try {
        const lista = await Libro.find();
        res.status(200).send({status: true, result: lista}); // ya no manda ese array directo, solo estadi
    } catch(err) {
        res.status(500).send({status:false, mensaje: err.message});
    }   
}

// GET /api/libros/bycode/:codlibro
exports.getByCode = async (req, res) => {
  try {
    const { codlibro } = req.params;
    const libro = await Libro.findOne({ codlibro });

    if (!libro) {
      return res.status(404).send({ status:false, message:"Libro no encontrado" });
    }

    res.status(200).send({ status:true, result: libro });
  } catch (err) {
    res.status(500).send({ status:false, message: err.message });
  }
};

// DELETE /api/libros/delete/:codlibro
exports.deleteByCode = async (req, res) => {
  try {
    const { codlibro } = req.params;
    const borrado = await Libro.findOneAndDelete({ codlibro });

    if (!borrado) {
      return res.status(404).send({ status:false, message:"Libro no encontrado" });
    }

    res.status(200).send({ status:true, message:"Libro eliminado correctamente" });
  } catch (err) {
    res.status(500).send({ status:false, message: err.message });
  }
};

// PUT /api/libros/update/:codlibro
exports.updateByCode = async (req, res) => {
  try {
    const { codlibro } = req.params;
    const datos = req.body; // nombre, autor, precio, etc.

    const actualizado = await Libro.findOneAndUpdate(
      { codlibro },
      datos,
      { new: true }
    );

    if (!actualizado) {
      return res.status(404).send({ status:false, message:"Libro no encontrado" });
    }

    res.status(200).send({ status:true, result: actualizado });
  } catch (err) {
    res.status(500).send({ status:false, message: err.message });
  }
};
