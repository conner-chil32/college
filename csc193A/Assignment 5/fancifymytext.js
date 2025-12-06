function test() {
    alert("Hello, world!");
}

function bigger() {
    const area = document.getElementById("userText");
    area.style.fontSize = "24pt";
}

function changeStyle(input) {
    const area = document.getElementById("userText");
    const fancy = document.getElementById("fancy");
    const boring = document.getElementById("boring");

    alert("changed to " + input);

    if (fancy.checked) {
        area.style.fontWeight = "bold";
        area.style.color = "blue";
        area.style.textDecoration = "underline";
    }

    if (boring.checked) {
        area.style.fontWeight = "normal";
        area.style.color = "black";
        area.style.textDecoration = "none";
        area.style.fontSize = "12pt";
    }
}

function moo() {
    const area = document.getElementById("userText");
    let text = area.value;

    text = text.toUpperCase();

    const originalTrimmed = area.value.trim();
    const endsWithPeriod = originalTrimmed.endsWith(".");

    const sentences = text.split(".");

    for (let i = 0; i < sentences.length; i++) {
        let sentence = sentences[i].trim();
        if (sentence.length === 0) {
            continue; 
        }

        const words = sentence.split(" ");
        const lastIndex = words.length - 1;
        if (lastIndex >= 0) {
            words[lastIndex] = words[lastIndex] + "-Moo";
        }
        sentences[i] = words.join(" ");
    }
    text = sentences.join(". ");
    if (endsWithPeriod && !text.endsWith(".")) {
        text += ".";
    }
    area.value = text;
}