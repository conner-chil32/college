'use strict';

const express = require('express');
const app = express();

// define endpoint for exercise 1 here
app.get("/math/circle/:r", function(req, res) {
    let radius = req.params['r']
    res.json({"area": Math.PI * radius * radius, "circumference": Math.PI * (2 * radius)})
});
// define endpoint for exercise 2 here

app.get("/hello/name", function(req, res) {
    let firstName = req.query['first']
    let lastName = req.query['last']

    if(firstName == null && lastName == null) {
        res.type('text').status(400).send('Missing Required GET parameters: first, last');
    } else if (lastName == null) {
        res.type('text').status(400).send('Missing Required GET parameters: last');
    } else if (firstName == null) {
        res.type('text').status(400).send('Missing Required GET parameters: first');
    } else {
        res.type('text');
        res.send("Hello " + firstName + " " + lastName);
    }
});


const PORT = process.env.PORT || 8000;
app.listen(PORT);