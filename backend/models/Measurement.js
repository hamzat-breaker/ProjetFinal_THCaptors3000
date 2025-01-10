const mongoose = require("mongoose");

const measurementSchema = new mongoose.Schema({
  timestamp: { 
    type: Date, 
    default: Date.now 
  },
  temperature: {
    type: Number, 
    required: true
  },
  humidity: {
    type: Number, 
    required: true
  }
});

module.exports = mongoose.model("Measurement", measurementSchema);
