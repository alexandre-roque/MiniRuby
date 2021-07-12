#ifndef TOKENTYPE_H
#define TOKENTYPE_H

enum TokenType {
    // SPECIALS
    TKN_UNEXPECTED_EOF = -2,
    TKN_INVALID_TOKEN = -1,
    TKN_END_OF_FILE = 0,

    // SYMBOLS
    TKN_SEMI_COLON,    // ;
    TKN_COMMA,         // ,
    TKN_ASSIGN,        // =
    TKN_DOT,           // .

    // OPERATORS
    TKN_EQUALS,        // ==
    TKN_NOT_EQUALS,    // !=
    TKN_LOWER,         // <
    TKN_LOWER_EQ,      // <=
    TKN_GREATER,       // >
    TKN_GREATER_EQ,    // >=
    TKN_CONTAINS,      // ===
    TKN_RANGE_WITH,    // ..
    TKN_RANGE_WITHOUT, // ...
    TKN_ADD,           // +
    TKN_SUB,           // -
    TKN_MUL,           // *
    TKN_DIV,           // /
    TKN_MOD,           // %
    TKN_EXP,           // **

    // KEYWORDS
    TKN_IF,            // if
    TKN_THEN,          // then
    TKN_ELSIF,         // elsif  
    TKN_ELSE,          // else
    TKN_END,           // end
    TKN_UNLESS,        // unless
    TKN_WHILE,         // while
    TKN_DO,            // do
    TKN_UNTIL,         // until
    TKN_FOR,           // for
    TKN_IN,            // in
    TKN_PUTS,          // puts
    TKN_PRINT,         // print
    TKN_NOT,           // not
    TKN_AND,           // and
    TKN_OR,            // or
    TKN_GETS,          // gets
    TKN_RAND,          // rand
    TKN_OPEN_BRA,      // [
    TKN_CLOSE_BRA,     // ]
    TKN_OPEN_PAR,      // (
    TKN_CLOSE_PAR,     // )
    TKN_LENGTH,        // length
    TKN_TO_INT,        // to_i
    TKN_TO_STR,        // to_s

    // OTHERS
    TKN_ID,            // identifier
    TKN_INTEGER,       // integer
    TKN_STRING         // string

};

#endif
