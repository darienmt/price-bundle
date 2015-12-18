/**
  * Local bundle provider.
  */
class LocalBundleProvider(bundles: List[Bundle]) extends BundleProvider {

  /**
    * Finds the applicable bundles to the items provided.
    * @param items Items to apply bundles on.
    * @return Applicable bundles
    */
  def findApplicableBundles(items: List[Item]) : List[Bundle] =
    bundles.filter(b => items.count(i => {
                                            b.items.find( ib => ib.id == i.id) match {
                                              case None => false // The item most be on the bundle.
                                              case Some(bi) => i.quantity >= bi.quantity
                                              // The quantity of items must be greater or equal to the bundle quantity
                                            }
                                          }
                                    ) == b.items.size
  )
}
