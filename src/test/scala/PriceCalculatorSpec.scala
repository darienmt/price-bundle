import org.scalatest.{FlatSpec, Matchers}

class PriceCalculatorSpec extends FlatSpec with Matchers {

  val products = List(Product(1,1), Product(2,2), Product(3,3), Product(4,4), Product(5,5))

  "without bundles, the price calculator" should "return a price sum(item.quantity*product.price)" in {

      val calc = new PriceCalculator(products,List())

      val shoppingCar = List(Item(1,1),Item(2,2), Item(3,1))

      val price = calc.calculatePrice(shoppingCar)

      price.isSuccess should be (true)
      price.get should be (8)
  }

  "duplicated items " should "be grouped and summed the result" in {

    val calc = new PriceCalculator(products,List())

    val price1 = calc.calculatePrice( List(Item(1,1),Item(2,2), Item(3,1)) )

    val price2 = calc.calculatePrice(List(Item(1,1),Item(2,1), Item(3,1),Item(2,1)) )

    price1.isSuccess should be (true)
    price2.isSuccess should be (true)

    price1.get should be (price2.get)

  }

  "a failure " should "be returned if an item is not on the catalog" in {

    val calc = new PriceCalculator(products,List())

    val shoppingCar = List(Item(-1,1),Item(2,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isFailure should be (true)
  }

  "one bundle applied" should "decrease the price by 2" in {

    val bundles = List(Bundle(List(Item(2,2)), 2))  // two 2-s for the price of one 2

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (6)
  }

  "one bundle applied twice" should "decrease the price by 4" in {

    val bundles = List(Bundle(List(Item(2,2)), 2))  // two 2-s for the price of one 2

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,4), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (8)
  }

  "two bundles applied" should "decrease the price by 4" in {

    val bundles = List(
                        Bundle(List(Item(2,2)), 2), // two 2-s for the price of one 2
                        Bundle(List(Item(1,1),Item(3,1)), 2) // 1 and 3 for two
                  )

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (4)
  }

  "two bundles applied" should "decrease the price to 4" in {

    val bundles = List(
      Bundle(List(Item(2,2)), 2), // two 2-s for the price of one 2
      Bundle(List(Item(1,1),Item(3,1)), 2) // 1 and 3 for two
    )

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (4)
  }

  "two bundles, but only one " should "be applied because of the quantity" in {

    val bundles = List(
      Bundle(List(Item(2,2)), 2), // two 2-s for the price of one 2
      Bundle(List(Item(1,1),Item(3,1)), 2) // 1 and 3 for two
    )

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,1), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (4)
  }

  "two bundles, but only one " should "be applied because of the item" in {

    val bundles = List(
      Bundle(List(Item(2,2)), 2), // two 2-s for the price of one 2
      Bundle(List(Item(1,1),Item(3,1)), 2) // 1 and 3 for two
    )

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(4,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (10)
  }

  "two bundles, the one resulting on a minimum price" should "be applied" in {

    val bundles = List(
      Bundle(List(Item(2,2)), 2), // two 2-s for the price of one 2
      Bundle(List(Item(2,1),Item(3,1)), 2) // 2 and 3 for two
    )

    val calc = new PriceCalculator(products, bundles)

    val shoppingCar = List(Item(1,1),Item(2,2), Item(3,1))

    val price = calc.calculatePrice(shoppingCar)

    price.isSuccess should be (true)
    price.get should be (5)
  }
}
