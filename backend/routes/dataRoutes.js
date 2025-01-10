const express = require("express");
const router = express.Router();
const authMiddleware = require("../middlewares/authMiddleware");
const Measurement = require("../models/Measurement");

// POST /data -> Enregistrer une mesure
router.post("/", async (req, res) => {
  try {
    const { temperature, humidity } = req.body;
    // Option : vérifier un token spécifique provenant de l'ESP32

    const newMeasurement = new Measurement({ temperature, humidity });
    await newMeasurement.save();

    return res.status(201).json({ message: "Mesure enregistrée" });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Erreur serveur" });
  }
});

// GET /data -> Récupérer les mesures (protégé par JWT)
router.get("/", authMiddleware, async (req, res) => {
  try {
    const measurements = await Measurement.find().sort({ timestamp: -1 });
    return res.status(200).json(measurements);
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Erreur serveur" });
  }
});

module.exports = router;
