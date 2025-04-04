
## std::weak_ptr
查看std::share_ptr是否exist

## std::optional
The class template std::optional manages an optional contained value, i.e. a value that may or may not be present.

## deduce type
```C++
std::visit([](auto&& arg)
{
    using T = std::decay_t<decltype(arg)>;
    if constexpr (std::is_same_v<T, int>)
        std::cout << "int with value " << arg << '\n';
    else if constexpr (std::is_same_v<T, long>)
        std::cout << "long with value " << arg << '\n';
    else if constexpr (std::is_same_v<T, double>)
        std::cout << "double with value " << arg << '\n';
    else if constexpr (std::is_same_v<T, std::string>)
        std::cout << "std::string with value " << std::quoted(arg) << '\n';
    else
        static_assert(false, "non-exhaustive visitor!");
}, w);
```