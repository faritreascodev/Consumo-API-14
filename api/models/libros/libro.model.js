module.exports = mongoose => {
    const Libro = mongoose.model(
      "libros",
      mongoose.Schema(
        {
          "codlibro":String,
          "nombre": String,
          "autor": String,
          "precio": Number,
        }
      ),
      "libros"
    );
  
    return Libro;
};



