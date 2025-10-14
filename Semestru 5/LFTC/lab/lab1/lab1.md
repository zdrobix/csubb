# Lab 1



## 1. Specificarea minilimbajului de programare

Tipuri de date:
- int
- double
- string

### Analizor lexical

```
- operatii aritmetice: +, -, *, /, %
- operatie de atribuire: =
- comparare: ==, !=, <, >, <=, >=
- misc: ,
- separatori: ;, {}, ()
- cuvinte cheie: int, double, while, if, else, Using
                + afiseaza(...) = Console.WriteLine(...)
                + citire_int() = return int.Parse(Console.ReadLine()!)
                + citire_double() = return double.Parse(Console.ReadLine()!)
```

### TS
<div style="display: flex; gap: 32px; align-items: flex-start;">
<div>

| Atom | Cod   |
|------|-------|
|ID  |0|
|CONST|1|
|; |2 |
|int |3 |
|double |4 |
|string |5 |
|( |6 |
|) |7 |
|+ |8 |
|* |9 |
|- |10 |
|/ |11 |
|% |12 |
|citire_int |13 |

</div>

<div>

| Atom | Cod   |
|------|-------|
|citire_double |14 |
|{ |15 |
|} |16 |
|, |17 |
|? |18 |
|== |19 |
|!= |20 |
|< |21 |
|> |22 |
|<= |23 |
|>= |24 |
|= |25 |
|" |26 |
|afiseaza |27 |

<br>

</div>
</div>

```
<aplicatie> ::= int main() { <lista_initializari> <lista_instructiuni> }
<lista_initializari> ::= <initializare> | <initializare> <lista_initializari>
<initializare> ::= <tip> ID;
<tip> ::= int|double|string

<lista_instructiuni> ::= <instructiune> | <instructiune> <lista_instructiuni>
<instructiune> ::= <atribuire> | <ciclare> | <iesire> | <selectie> | <afisare>

<atribuire> ::= <valoare> = CONST; | 
                <valoare> = citire_int(); | 
                <valoare> = citire_double(); |
                <valoare> = <expresie>; 


<ciclare> ::= while ( <conditie> ) { <lista_instructiuni> }

<selectie> ::= if ( <conditie> ) { <lista_instructiuni> } |
               if ( <conditie> ) { <lista_instructiuni> } else { <lista_instructiuni>}
<conditie> ::= <expresie> <operator_comparare> <expresie> | <expresie>
<expresie> ::= <valoare> | <lista_operatii_aritmetice>
<valoare> ::= CONST | ID
<operatie_aritmetica> ::= <valoare> <operator_aritmetic> <valoare>
<lista_operatii_aritmetice> ::= <operatie_aritmetica> | <operatie_aritmetica> 
                            <operator_aritmetic> <lista_operatii_aritmetice>
<operator_comparare> ::=  == | != | < | > | <= | >=
<operator_aritmetic> ::=  + | - | * | / | %

<iesire> ::= return <expresie>;

<afisare> ::= afiseaza( <expresie> );

ID ::= ( a | b | ... | z | A | B | ... | Z)( a | b | ... | z | A | B | ... | Z | 0 | ... | 9){0, 128}
CONST ::= <c_int> | <c_double> | <c_string>
<c_int> ::= ( + | - )?(0 | 1 | ... | 9)+
<c_double> ::= ( + | - )?(0 | 1 | ... | 9)+ | ( + | - )?(0 | 1 | ... | 9)+ \. ( 0 | 1 | ... | 9)*
<c_string> ::= .*


```

## 2. Textele sura a 3 mini-programe

```c#
//circumferinta si aria cercului
int main() {
    double raza;
    double pi;

    raza = 0
    raza = 3.14f

    raza = citire_double();

    double aria = pi * raza * raza;
    double circumferinta = 2 * pi * raza;

    afiseaza(aria);
    afiseaza(circumferinta);

    return raza;
}
```

<br>

```c#
//cel mai mare divizor comun a doua numere
int main() {
    int a;
    int b;

    a = citire_int();
    b = citire_int()

    while (a != b)
    {
        if (a > b)
            a = a - b;
        else
            b = b - a;
    }

    afiseaza(a);
return a;
}
```

<br>

```c#
//suma a n numere naturale
int main() {
    int n;
    int s;

    n = citire_int();
    s = 0;

    while (n > 0) {
        s += citire_int()
    }

    afiseaza(s);
    return s;
}
```

## 3. Textele sursa cu programe care contin erori

```c#
int main() {
    double a           //eroare
    
    a = 1.1;
    doubl b;           //eroare

    afiseaza(a);
}
```

```c#
int main() {
    int a, b;          //eroare, trebuia fiecare pe linie separat

    a = citire_int();
    b = citire_int();

    afiseaza(a + b);
    afiseaza(a++);    //eroare, nu este definita operatia
}
```

<br>

## Activitate in-class

##### Colegul a avut mlp derivat din C++

- do while
- loop-ul for
- nu exista structura '++'/ '--'
- nu exista structura *= / += / -=
