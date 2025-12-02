const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const ProductSchema = Schema({
    nombre: String,
    tamanio: Number,
    precioUnitario: Number,
    imagenUrl: String,
    descripcion: String,

}, {
    timestamps: true
});


ProductSchema.methods.setImagenUrl = function setImagenUrl (fileName) {
    const host='http://localhost';
    const port=8080;

    this.imagenUrl =`${host}:${port}/public/${fileName}`;
}


module.exports = mongoose.model('productos', ProductSchema);