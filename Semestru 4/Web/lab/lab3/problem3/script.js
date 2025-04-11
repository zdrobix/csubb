const numbers = [];
for (let i = 1; i <= 18; i++) {
  numbers.push(i, i);
}

for (let i = 0; i < numbers.length; i++) {
    const j = Math.floor(Math.random() * (i + 1));
    [numbers[i], numbers[j]] = [numbers[j], numbers[i]];
}

const gameBoard = document.getElementById("board");
const revealed = [];
let lock = false;

function handleClickButton (button) {
    if (lock || button.disabled || revealed.includes(button)) 
        return;

    button.textContent = value;
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
            btn1.textContent = "";
            btn2.textContent = "";
            revealed.length = 0;
            lock = false;
            }, 1000);
        }   
    }
}

function createButton(value, index) {
    const button = document.createElement("button");
    button.dataset.value = value;
    button.dataset.index = index;
    button.textContent = "*"; 
    button.addEventListener("click", () => {
        if (lock || button.disabled || revealed.includes(button)) 
            return;
    
        button.textContent = value;
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
                btn1.textContent = "";
                btn2.textContent = "";
                revealed.length = 0;
                lock = false;
                }, 1000);
            }   
        }
    });
    return button;
}

for (let row = 0; row < 6; row++) {
    const tr = document.createElement("tr");
    for (let col = 0; col < 6; col++) {
        const td = document.createElement("td");
        const index = row * 6 + col;
        const btn = createButton(numbers[index], index);
        td.appendChild(btn);
        tr.appendChild(td);
    }
    gameBoard.appendChild(tr);
}