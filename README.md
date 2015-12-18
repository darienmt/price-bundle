# Price bundle problem
(or be ready for holiday shopping)

## The problem

With the holidays almost here, there are so many offers that is hard to figure out how much this set of items will cost.
Lets suppose you have a set of products and a set of deals, and you want to find the minimum cost of the products you want to buy.

A product can be represented by an id and a price:
```
case class Product( id: Int, price: Long)
```

(yes, price is a Long because some currencies don't have decimals. We can always hope it will be a Byte but...)

An item on our shopping list could be an id and a quantity:
```
case class Item( id:Int, quantity: Int )
```

A bundle could be represented as a set of items and a price(Ex. 2 items X for one price, one item X, another item Y and an item Z for the price of X + Y):
```
case class Bundle( items:List[Item], price: Long )
```

The problem is to write a function that could map a set of bundles on a set of products and a set of items to buy to the minimum price after applying all the bundles.

## One solution

One solution could be to find all the bundle that could be applied and apply them, eliminate the items they represent
and solve the problem on the rest of the items again recursively.

The calculation is done at ./src/main/scala/Calculator.scala, and a ready-to-use class mixing a few components and traits can
found at ./src/main/scala/PriceCalculator.scala.
To try it, you can write a test based on ./src/test/PriceCalculatorSpec.scala

I hope you find it interesting. I had tons of fun.


Happy coding and Happy Holidays!!


