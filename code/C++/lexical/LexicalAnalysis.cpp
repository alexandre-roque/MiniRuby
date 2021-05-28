#include <cstdio>
#include <cctype>
#include <cassert>

#include "LexicalAnalysis.h"

LexicalAnalysis::LexicalAnalysis(const char* filename) : m_line(1) {
    m_file = fopen(filename, "r");
    if (!m_file)
        throw "Unable to open file";
}

LexicalAnalysis::~LexicalAnalysis() {
    fclose(m_file);
}

int LexicalAnalysis::line() const {
    return m_line;
}

struct Lexeme LexicalAnalysis::nextToken() {
    struct Lexeme lex = { "", TKN_END_OF_FILE };

    int state = 1;
    while (state != 12 && state != 13) {
        int c = getc(m_file);

        switch (state) {
            case 1:
                // TODO: Implement me!
                break;
            case 2:
                // TODO: Implement me!
                break;
            case 3:
                // TODO: Implement me!
                break;
            case 4:
                // TODO: Implement me!
                break;
            case 5:
                // TODO: Implement me!
                break;
            case 6:
                // TODO: Implement me!
                break;
            case 7:
                // TODO: Implement me!
                break;
            case 8:
                // TODO: Implement me!
                break;
            case 9:
                // TODO: Implement me!
                break;
            case 10:
                // TODO: Implement me!
                break;
            case 11:
                // TODO: Implement me!
                break;
            default:
                assert(false);
        }
    }

    if (state == 12)
        lex.type = m_st.find(lex.token);

    return lex;
}
