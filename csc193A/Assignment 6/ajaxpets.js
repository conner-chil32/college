/**
 * A webpage for fetching cute pet photos. Puppies (the best) or
 * kitties will be populated on the page after the user selects their desired
 * pet type.
 */
(function() {
  "use strict";

  window.addEventListener("load", initialize);

  /*
   * Sets up the radio buttons for the Ajax Pet Gallery for a user to select what type of
   * cute pet photos they want to see (they should of course, always pick puppies)
   */
  function initialize() {
    let radios = qsa("input[name='animal']");
    for (let i = 0; i < radios.length; i++) {
      radios[i].addEventListener("change", makeRequest);
    }
  }

  /*
   * Updates the photos displayed on the page based on the current pet selection, fetching the
   * photos from the AJAX Pets API.
   */
  function makeRequest() {
  	let animal = this.value;

    fetch(`/api/pets?animal=${animal}`)
      .then(checkStatus)
      .then(response => response.json())
      .then(displayPictures)
      .catch(console.log);
  }

  /**
   * Populates the pictures section of the page with the result images,
   * replacing any previous photos.
   * @param {string[]} imgs - array of pet image sources as strings
   */
  function displayPictures(imgs) {
    clearPictures();
    for (let i = 0; i < imgs.length; i++) {
      let imgPath = imgs[i];
      let img = document.createElement("img");
      img.src = imgPath;
      img.alt = "adorable friend";
  	  id("pictures").appendChild(img);
    }
  }

  /**
   * Clears any images in the photo gallery container.
   */
  function clearPictures() {
    let imgs = qsa("#pictures img");
    for (let i = 0; i < imgs.length; i++) {
      imgs[i].remove();
    }
  }

  /* ------------------------------ Helper Functions  ------------------------------ */

  /**
   * Helper function to return the response's result text if successful, otherwise
   * returns the rejected Promise result with an error status and corresponding text
   * @param {object} response - response to check for success/error
   * @return {object} - valid result text if response was successful, otherwise rejected
   *                    Promise result
   */
  function checkStatus(response) {
    if (response.status >= 200 && response.status < 300 || response.status == 0) {
      return response;
    } else {
      return Promise.reject(new Error(response.status + ": " + response.statusText));
    }
  }

  /**
   * Returns the element that has the ID attribute with the specified value.
   * @param {string} id - element ID
   * @return {object} DOM object associated with id.
   */
  function id(id) {
    return document.getElementById(id);
  }

  /**
   * Returns the first element that matches the given CSS selector.
   * @param {string} query - CSS query selector.
   * @returns {object[]} array of DOM objects matching the query.
   */
  function qs(query) {
    return document.querySelector(query);
  }

  /**
   * Returns the array of elements that match the given CSS selector.
   * @param {string} query - CSS query selector
   * @returns {object[]} array of DOM objects matching the query.
   */
  function qsa(query) {
    return document.querySelectorAll(query);
  }
})();