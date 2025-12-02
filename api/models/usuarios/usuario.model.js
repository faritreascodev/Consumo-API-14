module.exports = mongoose => {
    const Usuario = mongoose.model(
        "usuarios",
        mongoose.Schema(
            {
                nombre: String,
                correo: String,
                password: String,
                rol: { type: String, default: "USER" }
            }
        ),
        "usuarios"
    );

    return Usuario;
};
