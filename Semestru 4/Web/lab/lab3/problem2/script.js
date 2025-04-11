function validate_fields() {
    var name = document.getElementById("name");
    var email = document.getElementById("email");
    var birthdate = document.getElementById("birthdate");
    var age = document.getElementById("age");

    var elements = [name, email, birthdate, age];
    var valid = true;

    var errors = "";

    for (let i = 0; i < elements.length; i++) {
        reset_border(elements[i]);
        if (elements[i].value == "") {
            border_red(elements[i])
            valid = false;
        }
    }
    if (!valid) {
        errors += "Please fill in all fields. ";
    }
    
    if (!valid_name(name.value)) {
        border_red(name);
        errors += "Invalid name. "
    }

    if (!valid_email(email.value)) {
        border_red(email);
        errors += "Invalid email. ";
    }

    if (!valid_age(age.value)) {
        border_red(age);
        errors += "Invalid age. ";
    }

    if (!valid_birthdate(birthdate.value, age.value)) {
        border_red(birthdate);
        errors += "Invalid birthdate. ";
    }

    if (errors != "") {
        alert(errors);
    }
}

function valid_name(name) {
    const pattern = /^[a-z ,.'-]+$/i;
    return pattern.test(name);
}

function valid_email(email) {
    const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return pattern.test(email);
}

function valid_age(age) {
    try {
        let parsed = parseInt(age);
        return 0 <= age && age <= 100;
    } catch {
        return false;
    }
}

function valid_birthdate(birthdate, age) {
    if (age == "") 
        return false;
    const date = new Date(birthdate);
    const today = Date.now();
    const age_calculated = today.getFullYear() - date.getFullYear();
    return parseInt(age.value) == age_calculated && date <= today;
}

function border_red(element) {
    element.style.border = "2px solid red";
}

function reset_border(element) {
    element.style.border = "2px solid black";
}