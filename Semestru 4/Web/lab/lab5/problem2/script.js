function validate_fields() {
    var name = $("#name");
    var email = $("#email");
    var birthdate = $("#birthdate");
    var age = $("#age");

    var elements = [name, email, birthdate, age];
    var valid = true;

    var errors = "";

    for (let i = 0; i < elements.length; i++) {
        reset_border(elements[i]);
        if (elements[i].val() == "") {
            border_red(elements[i])
            valid = false;
        }
    }
    if (!valid) {
        errors += "Please fill in all fields. ";
    }
    
    if (!valid_name(name.val())) {
        border_red(name);
        errors += "Invalid name. "
    }

    if (!valid_email(email.val())) {
        border_red(email);
        errors += "Invalid email. ";
    }

    if (!valid_age(age.val())) {
        border_red(age);
        errors += "Invalid age. ";
    }

    if (!valid_birthdate(birthdate.val(), age.val())) {
        border_red(birthdate);
        errors += "Invalid birthdate. ";
    }

    if (errors !== "") {
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
    const today = new Date();
    const age_calculated = today.getFullYear() - date.getFullYear();
    return parseInt(age) == age_calculated && date <= today;
}

function border_red(element) {
    element.css("border", "2px solid red");
}

function reset_border(element) {
    element.css("border", "2px solid black");
}