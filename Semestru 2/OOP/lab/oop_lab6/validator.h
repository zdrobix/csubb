#ifndef VALIDATOR_H
#define VALIDATOR_H

#include <string>
#include <stdexcept>

using namespace std;

class Validator {
public:

    static void validate_product(const string& name, const string& producer, const string& type, const float price) {

        string errors;

        if (name.empty()) errors.append("Invalid name!\n");

        if (producer.empty()) errors.append("Invalid producer!\n");

        if (type.empty()) errors.append("Invalid type!\n");

        if (price <= 0) errors.append("Invalid price!\n");

        if (!errors.empty()) throw runtime_error(errors);
    }
};

#endif //VALIDATOR_H
