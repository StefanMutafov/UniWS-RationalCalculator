//
// Created by Stefan on 10/03/2024.
//

#ifndef RATIONALCALCULATOR_RATIONAL_H
#define RATIONALCALCULATOR_RATIONAL_H


#include <string>

class Rational {
public:
    /* Constructors */
    Rational();

    Rational(int num, int den);

    explicit Rational(int num);

    explicit Rational(const std::string &str);

    /* Accessors */
    [[nodiscard]] int getNum() const;

    [[nodiscard]] int getDen() const;

    [[nodiscard]] std::string str() const;

    [[nodiscard]] bool is_pos() const;

    [[nodiscard]] bool is_neg() const;

    [[nodiscard]] bool is_inf() const;

    [[nodiscard]] bool is_nan() const;

    /* Modifiers */
    void set(int num, int den);

    void str(const std::string &s);

    /* Operators */
    [[nodiscard]] Rational add(Rational b) const;

    [[nodiscard]] Rational sub(Rational b) const;

    [[nodiscard]] Rational mul(Rational b) const;

    [[nodiscard]] Rational div(Rational b) const;

    [[nodiscard]] Rational pow(int n) const;

    [[nodiscard]] Rational sqrt() const;

private:
    int num_;
    int den_;
};


#endif //RATIONALCALCULATOR_RATIONAL_H
