let numbers = [];
for (let i = 1; i <= 18; i++) {
  numbers.push(i, i);
}

for (let i = 0; i < numbers.length; i++) {
    const j = Math.floor(Math.random() * (i + 1));
    [numbers[i], numbers[j]] = [numbers[j], numbers[i]];
}

let board = document.getElementById("board");
let revealed = [];
let lock = false;

function handleClickButton (button) {
    if (lock || button.disabled || revealed.includes(button)) 
        return;

    button.style.backgroundImage = `url('./img/${value}.jpg')`;
    revealed.push(button);

    if (revealed.length === 2) {
        lock = true;
        let [btn1, btn2] = revealed;

        if (btn1.dataset.value === btn2.dataset.value) {
            btn1.disabled = true;
            btn2.disabled = true;
            revealed.length = 0;
            lock = false;
        } else {
            setTimeout(() => {
            btn1.style.backgroundImage = "";
            btn2.style.backgroundImage = "";
            revealed.length = 0;
            lock = false;
            }, 1000);
        }   
    }
}

function createButton(value, index) {
    let button = document.createElement("button");
    button.dataset.value = value;
    button.dataset.index = index;
    button.addEventListener("click", () => {
        if (lock || button.disabled || revealed.includes(button)) 
            return;
    
        button.style.backgroundImage = `url('./img/${value}.jpg')`;
        revealed.push(button);
    
        if (revealed.length === 2) {
            lock = true;
            const [btn1, btn2] = revealed;
    
            if (btn1.dataset.value === btn2.dataset.value) {
                btn1.disabled = true;
                btn2.disabled = true;
                revealed.length = 0;
                lock = false;
            } else {
                setTimeout(() => {
                btn1.style.backgroundImage = "";
                btn2.style.backgroundImage = "";
                revealed.length = 0;
                lock = false;
                }, 1000);
            }   
        }
    });
    return button;
}

for (let i = 0; i < 6; i++) {
    let tr = document.createElement("tr");
    for (let j = 0; j < 6; j++) {
        let td = document.createElement("td");
        let index = i * 6 + j;
        let btn = createButton(numbers[index], index);
        td.appendChild(btn);
        tr.appendChild(td);
    }
    board.appendChild(tr);
}