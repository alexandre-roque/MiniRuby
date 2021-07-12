#include "ArrayValue.h"

ArrayValue::ArrayValue(std::vector<Type*>* value)
  : Value(Type::ArrayType), m_value(value) {
}

ArrayValue::~ArrayValue() {
}

std::vector<Type*>* ArrayValue::value() {
    return m_value;
}
