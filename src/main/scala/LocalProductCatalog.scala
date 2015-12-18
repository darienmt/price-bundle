/**
  * Local product catalog
  */
class LocalProductCatalog(products: List[Product]) extends ProductCatalog {
  /**
    * Find product by identifier.
    * @param id Identifier.
    * @return Product... maybe
    */
  def findProductById(id: Int): Option[Product] =
    products.find( p => p.id == id )
}
