//
// Created by Stefan on 10/03/2024.
//
#include <string>
#include <cmath>
#include "Rational.h"
#include "rational_calc.h"


Rational::Rational(int num, int den) {
    if (den < 0) {
        den *= -1;
        num *= -1;
    }
    int gcd_value = gcd(num, den);
    if (gcd_value == 0)
        gcd_value = 1;
    this->num_ = num / gcd_value;
    this->den_ = den / gcd_value;
}

Rational::Rational() :
        Rational{0, 1} {}

Rational::Rational(int num) :
        Rational{num, 1} {}


Rational::Rational(const std::string &str) {
    if (str.find('/') != std::string::npos) {
        std::string num = str.substr(0, str.find('/'));
        std::string den = str.substr(str.find('/') + 1);
        int multyplier = 1;
        if (num[0] == '-') {
            multyplier *= -1;
            num.replace(0, 1, "");
        }
        if (den[0] == '-') {
            multyplier *= -1;
            den.replace(0, 1, "");
        }

        int i_num = stoi(num);
        int i_den = stoi(den);
        int gcd_val = gcd(i_num, i_den);

        this->num_ = multyplier * i_num / gcd_val;
        this->den_ = i_den / gcd_val;
    } else {
        int num = std::stoi(str);
        this->num_ = num;
        this->den_ = 1;
    }

}


int Rational::getNum() const {
    return num_;
}

int Rational::getDen() const {
    return den_;
}

std::string Rational::str() const {
    if (getDen() == 0 && getNum() == 0)
        return "NaN";
    else if (getDen() == 0 && getNum() > 0)
        return "+inf";
    else if (getDen() == 0 && getNum() < 0)
        return "-inf";

    return std::to_string(getNum()) + "/" + std::to_string(getDen());
}

bool Rational::is_pos() const {
    if (this->num_ < 0)
        return false;
    return true;
}

bool Rational::is_neg() const {
    if (this->num_ < 0)
        return true;
    return false;
}

bool Rational::is_inf() const {
    if (this->num_ != 0 && this->den_ == 0)
        return true;
    return false;
}

bool Rational::is_nan() const {
    if (this->num_ == 0 && this->den_ == 0)
        return true;
    return false;
}

void Rational::set(int num, int den) {
    if (den < 0) {
        den *= -1;
        num *= -1;
    }
    int gcd_value = gcd(num, den);
    if (gcd_value == 0)
        gcd_value = 1;
    this->num_ = num / gcd_value;
    this->den_ = den / gcd_value;
}

void Rational::str(const std::string &str) {
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
    if (this->is_nan() || b.is_nan())
        return Rational{0, 0};
    int den = lcm(this->den_, b.den_);
    int num = this->num_ * (den / this->den_) + b.num_ * (den / b.den_);
    return Rational{num, den};
}

Rational Rational::sub(Rational b) const {
    if (this->is_nan() || b.is_nan())
        return Rational{0, 0};
    int den = lcm(this->den_, b.den_);
    int num = this->num_ * (den / this->den_) - b.num_ * (den / b.den_);
    return Rational{num, den};
}

Rational Rational::mul(Rational b) const {
    if (this->is_nan() || b.is_nan())
        return Rational{0, 0};
    int den = this->den_ * b.den_;
    int num = this->num_ * b.num_;
    return Rational{num, den};
}

Rational Rational::div(Rational b) const {
    if (this->is_nan() || b.is_nan())
        return Rational{0, 0};
    int den = this->den_ * b.num_;
    int num = this->num_ * b.den_;
    return Rational{num, den};
}

Rational Rational::pow(int n) const {
    if (this->is_nan())
        return Rational{0, 0};
    int num;
    int den;
    if(n < 0){
        den = std::pow(this->num_, -1*n);
        num = std::pow(this->den_, -1*n);
    }else {
        num = std::pow(this->num_, n);
        den = std::pow(this->den_, n);
    }
    return Rational{num, den};
}

Rational Rational::sqrt() const {
    int num = std::sqrt(this->num_);
    int den = std::sqrt(this->den_);
    if (std::pow(num, 2) != this->num_ || std::pow(den, 2) != this->den_ || this->is_inf() || this->is_nan() ||
        this->is_neg()) {
        return Rational{0, 0};
    }
    return Rational{num, den};
}




