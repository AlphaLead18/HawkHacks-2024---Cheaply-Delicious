const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");

const app = express();

// Connect to MongoDB
mongoose.connect("mongodb://localhost:27017/recipeDB", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

// Create a schema for the data you want to store
const recipeSchema = new mongoose.Schema({
  input1: String,
  input2: String,
  savedRecipes: [String],
});

// Create a model from the schema
const Recipe = mongoose.model("Recipe", recipeSchema);

// Parse JSON bodies (as sent by API clients)
app.use(bodyParser.json());

// Route to handle form submission
app.post("/submit", (req, res) => {
  const newRecipe = new Recipe({
    input1: req.body.input1,
    input2: req.body.input2,
    savedRecipes: req.body.savedRecipes,
  });

  newRecipe.save((err) => {
    if (err) {
      res.status(500).send(err);
    } else {
      res.status(200).send("Recipe saved successfully!");
    }
  });
});

app.listen(3000, () => console.log("Server started on port 3000"));
