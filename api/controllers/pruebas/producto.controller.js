const Producto = require("../../models/pruebas/producto.model");
//const Producto =db.productos;



//listado de dependencias con su coordinador

exports.getAll = (req, res) => {
    res.send({ mensaje: "Hola productos"});
}


exports.create = async (req, res) => {
    //console.log(req.body);
    try {
        const {nombre, tamanio, precioUnitario, descripcion} = req.body;

        const producto = Producto({
            nombre, tamanio, precioUnitario, descripcion
        });

        if(req.file) {
            const {filename} = req.file;
            //console.log("nombre de archivo: ", req.file.originalname);
            producto.setImagenUrl(filename);
        }


        const result= await producto.save();
        res.status(200).send({status:true, result});

    }catch(err) {
        res.status(500).send({status:false, mensaje: err.message});
    } 

}



exports.guardarsinimagen = async (req, res) => {
    //console.log(req.body);
    try {
        const {nombre, tamanio, precioUnitario, descripcion} = req.body;

        const producto = Producto({
            nombre, tamanio, precioUnitario, descripcion
        });

        const result= await producto.save();
        res.status(200).send({result});

    }catch(err) {
        res.status(500).send({mensaje: err.message});
    } 

}


