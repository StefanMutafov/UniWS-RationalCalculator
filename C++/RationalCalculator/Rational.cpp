//
// Created by Stefan on 10/03/2024.
//
#include <string>
#include "Rational.h"
#include "functions.h"


Rational::Rational(int num, int den) {
    if (den < 0) {
        den *= -1;
        num *= -1;
    }
    int gcd_value = gcd(num, den);
    if(gcd_value == 0)
        gcd_value = 1;
    this->num_ = num / gcd_value;
    this->den_ = den / gcd_value;
}

Rational::Rational() :
        Rational{0, 1} {}

Rational::Rational(int num) :
        Rational{num, 1} {}

Rational::Rational(std::string str) {
    std::string num = str.substr(0, str.find('/'));
    std::string den = str.substr(str.find('/') + 1);
    int multyplier = 1;
    if (num[0] == '-') {
        multyplier *= -1;
        num.replace(num.find('/'), 1, "");
    } else if (den[0] == '-') {
        multyplier *= -1;
        num.replace(den.find('/'), 1, "");
    }

    int i_num = stoi(num);
    int i_den = stoi(den);
    int gcd_val = gcd(i_num, i_den);

    this->num_ = multyplier * i_num / gcd_val;
    this->den_ = i_den / gcd_val;

}




int Rational::getNum() const {
    return num_;
}

int Rational::getDen() const {
    return den_;
}

std::string Rational::str() const {
    if(getDen() == 0 && getNum() == 0)
        return "NaN";
    //TODO: Handling infinity?
    return std::to_string(getNum()) + "/" + std::to_string(getDen());
}

bool Rational::is_pos() const {
    if (this->num_ < 0)
        return false;
    return true;
}

bool Rational::is_neg() const {
    if(this->num_ < 0)
        return true;
    return false;
}

bool Rational::is_inf() const {
    if(this->num_ != 0 && this->den_ == 0)
        return true;
    return false;
}

bool Rational::is_nan() const {
    if(this->num_ == 0 && this->den_ == 0)
        return true;
    return false;
}

void Rational::set(int num, int den) {
    if (den < 0) {
        den *= -1;
        num *= -1;
    }
    int gcd_value = gcd(num, den);
    if(gcd_value == 0)
        gcd_value = 1;
    this->num_ = num / gcd_value;
    this->den_ = den / gcd_value;
}

void Rational::str(std::string str) {
    std::string num = str.substr(0, str.find('/'));
    std::string den = str.substr(str.find('/') + 1);
    int multyplier = 1;
    if (num[0] == '-') {
        multyplier *= -1;
        num.replace(num.find('/'), 1, "");
    } else if (den[0] == '-') {
        multyplier *= -1;
        num.replace(den.find('/'), 1, "");
    }

    int i_num = stoi(num);
    int i_den = stoi(den);
    int gcd_val = gcd(i_num, i_den);

    this->num_ = multyplier * i_num / gcd_val;
    this->den_ = i_den / gcd_val;

}
Rational Rational::add(Rational b) const {
    int den = lcm(this->den_, b.den_);
    int num = this->num_ * (den / this->den_) + b.num_ * (den / b.den_);
    return Rational{num, den};
}

Rational Rational::sub(Rational b) const {
    int den = lcm(this->den_, b.den_);
    int num = this->num_ * (den / this->den_) - b.num_ * (den / b.den_);
    return Rational{num, den};
}

Rational Rational::mul(Rational b) const {
    int den = this->den_*b.den_;
    int num = this->num_ * b.num_;
    return Rational{num, den};
}

Rational Rational::div(Rational b) const {
    int den = this->den_*b.num_;
    int num = this->num_ * b.den_;
    return Rational{num, den};
}
