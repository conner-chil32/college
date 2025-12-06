/**
 * A server for delivering adorable pet photos to clients.
 * When the client requests pictures of either puppies (the best) or kitties,
 * this server responds with a JSON array of image paths.
 */

"use strict";

const express = require("express");
const path = require("path");

const app = express();
const PORT = 8080;

// Set up static file serving
app.use(express.static(__dirname));

// Route the root URL to ajaxpets.html
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "ajaxpets.html"));
});

// Start listening for requests
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`);
});

/**
 * Handles GET requests to the /api/pets endpoint by sending back
 * an array of image paths for the selected animal.
 */
app.get("/api/pets", (req, res) => {
  const animal = req.query.animal;

  if (animal === "puppy") {
    res.json(generateImagePaths("puppy", 49));
  } else if (animal === "kitty") {
    res.json(generateImagePaths("kitty", 13));
  } else {
    res.status(400).json({ error: "Invalid animal type. Must be 'puppy' or 'kitty'." });
  }
});

/* ------------------------------ Helper Functions ------------------------------ */

/**
 * Generates an array of image file paths for a given base name and count.
 * Adds extra files manually when needed to include uniquely named images.
 *
 * @param {string} base - base name of the image files (e.g., "puppy" or "kitty")
 * @param {number} count - number of images to include in the response
 * @returns {string[]} array of image file paths to serve
 */
function generateImagePaths(base, count) {
  let imagePaths = [];

  // Generate standard image file names (e.g., /images/puppy01.jpg)
  for (let i = 1; i <= count; i++) {
    let num = i.toString().padStart(2, "0"); // pad numbers like 01, 02, etc.
    imagePaths.push(`/images/${base}${num}.jpg`);
  }

  // Manually include a few images without padded numbers if they exist
  // These filenames are specific to the dataset
  let extras = [
    "/images/puppy1.jpg", "/images/puppy2.jpg", "/images/puppy3.jpg",
    "/images/puppy4.jpg", "/images/puppy5.jpg", "/images/puppy6.jpg",
    "/images/puppy7.jpg", "/images/puppy9.jpg"
  ];

  if (base === "puppy") {
    imagePaths = imagePaths.concat(extras);
  }

  return imagePaths;
}
