Questions:

1. Data spec in XML or as Scala file
    => XML will be translated into Scala generator description
    => Scala file optionally bound (and compiled) into class path during call of > javac Myriad [path_to_gen.scala]

2. Output of Oligos
   i) Scala data description
        - Express grammar generation rules as Scala program
        - Create model for text generation based on regex, i.e. assign transition probabilities

   ii) Or Adapter/Parser

3. Abstract/Make use from distributed environment for a) grammar/regex derivation and b) data (+text) generation

4. Look at Iterable-Like API, yield not appropriate