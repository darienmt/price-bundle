import scala.util.{Failure, Success, Try}

trait Calculator {

  this : BundleProviderComponent with ProductCatalogComponent =>

  /**
    * Calculate minimum price for the provided items.
    * @param shoppingCar Items to calculate the price.
    * @return Minimum price.
    */
  def calculatePrice(shoppingCar: List[Item] ) : Try[Long] = {

    /**
      * Find the minimum price for the items provided.
      * @param items Items to calculate price on.
      * @return Minimum price.
      */
    def findMinPrice(items: List[Item]) : Long = {
      if ( items.isEmpty ) {
        0
      } else {
        val matchedBundles = bundleProvider.findApplicableBundles( items )
        if ( matchedBundles.isEmpty ) {
          items.map( i => {
            val product = productCatalog.findProductById(i.id).get
            i.quantity*product.price
          }).sum
        } else {
          matchedBundles.map( b => applyBundle( b, items ) ).min
        }
      }
    }

    /**
      * Apply the bundle to the items.
      * @param bundle Bundle to apply.
      * @param items Items to apply the bundle to.
      * @return Price
      */
    def applyBundle(bundle: Bundle, items: List[Item] ): Long = {
      val bundleIds = bundle.items.map(_.id)
      val (in, out) = items.partition( i => bundleIds.contains(i.id) )
      val nextIn = in.map ( i => {
        val bi = bundle.items.find( ib => ib.id == i.id ).get
        Item(i.id, i.quantity - bi.quantity )
      })
        .filter( _.quantity != 0 )
      val nextItems = nextIn ++ out
      bundle.price + findMinPrice( nextItems )
    }

    // group in case there are duplicated items.
    val items = shoppingCar.groupBy( _.id ).toList.map{
      case ( id, list ) => Item(id, list.map(_.quantity).sum )
    }

    // Check all items are in the catalog.
    val notFoundItems = items.exists( i => productCatalog.findProductById(i.id) match {
      case None => true
      case Some(p) => false
    })
    if ( notFoundItems ) {
      Failure(new IllegalArgumentException("Some items are not present on the current catalog."))
    } else {
      Success(findMinPrice(items))
    }
  }

}
