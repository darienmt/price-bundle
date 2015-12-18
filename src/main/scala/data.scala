
/**
  * Bundle definition.
  * @param items Items in bundle.
  * @param price Bundle price.
  */
case class Bundle( items:List[Item], price: Long )


/**
  * Item in a shopping car.
  * @param id Item identifier.
  * @param quantity Item quantity.
  */
case class Item( id:Int, quantity: Int )

/**
  * Product definition.
  * @param id
  * @param price
  */
case class Product( id: Int, price: Long )

