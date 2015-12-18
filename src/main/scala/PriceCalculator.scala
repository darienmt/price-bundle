
/**
  * Price calculator
  */
class PriceCalculator(myProducts : List[Product], myBundles : List[Bundle]) extends Calculator with BundleProviderComponent with ProductCatalogComponent {

  val bundleProvider: BundleProvider = new LocalBundleProvider(myBundles)

  val productCatalog: ProductCatalog = new LocalProductCatalog(myProducts)
}
