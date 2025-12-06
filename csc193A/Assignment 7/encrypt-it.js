
/*
 * Starter file 
 */
(function () {
  "use strict";

  /**
   * The starting point in our program, setting up a listener
   * for the "load" event on the window, signalling the HTML DOM has been constructed
   * on the page. When this event occurs, the attached function (init) will be called.
   */
  window.addEventListener("load", init);

  /**
   * TODO: Write a function comment using JSDoc.
   */
  function init() {
    console.log("WINDOW LOADED")
  }

  window.addEventListener("load", function () {
    const textarea = document.getElementById("input-text");

    function shiftCipher(text) {
      text = text.toLowerCase();
      let result = "";
      for (let i = 0; i < text.length; i++) {
        if (text[i] < 'a' || text[i] > 'z') {
          result += text[i];
        } else if (text[i] == 'z') {
          result += 'a';
        } else { // letter is between 'a' and 'y'
          let letter = text.charCodeAt(i);
          let resultLetter = String.fromCharCode(letter + 1);
          result += resultLetter;
        }
      }
      return result;
    }

    function handleClick() {
      textarea.value = shiftCipher(textarea.value)
      console.log("encrypted")
    }
    const encryptBtn = document.getElementById("encrypt-it");

    encryptBtn.addEventListener("click", handleClick);
  })

  window.addEventListener("load", function () {
    const textarea = document.getElementById("input-text");

    function handleReset() {
      console.log("reseted")
      textarea.value = '';
    }
    const resetBtn = document.getElementById("reset");

    resetBtn.addEventListener("click", handleReset);
  })




  // Add any other functions in this area (you should not implement your
  // entire program in the init function, for similar reasons that
  // you shouldn't write an entire Java program in the main method).

})();
