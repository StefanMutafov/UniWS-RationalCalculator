//
// Created by Stefan on 10/03/2024.
//

#include <stdexcept>
#include <iostream>
#include <algorithm>

#include "rational_calc.h"

int gcd(int a, int b) {
    if (a == 0 && b == 0)
        return 1;
    if (a < 0)
        a *= -1;
    if (b < 0)
        b *= -1;
    if (a == b)
        return a;
    if (a == 0)
        return b;
    if (b == 0)
        return a;
    if (a > b)
        return gcd(a - b, b);
    return gcd(a, b - a);
}

int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}

TokenArray solveLine(TokenArray array) {
    Token target{Token_Operator, "(", 1};
    while (array.consists(target)) {
        array = solveInnerBrackets(array);
    }
    array = solve_functions(array);
    array = solve_precedence(array);
    return array;
}

TokenArray solve_functions(TokenArray array) {
    array.reset();
    while (array.peekNextToken().type != Token_eof) {
        std::string value = array.getNextToken().value;
        Rational solution;
        if (value == "sqrt") {
            Rational num1{array.peekNextToken().value};
            solution = num1.sqrt();
            Token replacement{Token_Number, solution.str(), 0};
            array.replaceRange(array.getCurrTokenIndex() - 1, array.getCurrTokenIndex(), replacement);
        } else if (value == "^") {
            Rational num1{array.getToken(array.getCurrTokenIndex() - 2).value};
            Rational num2{array.peekNextToken().value};
            solution = num1.pow(num2.getNum());
            Token replacement{Token_Number, solution.str(), 0};
            array.replaceRange(array.getCurrTokenIndex() - 2, array.getCurrTokenIndex(), replacement);
        }

    }
    array.reset();
    return array;
}


TokenArray solve_precedence(TokenArray array) {
    while (array.getSize() > 2) {
        Rational solution;
        int highestPriorityIndex = getHighestPriorityIndex(array);
        Rational num1{array.getToken(highestPriorityIndex - 1).value};
        Rational num2{array.getToken(highestPriorityIndex + 1).value};
        if (array.getToken(highestPriorityIndex).value == "+") {
            solution = num1.add(num2);
        } else if (array.getToken(highestPriorityIndex).value == "-") {
            solution = num1.sub(num2);
        } else if (array.getToken(highestPriorityIndex).value == "*") {
            solution = num1.mul(num2);
        } else {
            solution = num1.div(num2);
        }
        Token replacement{Token_Number, solution.str(), 0};
        array.replaceRange(highestPriorityIndex - 1, highestPriorityIndex + 1, replacement);
        array.reset();

    }
    array.reset();
    return array;
}

int getHighestPriorityIndex(TokenArray array) {
    array.reset();
    int highestIndex = 0;
    while (array.peekNextToken().type != Token_eof) {
        Token cToken = array.getNextToken();
        if (cToken.type == Token_Operator && cToken.precedence > array.getToken(highestIndex).precedence) {
            highestIndex = array.getCurrTokenIndex() - 1;
        }
    }
    return highestIndex;
}

TokenArray solveInnerBrackets(TokenArray array) {
    int lastOpenIndex = 0;
    int firstCloseIndex = 0;
    std::string str{};
    while (array.peekNextToken().type != Token_eof) {
        if (array.getNextToken().value == "(") {
            lastOpenIndex = array.getCurrTokenIndex() - 1;
        }
    }
    array.reset();
    while (array.peekNextToken().type != Token_eof) {
        if (array.getNextToken().value == ")" && array.getCurrTokenIndex() > lastOpenIndex) {
            firstCloseIndex = array.getCurrTokenIndex() - 1;
            break;
        }
    }
    array.reset();
    while (array.getCurrTokenIndex() < firstCloseIndex) {
        Token token = array.getNextToken();
        if (array.getCurrTokenIndex() > lastOpenIndex + 1) {
            str += token.value;
        }
    }
    TokenArray out{str};
    out = solve_functions(out);
    out = solve_precedence(out);
    array.replaceRange(lastOpenIndex, firstCloseIndex, out.peekNextToken());
    array.reset();
    return array;
}

