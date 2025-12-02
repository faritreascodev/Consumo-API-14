const db = require("../../models");
const Usuario = db.usuarios;

// POST /api/usuarios/create
exports.create = async (req, res) => {
    const { nombre, correo, password, rol } = req.body;

    if (!nombre) return res.status(400).send({ status: false, message: "Falta el parámetro nombre." });
    if (!correo) return res.status(400).send({ status: false, message: "Falta el parámetro correo." });
    if (!password) return res.status(400).send({ status: false, message: "Falta el parámetro password." });

    try {
        const existe = await Usuario.findOne({ correo: new RegExp('^' + correo + '$', 'i') });
        if (existe) {
            return res.send({ status: true, message: "El usuario ya existe." });
        }

        const registro = new Usuario({ nombre, correo, password, rol });
        await registro.save();
        res.send({ status: true, message: "Usuario creado con éxito." });
    } catch (err) {
        res.status(500).send({ status: false, message: err.message });
    }
};

// GET /api/usuarios/list
exports.getAll = async (req, res) => {
    try {
        const lista = await Usuario.find();
        res.status(200).send({ status: true, result: lista });
    } catch (err) {
        res.status(500).send({ status: false, message: err.message });
    }
};
