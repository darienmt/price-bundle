

/**
  * Bundle provider
  */
trait BundleProvider {

  /**
    * Finds the applicable bundles to the items provided.
    * @param items Items to apply bundles on.
    * @return Applicable bundles
    */
  def findApplicableBundles(items: List[Item]) : List[Bundle]
}