bool TokenArray::consists(const Token &t) {
    while (peekNextToken().type != Token_eof) {
        if (getNextToken().value == t.value) {
            reset();
            return true;
        }
    }
    reset();
    return false;
}

TokenArray::TokenArray(std::string &string) {
    const char *currChar = string.c_str();
    while (*currChar) {
        TokenType type = Token_eof;
        while (isspace(*currChar))
            currChar++;
        if(*currChar) {
            const char *start = currChar;
            if (!isspace(*currChar)) {
                if (isdigit(*currChar) || (*currChar == '-' && (currChar[-1] == string[-1] || currChar[-1] == '/' ||
                                                                this->tokens.back().type == Token_Operator))) {
                    type = Token_Number;
                    bool foundFractionLine = false;
                    while (isdigit(*currChar) || *currChar == '/' ||
                           (*currChar == '-' &&
                            (currChar[-1] == string[-1] || ispunct(currChar[-1]) || isspace(currChar[-1])))) {
                        if (*currChar == '/') {
                            if (foundFractionLine)
                                break;
                            foundFractionLine = true;
                        }
                        currChar++;
                    }

                } else if (ispunct(*currChar)) {
                    type = Token_Operator;
                    currChar++;
                } else if (*currChar == 's') {
                    //If the operator is sqrt()
                    type = Token_Operator;
                    currChar += 4;
                } else {
                    throw std::invalid_argument("Found unexpected character in string");
                }


            }
            Token result;
            result.type = type;
            result.value = std::string(start, currChar);

            if (result.type == Token_Number) {
                Rational test{result.value};
                if (test.is_nan() || test.is_inf()) {
                    tokens.clear();
                    result.value = test.str();
                    result.precedence = -1;
                    this->tokens.push_back(result);
                    break;
                }
            }
            if (type == Token_Number || type == Token_eof) {
                result.precedence = 0;
            } else if (result.value == "+" || result.value == "-" || result.value == "(" || result.value == ")" ||
                       result.value == "(") {
                result.precedence = 1;
            } else if (result.value == "*" || result.value == "/") {
                result.precedence = 2;
            } else {
                //These are sqrt() and pow()
                result.precedence = 0;
            }
            this->tokens.push_back(result);
        }
    }
    Token eof;
    eof.type = Token_eof;
    eof.value = "";
    this->tokens.push_back(eof);
}


void TokenArray::setToken(int index, TokenType type, std::string value, int precedence) {
    this->tokens[index].value = value;
    this->tokens[index].type = type;
    this->tokens[index].precedence = precedence;
}

void TokenArray::removeTokens(int index1, int index2) {
    auto iterator1 = this->tokens.begin() + index1;
    auto iterator2 = this->tokens.begin() + index2;
    this->tokens.erase(iterator1, iterator2);
}

int TokenArray::getCurrTokenIndex() const {
    return this->currentToken;
}

int TokenArray::getSize() {
    return this->tokens.size();
}

std::string TokenArray::toString() {
    std::string str{};

    while (peekNextToken().type != Token_eof) {
        str += getNextToken().value;

    }
    reset();
    return str;
}

void TokenArray::replaceRange(int startIdx, int endIdx, const Token &replacement) {
    this->removeTokens(startIdx, endIdx);
    this->setToken(startIdx, replacement.type, replacement.value, replacement.precedence);
}

Token TokenArray::getNextToken() {
    return this->tokens[this->currentToken++];
}

Token TokenArray::peekNextToken() {
    return this->tokens[this->currentToken];
}

Token TokenArray::getToken(int index) {
    return this->tokens[index];
}

void TokenArray::reset() {
    this->currentToken = 0;
}