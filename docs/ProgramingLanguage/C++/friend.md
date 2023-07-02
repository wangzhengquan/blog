## Description

 Designates a class or a function as friends of this class:
```C++
class Z
{
    int data; // private member
 
 		friend class Y; // friend class forward declaration (elaborated class specifier)
    // the non-member function operator<< will have access to Z's private members
    friend std::ostream& operator<<(std::ostream& out, const Z& o);
    friend void X::set_chan(Z & t, int c); // members of other classes can be friends too
    friend X::X(char), X::~X(); // constructors and destructors can be friends
};
 
// this operator<< needs to be defined as a non-member
std::ostream& operator<<(std::ostream& out, const Z& z)
{
    return out << z.data; // can access private member Z::data
}
```

## Template friend class

```C++
template<typename T>
struct foo {
  template<typename U>
  friend class bar;
};
```

This means that bar is a friend of foo regardless of bar's template arguments. `bar<char>`, `bar<int>`, `bar<float>`, and any other bar would be friends of `foo<char>`.

```C++
template<typename T>
struct foo {
  friend class bar<T>;
};
```
This means that bar is a friend of foo when bar's template argument matches foo's. Only `bar<char>` would be a friend of `foo<char>`.

## Template friend non-member function
A common use case for template friends is declaration of a non-member operator overload that acts on a class template, e.g. `operator<<(std::ostream&, const Foo<T>&)` for some user-defined `Foo<T>`.

Such operator can be defined in the class body, which has the effect of generating a separate non-template `operator<<` for each T and makes that non-template `operator<<` a friend of its `Foo<T>`:

```C++
#include <iostream>
 
template<typename T>
class Foo
{
public:
    Foo(const T& val) : data(val) {}
private:
    T data;
 
    // generates a non-template operator<< for this T
    friend std::ostream& operator<<(std::ostream& os, const Foo& obj)
    {
        return os << obj.data;
    }
};
 
int main()
{
    Foo<double> obj(1.23);
    std::cout << obj << '\n';
}
```

or the function template has to be declared as a template before the class body, in which case the friend declaration within `Foo<T>` can refer to the full specialization of `operator<<` for its T:
```C++
#include <iostream>
 
template<typename T>
class Foo; // forward declare to make function declaration possible
 
template<typename T> // declaration
std::ostream& operator<<(std::ostream&, const Foo<T>&);
 
template<typename T>
class Foo
{
public:
    Foo(const T& val) : data(val) {}
private:
    T data;
 
    // refers to a full specialization for this particular T 
    friend std::ostream& operator<< <> (std::ostream&, const Foo&);
 
    // note: this relies on template argument deduction in declarations
    // can also specify the template argument with operator<< <T>"
};
 
// definition
template<typename T>
std::ostream& operator<<(std::ostream& os, const Foo<T>& obj)
{
    return os << obj.data;
}
 
int main()
{
    Foo<double> obj(1.23);
    std::cout << obj << '\n';
}
```

> https://en.cppreference.com/w/cpp/language/friend