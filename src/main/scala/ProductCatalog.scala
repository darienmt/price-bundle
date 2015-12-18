/**
  * Product catalog.
  */
trait ProductCatalog {

  /**
    * Find product by identifier.
    * @param id Identifier.
    * @return Product... maybe
    */
  def findProductById(id: Int) : Option[Product]
}
