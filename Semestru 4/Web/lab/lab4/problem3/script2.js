var numbers = [];
for (let i = 1; i <= 18; i++) {
    numbers.push(i, i);
}

for (let i = 0; i < numbers.length; i++) {
    var j = Math.floor(Math.random() * (i + 1));
    [numbers[i], numbers[j]] = [numbers[j], numbers[i]];
}

var $gameBoard = $("#board");
var revealed = [];
let lock = false;

function handleClickButton($button) {
    if (lock || $button.prop("disabled") || revealed.includes($button)) 
        return;

    $button.css({
        "background-image": `url('./img/${$button.data("value")}.jpg')`,
        "background-size": "cover",
        "color": "transparent"
    });
    revealed.push($button);

    if (revealed.length === 2) {
        lock = true;
        var [$btn1, $btn2] = revealed;


        if ($btn1.data("value") === $btn2.data("value")) {
            $btn1.prop("disabled", true);
            $btn2.prop("disabled", true);
            revealed = [];
            lock = false;
        } else {
            setTimeout(() => {
                $btn1.css({
                    "background-image": "none",
                    "color": "black"
                }).text("*");

                $btn2.css({
                    "background-image": "none",
                    "color": "black"
                }).text("*");

                revealed = [];
                lock = false;
        }, 1000);
        }   
    }
}

function createButton(value, index) {
    var $button = $("<button>")
        .data("value", value)
        .data("index", index)
        .text("*")
        .on("click", function () {
            handleClickButton($(this));
        });
        return $button;
}

for (let row = 0; row < 6; row++) {
    var $tr = $("<tr>");
    for (let col = 0; col < 6; col++) {
        var $td = $("<td>");
        var index = row * 6 + col;
        var $btn = createButton(numbers[index], index);
        $td.append($btn);
        $tr.append($td);
    }
    $gameBoard.append($tr);
}